package com.demo.courseworkbank.utils;

public class BillDataRandomizer {

    public static String getRandomBillNumber() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            result.append(getRandomNumber(0, 9));
        }
        return result.toString();
    }

    public static double getRandomMoneys(){
        return getRandomNumber(100, 100000);
    }

    private static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
