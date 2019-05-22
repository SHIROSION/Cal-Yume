
/*
 * @package: PACKAGE_NAME
 * @program: Cal-Yume
 * @description
 *
 * @author:  rinne
 * @e-mail:  minami.rinne.me@gmail.com
 * @date: 2019/05/12 下午 09:40
 */

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @classname: CalculatorCheck
 * @description: %{description}
 * @author: rinne
 * @date: 2019/05/12 下午 09:40
 * @Version 1.0
 */
public class CalculatorCheck {

    private final static ArrayList<String> METHOD = new ArrayList<String>(Arrays.asList("abs", "exp", "log10", "log", "ln", "sin", "cos", "tan"));
    private final static ArrayList<String> SYMBOL = new ArrayList<String>(Arrays.asList("^", "+", "-", "*", "/" ,"%", "=", "(", ")"));

    private final static String LEFT_PARENTHESIS = "(";
    private final static String RIGHT_PARENTHESIS = ")";
    private final static String MULTIPLY = "*";
    private final static String DIVISION = "/";
    private final static String POWER = "^";
    private final static String POINT = ".";
    private final static String MODULUS = "%";
    private final static char ZERO = '0';
    private final static char NINE = '9';

    public static boolean checkMethod(String code) {
        return METHOD.contains(code);
    }

    public static boolean checkSymbol(String code) {
        return SYMBOL.contains(code);
    }

    public static boolean checkLeftParenthesis(String word) {
        return LEFT_PARENTHESIS.equals(word);
    }

    public static boolean checkRightParenthesis(String word) {
        return RIGHT_PARENTHESIS.equals(word);
    }

    public static boolean checkMultiply(String code) {
        return MULTIPLY.equals(code);
    }

    public static boolean checkDivision(String code) {
        return DIVISION.equals(code);
    }

    public static boolean checkPower(String code) {
        return POWER.equals(code);
    }

    public static boolean checkPoint(String code) {
        return POINT.equals(code);
    }

    public static boolean checkModulus(String code) {
        return MODULUS.equals(code);
    }

    public static boolean checkWord(char code) {
        return code >= 97 && code <= 122;
    }

    public static boolean checkLowSymbol(char code) {
        return code == '-' || code == '+';
    }

    public static boolean checkNumber(char code) {
        return code >= ZERO && code <= NINE;
    }

    public static boolean checkTopSymbol(String code) {
        return checkMultiply(code) || checkDivision(code) || checkPower(code) || checkModulus(code);
    }

    public static boolean checkBigSymbol(String code, String peek) {
        return (checkTopSymbol(code) && !checkTopSymbol(peek)) || checkLeftParenthesis(peek);
    }

    public static boolean checkParenthesis(String code) {
        return RIGHT_PARENTHESIS.equals(code) || LEFT_PARENTHESIS.equals(code);
    }

    public static boolean checkNumberAndPoint(char code) {
        return code >= ZERO && code <= NINE || POINT.equals(String.valueOf(code));
    }
}
