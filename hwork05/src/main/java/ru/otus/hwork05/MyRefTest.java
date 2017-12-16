package ru.otus.hwork05;

import ru.otus.hwork05.annotations.After;
import ru.otus.hwork05.annotations.Before;
import ru.otus.hwork05.annotations.Test;

/*
 * tests
 */
public class MyRefTest {
    public MyRefTest() {
    }
    @SuppressWarnings("ConstantConditions")
    @Before
    public void beforeTest() {
        System.out.println("Test started");
    }

    @After
    public void afterTest() {
        System.out.println("Test finished");
    }

    @Test
    public void instantiate() {
        MyTestClass testClass = MyRef.instantiate(MyTestClass.class);
        System.out.println("Instantiate Test completed");
    }

    @Test
    public void getFieldValue() {
        MyTestClass test = new MyTestClass(1, "A");
        System.out.println("getFieldValue Test completed with values: "
                +MyRef.getFieldValue(test, "a")+";"
                +MyRef.getFieldValue(test, "s"));
    }

    @Test
    public void setFieldValue() {
        MyTestClass test = new MyTestClass(2, "B");
        MyRef.setFieldValue(test, "s", "BB");
        System.out.println("setFieldValue Test completed with values: "
                +MyRef.getFieldValue(test, "a")+";"
                +MyRef.getFieldValue(test, "s"));
    }
}
