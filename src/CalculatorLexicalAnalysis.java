
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
    private boolean useMethod = false;
    private final static String POINT = ".";

    public CalculatorLexicalAnalysis() {
        this.newFormula = new ArrayList<>();
        this.text = new ArrayList<>();
    }

    /**
     * 该方法主要用于初始化字符串变量和操作小数点的flag变量
     */
    private void setNumberNull() {
        this.text.clear();
        this.pointFind = false;
    }

    public ArrayList<String> set(char[] code) throws Exception {

        for (int i = 0; i < code.length; i ++) {
            if (CalculatorCheck.checkNumberAndPoint(code[i])) {
                addNumber(code[i], code.length, i);
            } else {
                addPunctuation(code[i]);
            }
        }

        System.out.println(this.newFormula);
        return this.newFormula;
    }

    private void addNumber(char code, int codeLength, int i) throws Exception {

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
        } else {
            this.text.add(String.valueOf(code));
        }
    }

    public void addPunctuation(char code) throws Exception {

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

    private void addNewFormula() {
        StringBuilder n = new StringBuilder();
        for (String c : this.text) {
            n.append(c);
        }
        this.newFormula.add(n.toString());
    }
}
