
/*
 * @package: PACKAGE_NAME
 * @program: Cal-Yume
 * @description
 *
 * @author:  rinne
 * @e-mail:  minami.rinne.me@gmail.com
 * @date: 2019/05/14 下午 03:47
 */

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @classname: TestJunit
 * @description: %{description}
 * @author: rinne
 * @date: 2019/05/14 下午 03:47
 * @Version 1.0
 */
public class TestJunit {
    @Test
    public void testNormal() throws Exception {
        String a = "[1, +, abs, (, 1, +, 2, *, 4, /, 5, )]";
        char[] b = "1+abs(1+2*4/5)".toCharArray();
       assertEquals(a, new CalculatorLexicalAnalysis().set(b).toString());
    }

    @Test
    public void testSymbol() throws Exception {
        String a = "[1, +, abs, (, -1, +, 2, *, 4, /, 5, )]";
        char[] b = "1+abs(-1+2*4/5)".toCharArray();
        assertEquals(a, new CalculatorLexicalAnalysis().set(b).toString());
    }

    @Test
    public void testPoint() throws Exception {
        String a = "[1, +, abs, (, -1.567, +, 2.890, *, 4, /, 5, )]";
        char[] b = "1+abs(-1.567+2.890*4/5)".toCharArray();
        assertEquals(a, new CalculatorLexicalAnalysis().set(b).toString());
    }

    @Test
    public void testDoublePoint() {
        boolean flag = false;
        char[] b = "1+abs(-1.5.67+2.890*4/5)".toCharArray();
        try {
            new CalculatorLexicalAnalysis().set(b);
        } catch (Exception e) {
            flag = true;
        }
        assertTrue(flag);
    }

    @Test
    public void testDoubleSymbol() throws Exception {
        String a = "[1, +, abs, (, -1.567, +, 2.890, *, 4, /, 5, )]";
        char[] b = "1+abs(-+--+1.567+2.890*4/5)".toCharArray();
        assertEquals(a, new CalculatorLexicalAnalysis().set(b).toString());
    }

    @Test
    public void testDoubleSymbol1() throws Exception {
        String a = "[1, +, abs, (, -1.567, +, -2.890, *, 4, /, 5, )]";
        char[] b = "1+abs(-+--+1.567+-+--+++2.890*4/5)".toCharArray();
        assertEquals(a, new CalculatorLexicalAnalysis().set(b).toString());
    }

    @Test
    public void testDoubleSymbol2() throws Exception {
        boolean flag = false;
        char[] b = "1+abs(-+--+1.567+-+-*-+++2.890*4/5)".toCharArray();
        try {
            new CalculatorLexicalAnalysis().set(b);
        } catch (Exception e) {
            flag = true;
        }
        assertTrue(flag);
    }

    @Test
    public void testMethodNumber() throws Exception {
        String a = "[1, +, 10, *, abs, (, -1.567, +, 2.890, *, 4, /, 5, )]";
        char[] b = "1+10abs(-1.567+2.890*4/5)".toCharArray();
        assertEquals(a, new CalculatorLexicalAnalysis().set(b).toString());
    }

    @Test
    public void testFirstZero() throws Exception {
        boolean flag = false;
        char[] b = "012+5".toCharArray();
        try {
            new CalculatorLexicalAnalysis().set(b);
        } catch (Exception e) {
            flag = true;
        }
        assertTrue(flag);
    }

    @Test
    public void testOneWord() {
        boolean flag = false;
        char[] b = "1+a".toCharArray();
        try {
            new CalculatorLexicalAnalysis().set(b);
        } catch (Exception e) {
            flag = true;
        }
        assertTrue(flag);
    }

    @Test
    public void testTwoWord() {
        boolean flag = false;
        char[] b = "1+as".toCharArray();
        try {
            new CalculatorLexicalAnalysis().set(b);
        } catch (Exception e) {
            flag = true;
        }
        assertTrue(flag);
    }

    @Test
    public void testNumberAndWord() {
        boolean flag = false;
        char[] b = "1+345as".toCharArray();
        try {
            new CalculatorLexicalAnalysis().set(b);
        } catch (Exception e) {
            flag = true;
        }
        assertTrue(flag);
    }

    @Test
    public void testNumberAndWord1() {
        boolean flag = false;
        char[] b = "1+345s123".toCharArray();
        try {
            new CalculatorLexicalAnalysis().set(b);
        } catch (Exception e) {
            flag = true;
        }
        assertTrue(flag);
    }

    @Test
    public void testMethod() throws Exception {
        String a = "[abs]";
        char[] b = "abs".toCharArray();
        assertEquals(a, new CalculatorLexicalAnalysis().set(b).toString());
    }

    @Test
    public void testPN() throws Exception {
        String a = "[6, 5, 2, 3, +, 8, *, +, 3, +, *]";
        char[] b = "6*(5+(2+3)*8+3)".toCharArray();
        assertEquals(a, new CalculatorControl(b).returnTest());
    }

    @Test
    public void testPN2() throws Exception {
        String a = "[4, 5, +, 6, *, 5, -, 2, /, 3, 2, *, +]";
        char[] b = "((4+5)*6-5)/2+3*2".toCharArray();
        assertEquals(a, new CalculatorControl(b).returnTest());
    }

    @Test
    public void testPN3() throws Exception {
        String a = "[5, 1, 2, +, 4, *, +, 3, -]";
        char[] b = "5+((1+2)*4)-3".toCharArray();
        assertEquals(a, new CalculatorControl(b).returnTest());
    }

    @Test
    public void testPN4() throws Exception {
        String a = "[10, 6, 9, 3, +, 11, *, /, *, 17, +, 5, +]";
        char[] b = "((10*(6/((9+3)*11)))+17)+5".toCharArray();
        assertEquals(a, new CalculatorControl(b).returnTest());
    }

    @Test
    public void testPN5() throws Exception {
        String a = "[9, 3, 4, 2, -, *, 3, *, +, 10, 2, /, +]";
        char[] b = "9+(3*(4-2))*3+10/2".toCharArray();
        assertEquals(a, new CalculatorControl(b).returnTest());
    }

    @Test
    public void testPN6() throws Exception {
        String a = "[1, 2, 5, -, 6, 9, *, +, 6, -, 7, 2, /, 3, *, 6, 9, 345, *, +, 76, -, *, +, 2, /, 3, *, +, 23, -]";
        char[] b = "1+(2-5+6*9-6+7/2*3*(6+9*345-76))/2*3-23".toCharArray();
        assertEquals(a, new CalculatorControl(b).returnTest());
    }

    @Test
    public void testPNParenthesis() {
        boolean flag = false;
        char[] b = "1+abs(1+2+4))".toCharArray();
        try {
            new CalculatorControl(b).returnTest();
        } catch (Exception e) {
            flag = true;
        }
        assertTrue(flag);
    }

    @Test
    public void testPNParenthesis1() {
        boolean flag = false;
        char[] b = "1+abs((1+2+4)".toCharArray();
        try {
            new CalculatorControl(b).returnTest();
        } catch (Exception e) {
            flag = true;
        }
        assertTrue(flag);
    }

    @Test
    public void testPNParenthesisVoid() {
        boolean flag = false;
        char[] b = "1+abs()".toCharArray();
        try {
            new CalculatorControl(b).returnTest();
        } catch (Exception e) {
            flag = true;
        }
        assertTrue(flag);
    }

    @Test
    public void testPNParenthesis2() {
        boolean flag = false;
        char[] b = "1+a(1+3*6)".toCharArray();
        try {
            new CalculatorControl(b).returnTest();
        } catch (Exception e) {
            flag = true;
        }
        assertTrue(flag);
    }

    @Test
    public void testResult() throws Exception {
        String a = "6.2";
        char[] b = "1+2*3-4/5".toCharArray();
        assertEquals(a, new CalculatorControl(b).getResult());
    }

    @Test
    public void testResult1() throws Exception {
        String a = "47846.3";
        char[] b = "1+(2-5+6*9-6.3+7/2*3*(6+9*345-76))/2*3-23".toCharArray();
        assertEquals(a, new CalculatorControl(b).getResult());
    }

    @Test
    public void testResult2() throws Exception {
        String a = "32.0";
        char[] b = "9+(3*(4-2))*3+10/2".toCharArray();
        assertEquals(a, new CalculatorControl(b).getResult());
    }

    @Test
    public void testResult3() {
        boolean flag = false;
        char[] b = "1+7/0".toCharArray();
        try {
            new CalculatorControl(b).getResult();
        } catch (Exception e) {
            flag = true;
        }
        assertTrue(flag);
    }

    @Test
    public void testResult4() throws Exception {
        String a = "3.0";
        char[] b = "12/4".toCharArray();
        assertEquals(a, new CalculatorControl(b).getResult());
    }

    @Test
    public void testResult5() throws Exception {
        String a = "0.0";
        char[] b = "12%4".toCharArray();
        assertEquals(a, new CalculatorControl(b).getResult());
    }

    @Test
    public void testResult6() throws Exception {
        String a = "-11.0";
        char[] b = "12+-+--+23".toCharArray();
        assertEquals(a, new CalculatorControl(b).getResult());
    }

    @Test
    public void testResult7() throws Exception {
        String a = "-276.0";
        char[] b = "12*+-+--+23".toCharArray();
        assertEquals(a, new CalculatorControl(b).getResult());
    }

    @Test
    public void testResult8() throws Exception {
        String a = "2.0";
        char[] b = "1+abs(-1)".toCharArray();
        assertEquals(a, new CalculatorControl(b).getResult());
    }

    @Test
    public void testResult9() throws Exception {
        String a = "1.0";
        char[] b = "abs(-1)".toCharArray();
        assertEquals(a, new CalculatorControl(b).getResult());
    }

    @Test
    public void testResult10() throws Exception {
        String a = "10.0";
        char[] b = "10abs(-1)".toCharArray();
        assertEquals(a, new CalculatorControl(b).getResult());
    }

    @Test
    public void testResult11() throws Exception {
        String a = "1.5403023058681398";
        char[] b = "1+cos(-1)".toCharArray();
        assertEquals(a, new CalculatorControl(b).getResult());
    }

    @Test
    public void testResult12() throws Exception {
        String a = "1.3678794411714423";
        char[] b = "1+exp(-1)".toCharArray();
        assertEquals(a, new CalculatorControl(b).getResult());
    }

    @Test
    public void testResult13() throws Exception {
        String a = "0.1585290151921035";
        char[] b = "1+sin(-1)".toCharArray();
        assertEquals(a, new CalculatorControl(b).getResult());
    }
}
