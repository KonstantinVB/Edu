package ru.otus.hwork09;

/*
Создайте в базе таблицу с полями:
id bigint(20) NOT NULL auto_increment
name varchar(255)
age int(3)

Создайте абстрактный класс DataSet. Поместите long id в DataSet.
Добавьте класс UserDataSet (с полями, которые соответствуют таблице),
унаследуйте его от DataSet.

<T extends DataSet> void save(T user){…}
<T extends DataSet> T load(long id, Class<T> clazz){…}

Напишите Executor, который сохраняет наследников DataSet в базу
и читает их из базы по id и классу.


*/

import java.util.Scanner;

public class Main {
    public static void main(String... args) {
        MyExecutor executor = new MyExecutor();
        UserDataSet uds;
        executor.create(UserDataSet.class);
        for (int i = 0; i < 5; i++) {
            uds= new UserDataSet("People"+i,18+i);
            executor.insert(uds);
        }
        System.out.println(executor.select(3L, UserDataSet.class));
        System.out.println(executor.selectAll(UserDataSet.class));
        System.out.println("To drop DataSet from DB press Y else press N:");
        Scanner in = new Scanner(System.in);
        if (in.next().toString().equals("Y")) {
            executor.drop(UserDataSet.class);
        }
    }
}