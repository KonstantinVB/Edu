package ru.otus.hwork08;

import com.google.gson.Gson;
import org.junit.Test;
import java.util.*;
import static org.junit.Assert.assertEquals;

public class MyWriterTest {
    private final Gson gson = new Gson();
    private final MyWriter myWriter = new MyWriter();

    @Test
    public void stringToJson() {
        final String testString = "Test String for JSON";
        System.out.println(String.format("Serialized Object is String: %s",this.gson.toJson(testString)));
        assertEquals(this.gson.toJson(testString), this.myWriter.toJson(testString));
    }
    @Test
    public void primitiveToJson() {
        final byte testByte = 127;
        final short testShort = 32767;
        final int testInt = 2147483647;
        final long testLong = 2147483648L;
        final float testFloat = 3.4e+38F;
        final double testDouble = 3.5e+38;
        final boolean testBoolean = true;
        final char testChar = 'h';
        System.out.println(String.format("Serialized Object is byte: %s",this.gson.toJson(testByte)));
        assertEquals(this.gson.toJson(testByte), this.myWriter.toJson(testByte));
        System.out.println(String.format("Serialized Object is short: %s",this.gson.toJson(testShort)));
        assertEquals(this.gson.toJson(testShort), this.myWriter.toJson(testShort));
        System.out.println(String.format("Serialized Object is int: %s",this.gson.toJson(testInt)));
        assertEquals(this.gson.toJson(testInt), this.myWriter.toJson(testInt));
        System.out.println(String.format("Serialized Object is long: %s",this.gson.toJson(testLong)));
        assertEquals(this.gson.toJson(testLong), this.myWriter.toJson(testLong));
        System.out.println(String.format("Serialized Object is float: %s",this.gson.toJson(testFloat)));
        assertEquals(this.gson.toJson(testFloat), this.myWriter.toJson(testFloat));
        System.out.println(String.format("Serialized Object is double: %s",this.gson.toJson(testDouble)));
        assertEquals(this.gson.toJson(testDouble), this.myWriter.toJson(testDouble));
        System.out.println(String.format("Serialized Object is boolean: %s",this.gson.toJson(testBoolean)));
        assertEquals(this.gson.toJson(testBoolean), this.myWriter.toJson(testBoolean));
        System.out.println(String.format("Serialized Object is char: %s",this.gson.toJson(testChar)));
        assertEquals(this.gson.toJson(testChar), this.myWriter.toJson(testChar));
    }
    @Test
    public void primArrayToJson() {
        final Object[] testArray = new Object[]{
                "first",
                127,
                32767,
                2147483647,
                2147483648L,
                3.4e+38F,
                3.5e+38,
                'h',
                true,
                null
        };
        System.out.println(String.format("Serialized Object is array of Primitives: %s",this.gson.toJson(testArray)));
        assertEquals(this.gson.toJson(testArray), this.myWriter.toJson(testArray));
    }
    @Test
    public void objectToJson() {
        final MyObject testObject = new MyObject (1,"a");
        System.out.println(String.format("Serialized Object is: %s",this.gson.toJson(testObject)));
        assertEquals(this.gson.toJson(testObject), this.myWriter.toJson(testObject));
    }
    @Test
    public void objArrayToJson() {
        final Object[] testObjArray = new Object[] {
            new MyObject (1,"a"),
            new MyObject (2,"b"),
            new MyObject (3,"c")};
        System.out.println(String.format("Serialized Object is array of Objects: %s",this.gson.toJson(testObjArray)));
        assertEquals(this.gson.toJson(testObjArray), this.myWriter.toJson(testObjArray));
    }
    @Test
    public void ListToJson() {
        final List<Object> testList = Arrays.asList(
                "first",
                127,
                32767,
                2147483647,
                2147483648L,
                3.4e+38F,
                3.5e+38,
                'h',
                true,
                null
        );
        System.out.println(String.format("Serialized Object is List: %s",this.gson.toJson(testList)));
        assertEquals(this.gson.toJson(testList), this.myWriter.toJson(testList));
    }
    @Test
    public void mapToJson() {
        final Map<Integer, Object> testMap = new HashMap<>();
        testMap.put(1,"first");
        testMap.put(2,127);
        testMap.put(3,32767);
        testMap.put(4,2147483647);
        testMap.put(5,2147483648L);
        testMap.put(6,3.4e+38F);
        testMap.put(7,3.5e+38);
        testMap.put(8,'h');
        testMap.put(9,true);
        testMap.put(10,null);
        System.out.println(String.format("Serialized Object is Map: %s",this.gson.toJson(testMap)));
        assertEquals(this.gson.toJson(testMap), this.myWriter.toJson(testMap));
    }
    @Test
    public void setToJson() {
        final Set<Object> testSet = new HashSet<>();
        testSet.add("first");
        testSet.add(127);
        testSet.add(32767);
        testSet.add(2147483647);
        testSet.add(2147483648L);
        testSet.add(3.4e+38F);
        testSet.add(3.5e+38);
        testSet.add('h');
        testSet.add(true);
        testSet.add(null);
        System.out.println(String.format("Serialized Object is Set: %s",this.gson.toJson(testSet)));
        assertEquals(this.gson.toJson(testSet), this.myWriter.toJson(testSet));
    }
}
