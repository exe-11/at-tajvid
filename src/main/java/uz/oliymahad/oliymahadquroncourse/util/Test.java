package uz.oliymahad.oliymahadquroncourse.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>(Arrays.asList("a", "b", "c"));
        list.remove("a");

        for (String s : list) {
            if (s.equals("a"))
                list.remove(s);
        }
    }
}
