
/*
 * @package: PACKAGE_NAME
 * @program: Cal-Yume
 * @description
 *
 * @author:  rinne
 * @e-mail:  minami.rinne.me@gmail.com
 * @date: 2019/05/12 下午 11:17
 */

import java.util.Scanner;

/**
 * @classname: main
 * @description: %{description}
 * @author: rinne
 * @date: 2019/05/12 下午 11:17
 * @Version 1.0
 */
public class main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.next();
        char[] a = input.toCharArray();
        CalculatorControl calculatorControl = new CalculatorControl(a);
        return;
    }
}
