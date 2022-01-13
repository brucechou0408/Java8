package com.bruce.java8stream.demo3;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.maxBy;
import static java.util.stream.Collectors.toList;

public class Demo {

    public static void main(String[] args) {
        List<Dish> menu = Arrays.asList(
                new Dish("pork", false, 800, Dish.Type.MEAT),
                new Dish("beaf", false, 700, Dish.Type.MEAT),
                new Dish("chicken", false, 400, Dish.Type.MEAT),
                new Dish("french fries", true, 530, Dish.Type.OTHER),
                new Dish("rice", false, 350, Dish.Type.OTHER),
                new Dish("season fruit", true, 120, Dish.Type.OTHER),
                new Dish("pizza", true, 550, Dish.Type.OTHER),
                new Dish("prawns", false, 300, Dish.Type.FISH),
                new Dish("salmon", false, 450, Dish.Type.FISH)
        );
        List<Dish> threeHigHCalories =
                menu.stream()
                        .filter(a -> a.getCalories() > 300)
                        .sorted(comparing(Dish::getCalories).reversed())
                        .limit(3)
                        .collect(toList());
        System.out.println(threeHigHCalories);
        // foreach迭代
        List<String> foreachList = new ArrayList<>();
        for (Dish dish : menu) {
            foreachList.add(dish.getName());
        }
        // iteraotor迭代
        List<String> iteratorList = new ArrayList<>();
        Iterator<Dish> iterator = menu.iterator();
        while (iterator.hasNext()) {
            Dish d = iterator.next();
            iteratorList.add(d.getName());
        }
        // stream 迭代
        List<String> streamList = new ArrayList();
        streamList = menu.stream().map(Dish::getName).collect(toList());
        System.out.println(foreachList);
        System.out.println(iteratorList);
        System.out.println(streamList);
        // ['hello','word']->['h','e','l','l','o','w','o','r','d']
//        String[] words = new String[]{"hello","word"};
        String[] strArr = {"hello", "word"};
        List<String[]> error1 = Arrays.stream(strArr).map(word -> word.split("")).distinct().collect(toList());
        List<Stream<String>> error2 = Arrays.stream(strArr)
                .map(word -> word.split(""))
                .map(Arrays::stream)
                .distinct().collect(toList());
        System.out.println(error1);
        List<String> correct = Arrays.stream(strArr)
                .map(word -> word.split(""))
                .flatMap(Arrays::stream)
                .distinct().collect(toList());
        System.out.println(correct);
        // 获取平方
        List<Integer> numberList = Arrays.stream(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
                .map(i -> i * i).collect(toList());
        System.out.println(numberList);

        List<Integer> number1 = Arrays.asList(1, 2, 3);
        List<Integer> number2 = Arrays.asList(4, 5);
        // flat数组
        List<int[]> intList = number1.stream().flatMap(i -> number2.stream().map(j -> new int[]{i, j})).collect(toList());
        intList.stream().forEach(i -> System.out.println(i[0]+","+i[1]));
        // reduce 归约
        // 求和
        int sum = Arrays.stream(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}).reduce(0,(a,b)->a+b);
        Arrays.stream(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}).reduce(0,Integer::sum);
        System.out.println("sum="+sum);
        // 最大值
        Optional max = Arrays.stream(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}).reduce(Integer::max);
        // 最小值
        Optional min = Arrays.stream(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}).reduce(Integer::min);

        System.out.println("max="+max.get());
        System.out.println("min="+min.get());

        Optional optional = menu.stream()
                .collect(maxBy(comparing(Dish::getCalories)));
        System.out.println(optional.get());

    }
}
