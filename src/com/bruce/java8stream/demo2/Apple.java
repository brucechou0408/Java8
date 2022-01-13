package com.bruce.java8stream.demo2;

import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Apple {
    private String color;
    private Integer weight;

    public Apple(String color, Integer weight) {
        this.color = color;
        this.weight = weight;
    }

    public Apple() {
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }
    public void test(Predicate<? super Apple> predicate,ArrayList<Apple> list) {
        list.stream().filter(predicate).collect(Collectors.toList());

    }
}
