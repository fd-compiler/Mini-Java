package ErrorDetection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ljl_test {
    public static int shit(){return 0;}
    public static void main(String[]args){
        List<String> list = new ArrayList<>();
        list.add("ljl");
        list.add(null);
        list.add(null);
        list.add("ohl");
        list.add(null);
        System.out.println(list.size());
        list.set(4,"tx");
        for(int i=0;i<list.size();i++){
            System.out.println(list.get(i));
        }
        int []a = new int[0];
        System.out.println("luelueleu");
    }
}