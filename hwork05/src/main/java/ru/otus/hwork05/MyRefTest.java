package ru.otus.hwork05;

import ru.otus.hwork05.annotations.After;
import ru.otus.hwork05.annotations.Assert;
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
        Assert.assertEquals(MyTestClass.class, MyRef.instantiate(MyTestClass.class).getClass());
    }

    @Test
    public void getFieldValue() {
        MyTestClass test = new MyTestClass(1, "A");
        Assert.assertEquals(1, test.getA());
        Assert.assertEquals("A", test.getS());
    }

    @Test
    public void setFieldValue() {
        MyTestClass test = new MyTestClass(2, "B");
        MyRef.setFieldValue(test, "s", "BB");
        Assert.assertEquals("BB", test.getS());
    }
}
