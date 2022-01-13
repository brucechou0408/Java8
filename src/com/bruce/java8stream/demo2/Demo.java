package com.bruce.java8stream.demo2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

public class Demo {
    public static List<Apple> inventory = new ArrayList<Apple>() {{
        add(new Apple("red", 10));
        add(new Apple("red", 11));
        add(new Apple("red", 12));
        add(new Apple("red", 13));
        add(new Apple("green", 10));
        add(new Apple("green", 11));
        add(new Apple("green", 12));
        add(new Apple("green", 13));
    }};

    public static void main(String[] args) {
        // 传递代码
        inventory.sort(new AppleComparator());
        // 匿名内部类
        inventory.sort(new Comparator<Apple>() {
            @Override
            public int compare(Apple o1, Apple o2) {
                return o1.getWeight().compareTo(o2.getWeight());
            }
        });
        // lambda表达式
        inventory.sort((a1, a2) -> a1.getWeight().compareTo(a2.getWeight()));
        // import static java.util.Comparator.comparing;
        inventory.sort(comparing(a -> a.getWeight()));
        // 方法引用
        inventory.sort(comparing(Apple::getWeight));
        // --- 比较器复合 ---
        // 逆序 -> 苹果按重量递减排序
        inventory.sort(comparing(Apple::getWeight).reversed());
        // 比较器链 -> 相同重量比较颜色
        inventory.sort(comparing(Apple::getWeight).reversed().thenComparing(Apple::getColor));
        // --- 谓词复合 ---
        // negate、and、or
        // 红
        Predicate<Apple> redApple = (a -> a.getColor().equals("red"));
        // 非红
        Predicate<Apple> notRedApple = redApple.negate();
        // 非红重量大于11
        notRedApple.and(a -> a.getWeight() > 11);
        // a&b||c 红色重量大于11 绿色全部
        Predicate chain = redApple.and(a -> a.getWeight() > 11).or(a -> a.getColor().equals("green"));
        inventory.stream().filter(chain).collect(Collectors.toList());
        // 函数复合
        // andThen g(f(1))
        Function<Integer, Integer> f = x -> x + 1;
        Function<Integer, Integer> g = x -> x * 3;
        System.out.println(f.andThen(g).apply(1));
        // compose f(g(1))
        System.out.println(f.compose(g).apply(1));
        Function<String, String> addHeader = Letter::addHeader;
        Function<String, String> full = addHeader.andThen(Letter::addFooter).andThen(Letter::toUpperCase);
        System.out.println(full.apply("abcdefg"));


    }

    public static class Letter {
        public static String addHeader(String str) {
            return "dear sir \n" + str;
        }

        public static String addFooter(String str) {
            return str + "\n此致敬礼";
        }

        public static String toUpperCase(String str) {
            return str.toUpperCase();
        }

    }
}
