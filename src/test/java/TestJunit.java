
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

/**
 * @classname: TestJunit
 * @description: %{description}
 * @author: rinne
 * @date: 2019/05/14 下午 03:47
 * @Version 1.0
 */
public class TestJunit {
    @Test
    public void testAdd() {
        String str = "Junit is working fine";
        assertEquals("Junit is working fine", str);
    }
}
