
/*
 * @package: PACKAGE_NAME
 * @program: Cal-Yume
 * @description
 *
 * @author:  rinne
 * @e-mail:  minami.rinne.me@gmail.com
 * @date: 2019/05/19 上午 12:57
 */

import java.util.Scanner;

/**
 * @classname: CalculatorMain
 * @description: %{description}
 * @author: rinne
 * @date: 2019/05/19 上午 12:57
 * @Version 1.0
 */
public class CalculatorMain {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.next();
        char[] a = input.toCharArray();
        CalculatorControl calculatorControl = new CalculatorControl(a);
        System.out.println(calculatorControl.getResult());
        return;
    }
}
