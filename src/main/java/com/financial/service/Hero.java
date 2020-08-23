package com.financial.service;

import java.util.HashMap;

public class Hero {

    public static void main(String[] args) {
        String testString = "accepted_date=2018-05-10T23:00:00.0%2D01:00";

        String[] parts = testString.split("&");
        HashMap<String, Object> map = new HashMap<>();


        for (String part : parts) {
            String[] keyAndValue = part.split("=");
            String key = keyAndValue[0];
            String value = keyAndValue[1];
            map.put(key, value);

        }

        map.forEach((x, y) -> System.out.println("Key :" + x + "  Value :" + y));

    }
}
