package ui;

import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculator_f {
    private static final Pattern EP = Pattern.compile("[0-9\\.+-/*()= ]+");
    private static final Map<String, Integer> Order = new HashMap<>();

    public Calculator_f(){
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
}

