package ru.otus.hwork01;

import org.apache.commons.lang3.Conversion;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Enter 4 bits to convert using the Msb0 bit ordering:");
        Scanner in = new Scanner(System.in);
        int numa = in.nextInt();;
        System.out.println("Converted: " +
                Conversion.intToHexDigitMsb0(numa));
    }
}