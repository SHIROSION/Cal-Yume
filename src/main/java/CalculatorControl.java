
/*
 * @package: PACKAGE_NAME
 * @program: Cal-Yume
 * @description
 *
 * @author:  rinne
 * @e-mail:  minami.rinne.me@gmail.com
 * @date: 2019/05/13 下午 09:45
 */


import java.util.ArrayList;

/**
 * @classname: CalculatorControl
 * @description: %{description}
 * @author: rinne
 * @date: 2019/05/13 下午 09:45
 * @Version 1.0
 */
public class CalculatorControl {
    private CalculatorStack<String> stack;
    private ArrayList<String> formula;
    private ArrayList<String> newFormula;
    private boolean method = false;
    private char MINUS = '-';
    private char ZERO = '0';

    public CalculatorControl(char[] inputFormula) throws Exception {
        this.formula = new CalculatorLexicalAnalysis().set(inputFormula);
        this.newFormula = new ArrayList<>();
        this.stack = new CalculatorStack<>();
        reversePolishNotation();
    }

    private void reversePolishNotation() throws Exception {
        for (String code : this.formula) {
            // 如果遍历的元素不是符号
            if (!CalculatorCheck.checkSymbol(code)) {
                this.newFormula.add(code);
                this.method = CalculatorCheck.checkMethod(code);
                // 判断是否是左括号，是左括号压栈
            } else if (CalculatorCheck.checkLeftParenthesis(code)) {
                this.stack.add(code);
                // 如果是右括号
            } else if (CalculatorCheck.checkRightParenthesis(code)) {

                if (this.method) {
                    throw new Exception("语法错误");
                }

                // 直到遇到左括号前都弹栈
                while (!CalculatorCheck.checkLeftParenthesis(this.stack.peek())) {
                    // 弹出的栈都压入列表里存放
                    this.newFormula.add(this.stack.pop());
                    // 如果栈顶空了，则报错
                    if (this.stack.isEmpty()) {
                        throw new Exception("语法错误！");
                    }
                }

                // 如果栈顶为左括号，则弹出
                if (CalculatorCheck.checkLeftParenthesis(this.stack.peek())) {
                    this.stack.pop();
                }
                // 如果遍历到不是"*","/","^"这三个运算优先级高的符号且栈顶元素优先级高于遍历到的元素
                // 则一直弹栈，直到遇到优先级高的符号或者左括号
            } else {
                if (this.stack.isEmpty()) {
                    this.stack.add(code);
                } else if (CalculatorCheck.checkBigSymbol(code, this.stack.peek())) {
                    this.stack.add(code);
                } else {
                    do {
                        this.newFormula.add(this.stack.pop());
                        if (this.stack.isEmpty()) {
                            break;
                        }
                    } while (!CalculatorCheck.checkBigSymbol(code, this.stack.peek()));

                    this.stack.add(code);
                }
            }
        }

        while (!this.stack.isEmpty()) {
            // 若栈内还有多余的左括号，则证明表达式语法错误
            if (!CalculatorCheck.checkLeftParenthesis(this.stack.peek())) {
                this.newFormula.add(this.stack.pop());
            } else {
                throw new Exception("语法错误");
            }
        }

        System.out.println(this.newFormula);
    }

    public String getResult() throws Exception {
        String second = "";
        String first = "";

        while (!this.stack.isEmpty()) {
            this.stack.pop();
        }

        for (String code : this.newFormula) {
            if (!CalculatorCheck.checkSymbol(code)) {
                this.stack.add(code);
            } else if (CalculatorCheck.checkSymbol(code)) {
                if (this.stack.getSize() >= 2) {
                    second = this.stack.pop();
                    first = this.stack.pop();
                    if (CalculatorCheck.checkMethod(first)) {
                        this.stack.add(calculator(mergeNumberSymbol(this.stack.pop()), mergeNumberSymbol(getMethod(first, second)), code));
                    } else {
                        this.stack.add(calculator(mergeNumberSymbol(second), mergeNumberSymbol(first), code));
                    }
                }
            } else {
                throw new Exception("语法错误");
            }
        }

        if (this.stack.getSize() == 2) {
            String number = this.stack.pop();
            String method = this.stack.pop();
            this.stack.add(getMethod(method, number));
        }

        return this.stack.pop();
    }

    private String mergeNumberSymbol(String number) {
        int times = 0;
        int minusTimes = 0;
        char minus = '-';
        int two = 2;

        for (char a : number.toCharArray()) {
            if (CalculatorCheck.checkLowSymbol(a)) {
                times ++;
                if (a == minus) {
                    minusTimes ++;
                }
            } else {
                break;
            }
        }

        if (minusTimes % two == 0) {
            return number;
        } else {
            return "-" + number.substring(times);
        }
    }

    private String getMethod(String method, String number) throws Exception {
        double newNumber = Double.parseDouble(number);

        switch (method) {
            case "abs":
                return String.valueOf(Math.abs(newNumber));
            case "exp":
                return String.valueOf(Math.exp(newNumber));
            case "log10":
                if (newNumber >= 0) {
                    return String.valueOf(Math.log10(newNumber));
                } else {
                    throw new Exception("语法错误");
                }
            case "log":
                if (newNumber >= 0) {
                    return String.valueOf(Math.log(newNumber));
                } else {
                    throw new Exception("语法错误");
                }
            case "ln":
                if (newNumber >= 0) {
                    return String.valueOf(Math.log1p(newNumber));
                } else {
                    throw new Exception("语法错误");
                }
            case "sin":
                return String.valueOf(Math.sin(newNumber));
            case "cos":
                return String.valueOf(Math.cos(newNumber));
            case "tan":
                try {
                    double a = Math.tan(newNumber);
                    return String.valueOf(a);
                } catch (Exception e) {
                    throw new Exception("语法错误");
                }
            default:
                throw new Exception("语法错误");
        }
    }

    private String calculator(String second, String first, String symbol) throws Exception {
        double firstNumber = Double.valueOf(first);
        double secondNumber = Double.valueOf(second);

        switch (symbol) {
            case "+":
                return String.valueOf(firstNumber + secondNumber);
            case "-":
                return String.valueOf(firstNumber - secondNumber);
            case "*":
                return String.valueOf(firstNumber * secondNumber);
            case "/":
                if (secondNumber == 0) {
                    throw new Exception("分母不能为0");
                } else {
                    return String.valueOf(firstNumber / secondNumber);
                }
            case "^":
                return String.valueOf(Math.pow(firstNumber, secondNumber));
            case "%":
                if (secondNumber == 0) {
                    throw new Exception("分母不能为0");
                } else {
                    return String.valueOf(firstNumber % secondNumber);
                }
            default:
                    throw new Exception("语法错误");
        }
    }

    public String returnTest() {
        return this.newFormula.toString();
    }
}
