import java.util.Scanner;
import java.util.Stack;

public class calc {

    public static final int TITLE_SCREEN = 0;                                                     //Constant values for UI messages
    public static final int INSTRUCTION_SCREEN = 1;
    public static final int ERROR_INVALID_CHARACTER = 2;
    public static final int ERROR_DOUBLE_OPERATOR = 3;
    public static final int ERROR_DOUBLE_NUMBER = 4;
    public static final int ERROR_END_OPERATOR = 5;
    public static final int EXIT_SCREEN = 6;
    static Scanner input = new Scanner(System.in);                                                //Create Scanner Object

    public static void main(String[] args) {
        boolean exit = false;
        printMenu(TITLE_SCREEN);                                                                   //Print welcome message
        while(!exit) {                                                                             //Run program until user enters "exit"
            printMenu(INSTRUCTION_SCREEN);                                                         //Print Instructions to user
            String userInput = input.nextLine();                                                   //Take user input
            if(userInput.equalsIgnoreCase("exit")) exit = true;                                    //Check if user chose to exit
            else infixCalculator(userInput);
        }
        printMenu(EXIT_SCREEN);
        input.close();                                                                             //Close Scanner object
    }

    public static double infixCalculator(String input){
        String postfixExpression = inputInterpret(input);                              //Validate input expression, convert to postfix if valid, print error if not
        if (!postfixExpression.equalsIgnoreCase("")) {                                     //If valid input, calculate result of expression, if invalid return to Instruction & Input screen
            //System.out.println("for testing, postfix expression: " + postfixExpression);   //testing line
            double result = postfixCalculator(postfixExpression);
            double scale = Math.pow(10, 3);  // round to 3 decimal places
            result = Math.round(result * scale) / scale;                                //Print Result of calculation, evaluatePostFix() returns an integer.
            printResult(result);
            return result;
        }
        else {
            return -1;
        }
    }

    //Takes integer postfix expression String (Whitespace separating each value and operator e.g. "5 10 6 * +")
    //Evaluates and returns the resulting integer
    public static double postfixCalculator(String expression) {
        Stack<Double> stack = new Stack<Double>();
        double x = 0;
        boolean isNegative = false;
        int isLog = 0;
        int isExp = 0;
        int pastFloatingPoint = 0;
        for (int i = 0; i < expression.length(); i++) {
            if (expression.charAt(i) == 'l' && expression.charAt(i+1) == 'n') {
                isLog++;
                i = i + 2; // skip over characters of "ln("
            }
            else if (expression.charAt(i) == 'e' && expression.charAt(i+1) == 'x' && expression.charAt(i+2) == 'p') {
                isExp++;
                i = i + 3; // skip over characters of "exp("
            }
            else if (expression.charAt(i) == ')') {
                double result = stack.pop();
                if (isLog > 0) {
                    result = Math.log(result);
                    isLog--;
                }
                else if (isExp > 0) {
                    result = Math.exp(result);
                    isExp--;
                }
                stack.push(result);

            }
            else if (Character.isDigit(expression.charAt(i))) {
                if (pastFloatingPoint > 0) {
                    double temp = (double) Character.getNumericValue(expression.charAt(i)) / (Math.pow(10,pastFloatingPoint));
                    x = x + temp;
                    pastFloatingPoint++;
                }
                else if(isNegative)
                    x -= Character.getNumericValue(expression.charAt(i));
                else
                    x += Character.getNumericValue(expression.charAt(i));

                if((i+1) < expression.length() && Character.isDigit(expression.charAt(i+1)) && pastFloatingPoint == 0) {
                    x = x*10;
                }

                if((i+1) >= expression.length() || (!Character.isDigit(expression.charAt(i+1)) && expression.charAt(i+1) != '.')) {
                    stack.push(x);
                    x = 0;
                    isNegative = false;
                    pastFloatingPoint = 0;
                }
            }
            else if (expression.charAt(i) == '.') {
                pastFloatingPoint++;
            }
            else if((i+1) < expression.length() && expression.charAt(i) == '-' && Character.isDigit(expression.charAt(i+1))) {
                isNegative = true;
            }
            else if(checkPrecedence(expression.charAt(i)) != -1){
                double oprand2 = (double) stack.pop();
                double oprand1 = (double) stack.pop();
                double result = 0;

                if (expression.charAt(i) == '^') result = Math.pow(oprand1, oprand2);
                else if(expression.charAt(i) == '*') result = oprand1 * oprand2;
                else if (expression.charAt(i) == '/') result = oprand1 / oprand2;
                else if (expression.charAt(i) == '+') result = oprand1 + oprand2;
                else if (expression.charAt(i) == '-') result = oprand1 - oprand2;

                stack.push(result);
            }
        }
        return stack.pop();
    }

    //-------------NEGATIVE VALUES IN USER INPUT NOT HANDLED YET---------------
    //Takes user-input mathematical expression (infix notation)
    //Checks if valid expression,
    //If valid, returns postfix conversion of infix expression
    //If invalid,  prints errors and returns ""
    public static String inputInterpret(String input) {
        String invalidExpression = "";                                                        //Initialising return Strings
        String postfixExpression = "";
        Stack<Character> stack = new Stack<Character>();
        boolean lastCharacterDigit = false;                                                   //Flag used for validating expressions. False if last character(s) processed was a numeric value, True if the last character processed was an operator

        for (int i = 0; i < input.length(); i++) {
            //process whitespace
            while (input.charAt(i) == ' ') {
                ++i;
            }

            if (input.charAt(i) == 'l' && input.charAt(i+1) == 'n') { // check for log
                i = i + 2; // skip to number of log
                postfixExpression += "ln(";
                stack.push('l'); // marks end of log in stack
            }
            else if (input.charAt(i) == 'e' && input.charAt(i+1) == 'x' && input.charAt(i+2) == 'p') { // check for exp
                i = i + 3; // skip to number of exp
                postfixExpression += "exp(";
                stack.push('e'); // marks end of exp in stack
            }

            //process number values
            if (Character.isDigit(input.charAt(i)) || input.charAt(i) == '.') {                                         //Check if character at position i is a numeric value
                if (lastCharacterDigit) {
                    printMenu(ERROR_DOUBLE_NUMBER);                                           //If the character is a numeric value, and the last element of the user input processed was also a numeric value,
                    return invalidExpression;                                                 //An error message is printed and the string is said to be invalid, as there were two numbers after one another with no operator in between.
                }

                postfixExpression += input.charAt(i);
                if (i+1 >= input.length() || (!Character.isDigit(input.charAt(i+1)) && input.charAt(i+1) != '.')) {
                    postfixExpression += ' ';
                    lastCharacterDigit = true;
                }
            }

            else if (!lastCharacterDigit && input.charAt(i) == '-' && Character.isDigit(input.charAt(i+1))) {
                postfixExpression += input.charAt(i);
            }

            else if (input.charAt(i) == '(') {
                stack.push(input.charAt(i));
            }

            else if (input.charAt(i) == ')') {
                while ((!stack.isEmpty()) && (stack.peek() != '(')) {
                    postfixExpression += stack.pop();
                    postfixExpression += ' ';
                }
                stack.pop(); // discard left bracket
                if (stack.peek() == 'l' || stack.peek() == 'e') {
                    postfixExpression += ") ";
                    stack.pop();
                }
            }

            //process operators
            else if (checkPrecedence(input.charAt(i)) != -1) {                                //Check if character at position i is an operator
                if (!lastCharacterDigit) {
                    printMenu(ERROR_DOUBLE_OPERATOR);                                         //If the character is an operator, and the last element of the user input processed was also an operator,
                    return invalidExpression;                                                 //An error message is printed and the string is said to be invalid, as there were two operators after one another with no number in between.
                }

                while ((!stack.isEmpty()) && (checkPrecedence(stack.peek()) >= checkPrecedence(input.charAt(i))) && (stack.peek() != '(')) {
                    postfixExpression += stack.pop();
                    postfixExpression += ' ';
                }
                stack.push(input.charAt(i));
                lastCharacterDigit = false;
            }

            else {
                printMenu(ERROR_INVALID_CHARACTER);                                            //Print Error message for invalid character used
                return invalidExpression;
            }
        }

        if(!lastCharacterDigit) {
            printMenu(ERROR_END_OPERATOR);                                            //Print Error message for invalid character used
            return invalidExpression;
        }

        else {
            while (!stack.isEmpty()) {
                postfixExpression += stack.pop();
                postfixExpression += ' ';
            }
            return postfixExpression;
        }
    }

    //takes character of mathematical operator
    //returns an integer relating to its precedence level
    //returns -1 if not a valid operator
    public static int checkPrecedence(char operator){
        if(operator == '^'){  // lowest precedence
            return 2;
        }
        else if(operator == '*' || operator == '/'){ //higher precedence
            return 1;
        }
        else if(operator == '+' || operator == '-'){  // lower precedence
            return 0;
        }
        return -1; // invalid operator
    }

    //takes integer to navigate to a specific message for the user and prints it.
    //return type void
    private static void printMenu(int option) {
        switch(option) {
            case 0:
                System.out.println("\r\n" +
                        "   _____      _            _       _               _______ _                \r\n" +
                        "  / ____|    | |          | |     | |             |__   __(_)               \r\n" +
                        " | |     __ _| | ___ _   _| | __ _| |_ ___  _ __     | |   _ _ __ ___   ___ \r\n" +
                        " | |    / _` | |/ __| | | | |/ _` | __/ _ \\| '__|    | |  | | '_ ` _ \\ / _ \\\r\n" +
                        " | |___| (_| | | (__| |_| | | (_| | || (_) | |       | |  | | | | | | |  __/\r\n" +
                        "  \\_____\\__,_|_|\\___|\\__,_|_|\\__,_|\\__\\___/|_|       |_|  |_|_| |_| |_|\\___|\r\n" +
                        "                                                                            \r\n" +
                        "                                                                            \r\n" +
                        "");
                break;

            case 1:
                System.out.print("Enter a simple mathematical expression (or \"exit\"): ");
                break;

            case 2:
                System.out.println("ERROR: Invalid character, only enter numerics and mathematical operators: (+, -, *, /) \r\n");
                break;

            case 3:
                System.out.println("ERROR: Two operators with no numeric value inbetween \r\n");
                break;

            case 4:
                System.out.println("ERROR: Two numeric values with no operator inbetween \r\n");
                break;

            case 5:
                System.out.println("ERROR: Can't end expression with an operator \r\n");
                break;

            case 6:
                System.out.println("Goodbye!");
                break;
        }

    }

    //takes integer result from calculations and prints it to screen with message
    //return type void
    private static void printResult(double result) {
        String resultString = "Result: " + result + "\r\n";
        System.out.println(resultString);
    }
}