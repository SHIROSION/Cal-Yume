
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
        this.newFormula = new ArrayList<String>();
        this.stack = new CalculatorStack<String>();
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

        while (!this.stack.isEmpty()) {
            this.stack.pop();
        }

        for (String code : this.newFormula) {
            if (!CalculatorCheck.checkSymbol(code)) {
                this.stack.add(code);
            } else if (CalculatorCheck.checkSymbol(code)) {
                if (this.stack.getSize() >= 2) {
                    this.stack.add(calculator(this.stack.pop(), code, this.stack.pop()));
                }
            } else {
                throw new Exception("语法错误");
            }
        }
        return this.stack.pop();
    }

//    private void checkNumberSymbol(String second, String symbol, String first) {
//
//        int minusTimes = 0;
//        boolean firstMinus = false;
//        boolean secondMinus = false;
//
//        StringBuffer newSecondNumber = new StringBuffer();
//        StringBuffer newFirstNumber = new StringBuffer();
//
//        char[] secondNumber = second.toCharArray();
//        char[] firstNumber = first.toCharArray();
//
//        if (CalculatorCheck.checkNumber(secondNumber[0])) {
//            newSecondNumber = new StringBuffer(second);
//        } else {
//            for (char a : second.toCharArray()) {
//                if (CalculatorCheck.checkLowSymbol(a)) {
//                    if (a == this.MINUS) {
//                        minusTimes ++;
//                    }
//                } else if (a == this.ZERO){
//                    newSecondNumber.append(a);
//                    break;
//                } else {
//                    newSecondNumber.append(a)
//                }
//            }
//
//            if ()
//        }
//
//        if (CalculatorCheck.checkNumber(firstNumber[0])) {
//            newFirstNumber = new StringBuffer(second);
//        }
//    }

    private String calculator(String second, String symbol, String first) throws Exception {
        Double firstNumber = Double.valueOf(first);
        Double secondNumber = Double.valueOf(second);

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
