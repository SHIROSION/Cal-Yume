
/*
 * @package: PACKAGE_NAME
 * @program: Cal-Yume
 * @description
 *
 * @author:  rinne
 * @e-mail:  minami.rinne.me@gmail.com
 * @date: 2019/05/12 下午 09:44
 */

import java.util.ArrayList;

/**
 * @classname: CalculatorStack
 * @description: %{description}
 * @author: rinne
 * @date: 2019/05/12 下午 09:44
 * @Version 1.0
 */
public class CalculatorStack<E> {
    private ArrayList<E> arrayList;

    public CalculatorStack() {
        this.arrayList = new ArrayList<E>();
    }

    public void add(E o) {
        this.arrayList.add(o);
    }

    public int getSize() {
        return this.arrayList.size();
    }

    public E peek() {
        return this.arrayList.get(getSize() - 1);
    }

    public E pop() {
        E o = peek();
        this.arrayList.remove(getSize() - 1);
        return o;
    }

    public boolean isEmpty() {
        return this.arrayList.isEmpty();
    }

}
