package ru.otus.hwork08;

import org.junit.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/*
 * Тестируем собственный json object writer (object to json string),
 * аналогичный gson на основе simple-json и Reflection.
 * Поддерживает массивы объектов и примитивных типов, и коллекции из стандартный библиотерки.
 */
public class Main {
    public static void main(String... args) throws InstantiationException, ClassNotFoundException {
        Class clas$ = Class.forName("ru.otus.hwork08.MyWriterTest");
        List<Method> testMethods = new ArrayList<>();
        for (Method method : clas$.getDeclaredMethods()) {
            Annotation testAnnotation = method.getAnnotation(Test.class);
            if (testAnnotation != null) {
                testMethods.add(method);
            }
        }
        for (Method testMethod : testMethods) {
            try {
                Object testInstance = clas$.newInstance();
                testMethod.invoke(testInstance);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }

    }
}
