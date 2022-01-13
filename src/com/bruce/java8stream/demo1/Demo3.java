package com.bruce.java8stream.demo1;

import java.util.ArrayList;
import java.util.List;

public class Demo3 {
    public static void main(String[] args) {
        filterApple(new ArrayList<Apple>(), p -> p.getWeight()>10);
    }

    public static List<Apple> filterApple(List<Apple> inventory,ApplePredicate p){
        List<Apple> result = new ArrayList<Apple>();
        for (Apple apple : inventory) {
            if(p.test(apple)){
                result.add(apple);
            }
        }
        return result;
    }
}
