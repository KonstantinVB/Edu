package ru.otus.hwork03;

import java.util.Collections;
import static java.util.Comparator.naturalOrder;
import java.util.Scanner;

public class Main {
    public static void main(String... args) {
        MyArrayList<String> arrlist = new MyArrayList<String>();
        System.out.println("Enter Array elements with comma delimiters:");
        Scanner in = new Scanner(System.in);
        String [] instr = in.next().split(",");
//Проверяем добавление
        boolean b = Collections.addAll(arrlist,instr);
        System.out.print(arrlist.size()+"-el array has been written by addAll: ");
        System.out.print(arrlist.toString());
        System.out.println();
//Проверяем копирование
        MyArrayList<String> arrlistN = new MyArrayList<String>(arrlist.size());
        Collections.copy(arrlistN,arrlist);
        System.out.print(arrlistN.size()+"-el array has been written by copy:   ");
        System.out.print(arrlistN.toString());
        System.out.println();
//Проверяем сортировку
        Collections.sort(arrlistN, naturalOrder());
        System.out.print(arrlistN.size()+"-el array has been ordered by sort:   ");
        System.out.print(arrlistN.toString());
        System.out.println();
    }
}
