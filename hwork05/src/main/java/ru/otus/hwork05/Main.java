package ru.otus.hwork05;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/*
 * Run Test Framework.
 */
public class Main {
    public static void main(String... args) throws InterruptedException, ClassNotFoundException, InstantiationException, IOException {
        System.out.println("**** TESTS IN CLASS ****");
        runTestsInClass("ru.otus.hwork05.MyRefTest");
        System.out.println("**** TESTS IN PACKAGE ****");
        runTestsInPackages("ru.otus.hwork05");
    }

    static void runTestsInClass(String ...classNames) throws ClassNotFoundException, InstantiationException {
        for (String className: classNames){
            Class clas$ = Class.forName(className);
            if(clas$ != null){
                MyRef.scanforrun(clas$);
            }
        }
    }

    static void runTestsInPackages(String ...packageName) throws InstantiationException {
        final Reflections reflections = new Reflections(packageName, new SubTypesScanner(false));
        final List<Class<?>> classes = reflections.getSubTypesOf(Object.class)
                .stream()
                .filter(cla$$ -> Object.class.equals(cla$$.getSuperclass()))
                .collect(Collectors.toList());
        for (Class clas$: classes) {
            MyRef.scanforrun(clas$);
        }
    }
}
