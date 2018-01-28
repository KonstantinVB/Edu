package ru.otus.hwork10;

/*
На основе предыдущего ДЗ (myORM):
1. Оформить решение в виде DBService (interface DBService, class DBServiceImpl, UsersDAO, UsersDataSet, Executor)
2. Не меняя интерфейс DBSerivice сделать DBServiceHibernateImpl на Hibernate.
3. Добавить в UsersDataSet поля:
адресс (OneToOne) 
class AddressDataSet{
private String street;
}
и телефон* (OneToMany)
class PhoneDataSet{
private String number;
}
Добавить соответствущие датасеты и DAO. 
*/

import ru.otus.hwork10.DS.*;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String... args) {
        testmyORM();
        testHibernate();
    }

    private static void testHibernate(){
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
        } finally {
            dbService.shutdown();
        }
    }

    private static void testmyORM(){
        UserDataSet uDataSet;
        DBService dbService = new DBServiceMyImpl();
        try {
            System.out.println("$$$$$$$$$$ Status: " + dbService.getLocalStatus());
            for (int i = 0; i < 5; i++) {
                uDataSet= new UserDataSet("People"+i,18+i);
                dbService.insert(uDataSet);
            }
            System.out.println(dbService.select(3L, UserDataSet.class));
            System.out.println(dbService.selectAll(UserDataSet.class));
            System.out.println("To drop DataSet from DB press Y else press N:");
            Scanner in = new Scanner(System.in);
            if (in.next().toString().equals("Y")) {
                dbService.drop();
            }
        } finally {
            dbService.shutdown();
        }
    }
}