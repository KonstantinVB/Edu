package ru.otus.hwork05;

import ru.otus.hwork05.annotations.After;
import ru.otus.hwork05.annotations.Before;
import ru.otus.hwork05.annotations.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/*
 * reflection class.
 */
@SuppressWarnings("SameParameterValue")
public class MyRef {

    private MyRef() {
    }

    public static <T> T instantiate(Class<T> type, Object... args) {
        try {
            if (args.length == 0) {
                return type.newInstance();
            } else {
                return type.getDeclaredConstructor(toClasses(args)).newInstance(args);
            }
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object getFieldValue(Object object, String name) {
        Field field = null;
        boolean isAccessible = true;
        try {
            field = object.getClass().getDeclaredField(name); //getField() for public fields
            isAccessible = field.isAccessible();
            field.setAccessible(true);
            return field.get(object);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            if (field != null && !isAccessible) {
                field.setAccessible(false);
            }
        }
        return null;
    }

    public static void setFieldValue(Object object, String name, Object value) {
        Field field = null;
        boolean isAccessible = true;
        try {
            field = object.getClass().getDeclaredField(name); //getField() for public fields
            isAccessible = field.isAccessible();
            field.setAccessible(true);
            field.set(object, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            if (field != null && !isAccessible) {
                field.setAccessible(false);
            }
        }
    }

    static private Class<?>[] toClasses(Object[] args) {
        List<Class<?>> classes = Arrays.stream(args)
                .map(Object::getClass)
                .collect(Collectors.toList());
        return classes.toArray(new Class<?>[classes.size()]);
    }
// annotations searching and tests running
    public static void scanforrun(Class clas$) throws InstantiationException {
        Method beforeMethod = null;
        Method afterMethod = null;
        List<Method> testMethods = new ArrayList<>();
        for (Method method : clas$.getDeclaredMethods()) {
            Annotation testAnnotation = method.getAnnotation(Test.class);
            if (testAnnotation != null) {
                testMethods.add(method);
            }
            Annotation beforeAnnotation = method.getAnnotation(Before.class);
            if (beforeAnnotation != null) {
                beforeMethod = method;
            }
            Annotation afterAnnotation = method.getAnnotation(After.class);
            if (afterAnnotation != null) {
                afterMethod = method;
            }
        }
        for (Method testMethod : testMethods) {
            try {
                Object testInstance = clas$.newInstance();
                beforeMethod.invoke(testInstance);
                testMethod.invoke(testInstance);
                afterMethod.invoke(testInstance);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}
