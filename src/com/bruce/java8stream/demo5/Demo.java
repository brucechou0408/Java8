package com.bruce.java8stream.demo5;


import java.util.*;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.*;

public class Demo {
    public static void main(String[] args) {
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");
        List<Transaction> transactions = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );
//        transactions.stream().collect(toList())
        // min/max
        Optional<Transaction> optional = transactions.stream().collect(maxBy(comparing(Transaction::getValue)));
        System.out.println(optional.get());
        // 汇总
        // 求和
        int sum = transactions.stream().collect(summingInt(Transaction::getValue));
        System.out.println(sum);
        // 平均数
        double avg = transactions.stream().collect(averagingInt(Transaction::getValue));
        System.out.println(avg);
        // 统计
        IntSummaryStatistics intSummaryStatistics = transactions.stream().collect(summarizingInt(Transaction::getValue));
        System.out.println(intSummaryStatistics);
        // 链接字符串
        String str = transactions.stream().map(t -> t.getTrader().getName()).collect(joining(","));
        System.out.println(str);
        // 分组
        Map<Integer, List<Transaction>> map = transactions.stream().collect(groupingBy(Transaction::getYear));
        System.out.println(map);
        Map<String, List<Transaction>> map2 = transactions.stream().collect(groupingBy(t -> {
            if (t.getValue() < 300) return "低";
            return "高";
        }));
        System.out.println(map2);
        // 多级分组
        Map<Integer, Map<String, List<Transaction>>> manyMap = transactions.stream().collect(groupingBy(Transaction::getYear, groupingBy(
                t -> {
                    if (t.getValue() < 300) return "低";
                    return "高";
                }
        )));
        System.out.println(manyMap);
        // 按子组收集收据
        Map<Integer, Long> countYear = transactions.stream().collect(groupingBy(Transaction::getYear, counting()));
        System.out.println(countYear);
        Map<Integer, Optional<Transaction>> yearMax = transactions.stream().collect(groupingBy(Transaction::getYear, maxBy(comparing(Transaction::getValue))));
        System.out.println(yearMax);
        // 每年最高的
        Map<Integer, Transaction> yearMax2 = transactions.stream()
                .collect(
                        groupingBy(
                                Transaction::getYear, collectingAndThen(
                                        maxBy(
                                                comparing(Transaction::getValue)
                                        ),Optional::get
                                )
                        )
                );
        System.out.println(yearMax2);
        // 联合收集器
        Map<Integer,Integer> yearSum =transactions.stream().collect(groupingBy(Transaction::getYear,summingInt(Transaction::getValue)));
        System.out.println(yearSum);
        Map<Integer,Set<Object>> mapSet =transactions.stream().collect(groupingBy(Transaction::getYear,mapping(
                t->{
                    if(t.getValue()<400) return "低";
                    if(t.getValue()<800) return "中";
                    return "高";
                },toSet()
        )));
        System.out.println(mapSet);

        Map<Integer,Set<String>> mapHashSet =transactions.stream().collect(groupingBy(Transaction::getYear,mapping(
                t->{
                    if(t.getValue()<400) return "低";
                    if(t.getValue()<800) return "中";
                    return "高";
                },toCollection(HashSet::new)
        )));
        // 分区 partitioningBy

        // 自定义收集器
        List<Transaction> myCollection = transactions.stream().collect(new ToListCollector<Transaction>());
        System.out.println(myCollection);

    }
}
