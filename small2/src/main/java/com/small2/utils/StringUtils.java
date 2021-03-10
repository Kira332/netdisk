package com.small2.utils;


public class StringUtils {
    public static String getRandomString() {
        String randomString = "";
        String model = "0123456789abcdefghijklmnopqrstuvwxyz";
        char[] m = model.toCharArray();
        for (int j = 0; j < 6; j++) {
            char c = m[(int) (Math.random() * 36)];
            if (randomString.contains(String.valueOf(c))) {
                j--;
                continue;
            }
            randomString = randomString + c;
        }
        return randomString;
    }

}
