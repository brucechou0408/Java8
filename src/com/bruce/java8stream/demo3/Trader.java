package com.bruce.java8stream.demo3;

public class Trader {

    private final String name;
    private final String city;

    public Trader(String name, String city) {
        this.name = name;
        this.city = city;
    }

    @Override
    public String toString() {
        return "Trader" +this.name + "in" + city;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }
}
