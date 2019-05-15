
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
        String a = "[1, +, abs, (, -+--+1.567, +, 2.890, *, 4, /, 5, )]";
        char[] b = "1+abs(-+--+1.567+2.890*4/5)".toCharArray();
        assertEquals(a, new CalculatorLexicalAnalysis().set(b).toString());
    }

    @Test
    public void testMethodNumber() throws Exception {
        String a = "[1, +, 10, *, abs, (, -1.567, +, 2.890, *, 4, /, 5, )]";
        char[] b = "1+10abs(-1.567+2.890*4/5)".toCharArray();
        assertEquals(a, new CalculatorLexicalAnalysis().set(b).toString());
    }

    @Test
    public void testOneWord() throws Exception {
        String a = "[1, +, a]";
        char[] b = "1+a".toCharArray();
        assertEquals(a, new CalculatorLexicalAnalysis().set(b).toString());
    }

    @Test
    public void testTwoWord() throws Exception {
        String a = "[1, +, as]";
        char[] b = "1+as".toCharArray();
        assertEquals(a, new CalculatorLexicalAnalysis().set(b).toString());
    }
}
