package com.bruce.java8stream.demo1;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Demo2 {
    public static void main(String[] args) {
        int i = 10;

        List<Integer> list = new ArrayList(i);
        Random random= new Random();
        for (int j = 0; j < i; j++) {
            list.add(random.nextInt());
        }
        long timeA = System.currentTimeMillis();
        list.stream().forEach(item->{
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        long timeB = System.currentTimeMillis();
        System.out.println(timeB-timeA);
    }
}
