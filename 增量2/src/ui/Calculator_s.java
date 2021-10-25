package ui;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculator_s {
    private static final Pattern EP = Pattern.compile("[0-9\\.+-/*()a-zA-Z, ]+");
    private static final Map<String, Integer> Order = new HashMap<>();

    public Calculator_s(){
        Order.put("(", 0);
        Order.put("+", 1);
        Order.put("-", 1);
        Order.put("*", 2);
        Order.put("/", 2);
        Order.put(")", 3);
    }

    public double calculate(String expression){
        Matcher matcher = EP.matcher(expression);
        if(!matcher.matches()){
            throw new IllegalArgumentException("表达式不合法");
        }
        Stack<String> opStack = new Stack<>();
        Stack<Double> numStack = new Stack<>();
        StringBuilder buffer = new StringBuilder();

        for(int i = 0; i < expression.length(); i++){
            char ch = expression.charAt(i);
            if ((i == 0 || (i > 0 && expression.charAt(i - 1) == '('))
                    && ch == '-'){
                numStack.push(0.0);
                opStack.push("-");
                continue;
            }
            if((ch >= '0' && ch <= '9') || ch == '.'){
                buffer.append(ch);
                continue;
            }
            if(buffer.length() > 0){
                numStack.push(Double.parseDouble(buffer.toString()));
                buffer.delete(0, buffer.length());
            }
            if((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')){
                i = calculate_special(numStack, i, expression);
                continue;
            }
            if(opStack.empty()){
                opStack.push(String.valueOf(ch));
                continue;
            }
            if(ch == '('){
                opStack.push(String.valueOf(ch));
                continue;
            }
            if(ch == ')'){
                calculate_all(opStack, numStack);
                continue;
            }
            calculate_op(opStack, numStack, String.valueOf(ch));
        }
        if(buffer.length() > 0){
            numStack.push(Double.parseDouble(buffer.toString()));
            while(!opStack.empty()){
                String op = opStack.pop();
                double y = numStack.pop();
                double x = numStack.pop();
                numStack.push(calculate_once(op, x, y));
            }
            double result = numStack.pop();
            if(!numStack.empty()){
                throw new IllegalArgumentException("表达式不合法");
            }
            return result;
        }
        while(!opStack.empty()){
            String op = opStack.pop();
            double y = numStack.pop();
            double x = numStack.pop();
            numStack.push(calculate_once(op, x, y));
        }
        double result = numStack.pop();
        if(!numStack.empty()){
            throw new IllegalArgumentException("表达式不合法");
        }
        return result;
    }

    public void calculate_all(Stack<String> opStack, Stack<Double> numStack){
        if(opStack.peek().equals("(")){
            opStack.pop();
        }
        else {
            String op = opStack.pop();
            double y = numStack.pop();
            double x = numStack.pop();
            numStack.push(calculate_once(op, x, y));
            calculate_all(opStack, numStack);
        }
    }

    public void calculate_op(Stack<String> opStack, Stack<Double> numStack, String temp){
        String op = opStack.peek();
        if(Order.get(op) >= Order.get(temp)){
            double y = numStack.pop();
            double x = numStack.pop();
            op = opStack.pop();
            numStack.push(calculate_once(op, x, y));
            if(!opStack.empty()){
                calculate_op(opStack, numStack, temp);
            }
            else{
                opStack.push(temp);
            }
        }
        else{
            opStack.push(temp);
        }


    }

    public double calculate_once(String op, double x, double y){
        switch (op){
            case "+": return x + y;
            case "-": return x - y;
            case "*": return x * y;
            case "/": return x / y;
            default: throw new IllegalArgumentException("表达式不合法");
        }
    }

    public int calculate_special(Stack<Double> numStack, int i, String expression){
        int counter = 0;
        StringBuilder buffer = new StringBuilder();
        while(true){
            char ch = expression.charAt(i);
            if((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')){
                counter ++;
                i++;
                buffer.append(ch);
                continue;
            }
            if(counter != 3){
                throw new IllegalArgumentException("表达式不合法");
            }
            String str = buffer.toString();
            String op = "";
            switch (str){
                case "sin":
                    op = "sin";
                    return operate_1(numStack, i, expression, op);
                case "cos":
                    op = "cos";
                    return operate_1(numStack, i, expression, op);
                case "tan":
                    op = "tan";
                    return operate_1(numStack, i, expression, op);
                case "squ":
                    op = "squ";
                    return operate_1(numStack, i, expression, op);
                case "sqt":
                    op = "sqt";
                    return operate_1(numStack, i, expression, op);
                case "pow":
                    return operate_2(numStack, i, expression);
            }
        }
    }

    public int operate_1(Stack<Double> numStack, int i, String expression, String op){
        char ch = expression.charAt(i);
        if(ch != '('){
            throw new IllegalArgumentException("表达式不合法");
        }
        i++;
        StringBuilder buffer = new StringBuilder();
        int counter = 0;
        while(counter < 1){
            ch = expression.charAt(i);
            if((ch >= '0' && ch <= '9') || ch == '.' || ch == '-'){
                buffer.append(ch);
                i++;
                continue;
            }
            if(buffer.length() > 0){
                numStack.push(Double.parseDouble(buffer.toString()));
                buffer.delete(0, buffer.length());
                counter++;
            }
            if(ch != ')'){
                throw new IllegalArgumentException("表达式不合法");
            }
        }
        switch (op){
            case "sin":
                numStack.push(Math.sin(numStack.pop()));
                break;
            case "cos":
                numStack.push(Math.cos(numStack.pop()));
                break;
            case "tan":
                numStack.push(Math.tan(numStack.pop()));
                break;
            case "squ":
                double a = numStack.pop();
                numStack.push(a * a);
                break;
            case "sqt":
                numStack.push(Math.sqrt(numStack.pop()));
                break;
        }
        return i;
    }

    public int operate_2(Stack<Double> numStack, int i, String expression){
        char ch = expression.charAt(i);
        if(ch != '('){
            throw new IllegalArgumentException("表达式不合法");
        }
        i++;
        StringBuilder buffer = new StringBuilder();
        int counter = 0;
        while(counter < 2){
            ch = expression.charAt(i);
            if((ch >= '0' && ch <= '9') || ch == '.' || ch == '-'){
                buffer.append(ch);
                i++;
                continue;
            }
            if(buffer.length() > 0){
                numStack.push(Double.parseDouble(buffer.toString()));
                buffer.delete(0, buffer.length());
                counter++;
            }
            if (counter == 1){
                if(ch != ','){
                    throw new IllegalArgumentException("表达式不合法");
                }
                i++;
                continue;
            }
            if(ch != ')'){
                throw new IllegalArgumentException("表达式不合法");
            }
        }
        double y = numStack.pop();
        double x = numStack.pop();
        numStack.push(Math.pow(x, y));
        return i;
    }
}

