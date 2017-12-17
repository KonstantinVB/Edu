package ru.otus.hwork05.annotations;

public class Assert {
    private Assert(){}

    public static void assertEquals(Object expected, Object result){
        if(!result.equals(expected)){
            fail (result);
        } else success(result);
    }

    private static void fail(Object result){
        try {
            throw new AssertionError(String.format("Unsuccessfull result:<%s>",result));
        } catch (AssertionError e) {
            System.out.println(e);
        }
    }

    private static void success(Object result){
        System.out.println(String.format("Successfull result:<%s>",result));
    }

}
