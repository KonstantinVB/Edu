package ru.otus.hwork11;

/*
На основе предыдущего ДЗ (HibernateORM):
Напишите свой cache engine с soft references.
Добавьте кэширование в DBService
*/

import ru.otus.hwork11.DS.*;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * VM options -Xmx512m -Xms512m
 */


public class Main {
    private static final long WORK_TIME_MS = TimeUnit.MINUTES.toMillis(5);
    private static final long STEP_TIME_MS = TimeUnit.SECONDS.toMillis(5);

    public static void main(String... args) throws InterruptedException {
        testHibernate();
    }

    private static void testHibernate() throws InterruptedException {
        UserDataSet uDataSet;
        DBService dbService = new DBServiceHibernateImpl();
        try {
            System.out.println("$$$$$$$$$$ Status: " + dbService.getLocalStatus());
            dbService.insert(new UserDataSet("Vasya", 35, new AddressDataSet("620137, Ekaterinburg, Vilonov str., 1-1"),new PhoneDataSet("8888888")));
            dbService.insert(new UserDataSet("Petya", 45, new AddressDataSet("620039, Ekaterinburg, Mashinostroiteley str., 8-8"), new PhoneDataSet("9999999")));
            uDataSet = dbService.select(2,UserDataSet.class);
            dbService.insert(new PhoneDataSet("7777777",uDataSet));
            dbService.update("number","7777777","number","5555555", PhoneDataSet.class);
            dbService.update("street","620039, Ekaterinburg, Mashinostroiteley str., 8-8","street","620000,  Ekaterinburg, Lenina str., 8-8", AddressDataSet.class);
            System.out.println(uDataSet);
            uDataSet = dbService.selectByName("name","Vasya",UserDataSet.class);
            System.out.println(uDataSet);
            List<UserDataSet> luDS = dbService.selectAll(UserDataSet.class);
            for (DataSet ds : luDS) {
                System.out.println(ds);
            }
            List<AddressDataSet> laDS = dbService.selectAll(AddressDataSet.class);
            for (DataSet ds : laDS) {
                System.out.println(ds);
            }
            List<PhoneDataSet> lpDS = dbService.selectAll(PhoneDataSet.class);
            for (DataSet ds : lpDS) {
                System.out.println(ds);
            }
            long startTime = System.currentTimeMillis();
            int i=0;
            while (System.currentTimeMillis() - startTime < WORK_TIME_MS) {
                i++;
                System.out.println(dbService.select(2, UserDataSet.class));
                i++;
                System.out.println(dbService.selectByName("name", "Vasya", UserDataSet.class));
                Thread.sleep(STEP_TIME_MS);
            }
            System.out.println("Total count: "+i);
        } finally {
            dbService.shutdown();
        }
    }

}