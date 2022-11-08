import java.util.ArrayList;
import java.util.List;

public class main {
    static char[] testChars = {'0','1','2','3','4','5','6','7','8','9','+','-','*'};

    public static void printer(String result){
        System.out.print(result);
        return;
    }

    public static String operations(String firstNum, String secondNum, String operator){
        double result = 0;
        switch (operator) {
            case "*":
                result = result + Double.parseDouble(firstNum) * Double.parseDouble(secondNum);
                break;
            case "-":
                result = result + Double.parseDouble(firstNum) - Double.parseDouble(secondNum);
                break;
            case "+":
                result = result + Double.parseDouble(firstNum) + Double.parseDouble(secondNum);
                break;
        }
        return Double.toString(result);
    }

    public static List recognize(String expression){
        String string = "";
        char [] expressions = expression.toCharArray();
        List<String> expressionList = new ArrayList<>();
        for (int i = 0; i < expressions.length; i++){
            if (Character.isDigit(expressions[i])){
                string = string + Character.toString(expressions[i]);
            }
            else {
                expressionList.add(string);
                expressionList.add(Character.toString(expressions[i]));
                string = "";
            }
        }
        expressionList.add(string);


        return expressionList;
    }

    public static String toDoTheOperation(List expressionLists){
        String result = "";
        String firstNum = "";
        String secondNum = "";
        String operator = "";
        for (int i = 0; i < expressionLists.size(); i++) {
            if (expressionLists.get(i).equals("*")) {
                firstNum = expressionLists.get(i - 1).toString();
                secondNum = expressionLists.get(i + 1).toString();
                result = operations(firstNum, secondNum, "*");
                expressionLists.remove(i - 1);
                expressionLists.remove(i);
                expressionLists.set(i - 1, result);
                i = 0;
            }
        }
        for (int j = 0; j <expressionLists.size(); j++){
            if (expressionLists.get(j).equals("+")) {
                firstNum = expressionLists.get(j - 1).toString();
                secondNum = expressionLists.get(j + 1).toString();
                result = operations(firstNum, secondNum, "+");
                expressionLists.remove(j - 1);
                expressionLists.remove(j);
                expressionLists.set(j - 1, result);
                j = 0;
            }
            if (expressionLists.get(j).equals("-")) {
                firstNum = expressionLists.get(j - 1).toString();
                secondNum = expressionLists.get(j + 1).toString();
                result = operations(firstNum, secondNum, "-");
                expressionLists.remove(j - 1);
                expressionLists.remove(j);
                expressionLists.set(j - 1, result);
                j = 0;
            }
        }
        return result;
    }

    public static boolean checkExpression(String input) {
        boolean check = false;
        if(!input.contains("+") && !input.contains("-") && !input.contains("*") ) {
            return false;
        }
        char[] chars = input.toCharArray();
        for (char ch: chars) {
            int i=0;
            while(i<testChars.length) {
                if(ch==testChars[i]) {
                    check = true;
                    break;
                }

                else {
                    if(i==testChars.length-1) {
                        return false;
                    }
                    check = false;
                    i++;
                }
            }
        }
        return check;


    }
}
