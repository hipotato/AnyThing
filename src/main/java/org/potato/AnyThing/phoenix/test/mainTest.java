package org.potato.AnyThing.phoenix.test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by potato on 2018/4/20.
 */
public class mainTest {
    public static void main(String[] args) {
        List<String>  titles = new ArrayList<>();

        titles.add("颐和园");
        titles.add("a和园");

        for(String title:titles){
            System.out.println(title+":"+title.length());
        }

    }
}
