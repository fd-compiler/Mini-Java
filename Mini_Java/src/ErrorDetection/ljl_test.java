package ErrorDetection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ljl_test {
    public static void main(String[]args){
        List<String> list = new LinkedList<>();
        list.add("ljl");
        list.add("ohl");
        list.add("tx");
        int a[]=new int [10];
        System.out.println(a[10]);

        System.out.println(list.get(0));
        System.out.println(list.get(1));
        System.out.println(list.get(2));
        ((LinkedList<String>) list).removeLast();
        System.out.println(list.get(0));
        System.out.println(list.get(1));


    }
}