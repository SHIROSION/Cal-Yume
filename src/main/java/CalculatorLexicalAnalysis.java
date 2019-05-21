
/*
 * @package: PACKAGE_NAME
 * @program: Cal-Yume
 * @description
 *
 * @author:  rinne
 * @e-mail:  minami.rinne.me@gmail.com
 * @date: 2019/05/12 下午 09:43
 */

import java.util.ArrayList;

/**
 * @classname: CalculatorLexicalAnalysis
 * @description: %{description}
 * @author: rinne
 * @date: 2019/05/12 下午 09:43
 * @Version 1.0
 */
public class CalculatorLexicalAnalysis {
    private ArrayList<String> newFormula;
    private ArrayList<String> text;

    private boolean pointFind = false;
    private boolean symbolFlag = false;
    private char ZERO = '0';

    public CalculatorLexicalAnalysis() {
        this.newFormula = new ArrayList<String>();
        this.text = new ArrayList<String>();
    }

    public ArrayList<String> set(char[] code) throws Exception {

        for (int i = 0; i < code.length; i ++) {
            if (CalculatorCheck.checkNumberAndPoint(code[i])) {
                addNumber(code[i], code.length, i);
            } else {
                addPunctuation(code[i]);
            }
        }
        finalSetFormula();
        System.out.println(this.newFormula);
        return this.newFormula;
    }

    private void addNumber(char code, int codeLength, int i) throws Exception {

        if (this.text.isEmpty() && code == this.ZERO) {
            throw new Exception("语法错误");
        }
        // 判断传入字符是否为数字
        if (CalculatorCheck.checkPoint(String.valueOf(code))) {
            if (this.text.isEmpty()) {
                throw new Exception("语法错误");
            } else if (!this.pointFind) {
                this.pointFind = true;
                this.text.add(String.valueOf(code));
            } else {
                throw new Exception("语法错误");
            }
        } else if (codeLength - 1 == i) {
            StringBuilder number = new StringBuilder();
            for (String c : this.text) {
                number.append(c);
            }
            this.newFormula.add(number.append(code).toString());
            this.text.clear();
        } else {
            this.text.add(String.valueOf(code));
        }
    }

    private void addPunctuation(char code) throws Exception {

        if (CalculatorCheck.checkWord(code)) {

            if (CalculatorCheck.checkWord(code)) {
                if (!this.text.isEmpty()) {
                    if (CalculatorCheck.checkWord(this.text.get(this.text.size() - 1).toCharArray()[0])) {
                        this.text.add(String.valueOf(code));
                    } else if (CalculatorCheck.checkNumber(this.text.get(this.text.size() - 1).toCharArray()[0])) {
                        this.addNewFormula();
                        this.newFormula.add("*");
                        this.text.clear();
                        this.text.add(String.valueOf(code));
                    } else if (CalculatorCheck.checkSymbol(this.text.get(this.text.size() - 1))) {
                        this.addNewFormula();
                        this.text.clear();
                        this.text.add(String.valueOf(code));
                    } else {
                        throw new Exception("语法错误");
                    }
                } else {
                    this.text.add(String.valueOf(code));
                }
            }

        } else if (CalculatorCheck.checkSymbol(String.valueOf(code))) {
            if (!this.text.isEmpty()) {
                this.addNewFormula();
                this.newFormula.add(String.valueOf(code));
                this.text.clear();
                this.pointFind = false;
            } else {
                this.newFormula.add(String.valueOf(code));
            }
        }
    }

    private void finalSetFormula() throws Exception {
        if (!this.text.isEmpty()) {
            StringBuilder a = new StringBuilder();
            for (String s : this.text) {
                a.append(s);
            }
            this.newFormula.add(a.toString());
        }
        this.text.clear();
        mergeSymbol();
    }

    private void mergeSymbol() throws Exception {
        StringBuilder s = new StringBuilder();

        for (String code : this.newFormula) {
            if (CalculatorCheck.checkSymbol(code)) {
                if (!this.symbolFlag || CalculatorCheck.checkParenthesis(code)) {
                    this.symbolFlag = true;
                    this.text.add(code);
                    if (CalculatorCheck.checkRightParenthesis(code)) {
                        this.symbolFlag = false;
                    }
                } else if (this.symbolFlag && CalculatorCheck.checkLowSymbol(code.charAt(0))) {
                    s.append(code);
                } else {
                    throw new Exception("语法错误");
                }
            } else {
                if (CalculatorCheck.checkWord(code.charAt(0))) {
                    if (CalculatorCheck.checkMethod(code)) {
                        this.symbolFlag = false;
                        this.text.add(s.append(code).toString());
                        s.setLength(0);
                    } else {
                        throw new Exception("语法错误");
                    }
                } else {
                    this.symbolFlag = false;
                    this.text.add(s.append(code).toString());
                    s.setLength(0);
                }
            }
        }

        this.newFormula.clear();
        this.newFormula = this.text;
    }

    private void addNewFormula() {
        StringBuilder n = new StringBuilder();
        for (String c : this.text) {
            n.append(c);
        }
        this.newFormula.add(n.toString());
    }
}
