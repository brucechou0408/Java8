package com.bruce.java8stream.demo3;

/**
 * @author bruce
 */
public class Dish {
    private final String name;
    private final boolean vegetraian;
    private final int calories;
    private final Type type;

    public Dish(String name, boolean vegetraian, int calories, Type type) {
        this.name = name;
        this.vegetraian = vegetraian;
        this.calories = calories;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public boolean isVegetraian() {
        return vegetraian;
    }

    public int getCalories() {
        return calories;
    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        return name;
    }

    public enum Type {MEAT,FISH,OTHER}


}
