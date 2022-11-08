import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


@WebServlet("/Calculator")

public class Calculator extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        response.setContentType("text/html");
        PrintWriter out = null;
        try{
            out = response.getWriter();
            /*String result = null;
            String expression = request.getParameter("t1");
            System.out.println("Please type your expression to the calculator:");
            if(main.checkExpression(expression)) {
                List expressionLists = main.recognize(expression);
                result = main.toDoTheOperation(expressionLists);
            }*/
            double result = 0;
            String userInput = request.getParameter("t1");
            result = calc.infixCalculator(userInput);
            out.println("<h3 style=\"position: absolute; left: 50px; margin-top: 70px\">"+ userInput + " = " + result + "</h3>");
        }catch(Exception e){
            out.println("Error: " + e.getMessage());
        }
        finally {
            out.println("<br>");
            out.println("Another Calculation <a href=index.html> Click Here </a>");
            out.println("<style>");
            out.println("body{");
            out.println("background-image: url(\"desk.jpg\");");
            out.println("background-position: center center;");
            out.println("background-repeat: no-repeat;");
            out.println("background-size: cover;");
            out.println("background-attachment: fixed;");
            out.println("}");
            out.println("<style>");
        }
    }

}