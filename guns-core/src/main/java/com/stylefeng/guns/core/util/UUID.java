package com.stylefeng.guns.core.util;

import java.util.Random;

public class UUID {

    public static synchronized String genUniqueKey(){

        Random random = new Random();
        Integer a = random.nextInt(900000) + 100000;
        return System.currentTimeMillis() + String.valueOf(a);
    }
}
