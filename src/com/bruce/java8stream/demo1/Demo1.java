package com.bruce.java8stream.demo1;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Demo1 {

    public static class Person {
        private int age;

        private String name;

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }


        public Person(int age) {
            this.age = age;
        }
    }

    public static void main(String[] args) {
        int j = 100000000;
        List<Person> list = new ArrayList(j);
        List<Person> result = new ArrayList<>();
        List<Person> result2 = new ArrayList<>();
        List<Person> result3 = new ArrayList<>();
        System.out.println("开始插入数据");
        long timeA1 = System.currentTimeMillis();
        Random random = new Random();
        for(int i = 0; i < j; i++) {
            list.add(new Person(random.nextInt()));
        }
        long timeA2 = System.currentTimeMillis();
        System.out.println("插入完毕耗时"+(timeA2 -timeA1));
        long timeA = System.currentTimeMillis();
        for (Person person : list) {
            if(person.getAge()<200){
                result.add(person);
            }
        }
        long timeB = System.currentTimeMillis();
        System.out.println(timeB-timeA);
        timeB = System.currentTimeMillis();
        result2 = list.parallelStream().filter(person -> person.getAge()<200).collect(Collectors.toList());
        long timeC = System.currentTimeMillis();
        list.forEach(person -> {
            if(person.getAge()<200 ){
                result3.add(person);
            }
        });
        long timeD = System.currentTimeMillis();
        System.out.println(timeC - timeB);
        System.out.println(timeD - timeC);
        System.out.println(result.size());
        System.out.println(result2.size());
        System.out.println(result3.size());


    }
}
