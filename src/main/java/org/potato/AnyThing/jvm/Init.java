package org.potato.AnyThing.jvm;


/**装箱拆箱经典测试
 * Created by potato on 2018/4/24.
 */
public class Init {

    final int fb =2 ;
    public static void main(String[] args) {

        Integer a = 1;
        Integer b = 2;
        Integer c = 3;
        Integer d = 3;
        Integer e = 321;
        Integer f = 321;
        Long g = 3L;
        int aa = 3;
        double ab = 3.0;


        //fb=2;

        //true
        System.out.println(c == d);
        //false
        System.out.println(e == f);
        //true
        System.out.println(c == (a + b));
        //true
        System.out.println(c.equals(a + b));
        //true
        System.out.println(g == (a + b));
        //false
        System.out.println(g.equals(a + b));


    }
}



