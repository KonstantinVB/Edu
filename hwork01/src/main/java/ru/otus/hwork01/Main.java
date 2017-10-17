package ru.otus.hwork01;


import hirondelle.date4j.DateTime;
import java.util.TimeZone;

public class Main {
    public static void main(String[] args) {
        // Первый попавшийся HelloWorld с использованием внешней библиотеки
        System.out.println("Сегодня " +
                DateTime.now(TimeZone.getDefault()).getDayOfYear() +
                "й день года");
    }
}
