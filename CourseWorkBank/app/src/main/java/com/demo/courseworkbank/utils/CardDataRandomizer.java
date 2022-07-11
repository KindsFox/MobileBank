package com.demo.courseworkbank.utils;

import java.util.Date;

public class CardDataRandomizer {

    public static String getCardNumber() {
        StringBuilder cardNumber = new StringBuilder("4210");

        for(int i = 0; i < 3;i++) {
            cardNumber.append(" ");
            cardNumber.append(getRandomNumber(1000, 9999));
        }
        return String.valueOf(cardNumber);
    }

    public static int getCardCvv(){
        return getRandomNumber(100,999);
    }

    public static Date getCardDate(){
        Date date = new Date();
        long time = date.getTime() + 1440L * 24 * 60 * 60 * 1000;
        return new Date(time);
    }

    private static int getRandomNumber(int min, int max){
        return (int) ((Math.random() * (max - min)) + min);
    }
}
