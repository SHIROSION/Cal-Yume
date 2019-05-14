
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
    private boolean symbol = false;

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
            }  else if (!CalculatorCheck.checkTopSymbol(code)) {
                // 如果堆栈为空，则压栈
                if (this.stack.isEmpty()) {
                    this.stack.add(code);
                } else {
                    // 如果堆栈不为空，且栈顶元素优先级大于遍历的元素
                    if (CalculatorCheck.checkTopSymbol(this.stack.peek())) {
                        // 栈顶弹出，直到栈顶栈顶元素优先级不再高于遍历的元素
                        do {
                            this.newFormula.add(this.stack.pop());
                        } while (!CalculatorCheck.checkTopSymbol(this.stack.peek())
                                && !CalculatorCheck.checkLeftParenthesis(this.stack.peek()));
                        // 弹完栈顶后再入栈
                        this.stack.add(code);
                    } else {
                        this.stack.add(code);
                    }
                }
            } else {
                this.stack.add(code);
            }
        }

        // 遍历完元素后，若栈内还有元素则弹栈
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
}
