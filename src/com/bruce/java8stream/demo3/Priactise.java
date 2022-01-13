package com.bruce.java8stream.demo3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

public class Priactise {
    public static void main(String[] args) {
        Trader raoul = new Trader("Raoul","Cambridge");
        Trader mario = new Trader("Mario","Milan");
        Trader alan = new Trader("Alan","Cambridge");
        Trader brian = new Trader("Brian","Cambridge");
        List<Transaction> transactions = Arrays.asList(
                new Transaction(brian,2011,300),
                new Transaction(raoul,2012,1000),
                new Transaction(raoul,2011,400),
                new Transaction(mario,2012,710),
                new Transaction(mario,2012,700),
                new Transaction(alan,2012,950)
        );
        // 2011年发生的交易，按交易额降序
        List<Transaction> demo1=  transactions.stream()
                .filter(t->t.getYear()==2011)
                .sorted(comparing(Transaction::getValue).reversed())
                .collect(toList());
        System.out.println(demo1);
        // 交易员在那些城市工作过
        List<String> demo2 =
        transactions.stream()
                .map(transaction-> transaction.getTrader().getCity())
                .distinct()
                .collect(toList());
        System.out.println(demo2);
        // 查找来自剑桥的交易员，按照姓名排序
        List<Trader> demo3 =
                transactions.stream()
                        .map(transaction-> transaction.getTrader())
                        .filter(t->t.getCity().equals("Cambridge"))
                        .distinct()
                        .sorted(comparing(Trader::getName))
                        .collect(toList());
        System.out.println(demo3);
        // 返回交易员姓名字符串，按字母顺序排序
        List<String> demo4 =
                transactions.stream()
                        .map(transaction-> transaction.getTrader().getName())
                        .distinct()
                        .sorted(comparing(String::valueOf))
                        .collect(toList());
        System.out.println(demo4);
        // 有没有在米兰工作过
        Optional demo5=transactions.stream()
                .map(transaction-> transaction.getTrader().getCity()).filter(a->a.equals("Milan")).findAny();
        System.out.println(demo5.isPresent());
        // 打印所有剑桥交易员的交易额
        int demo6 =
                transactions.stream()
                        .filter(transaction-> transaction.getTrader().getCity().equals("Cambridge"))
                        .map(Transaction::getValue)
                        .reduce(0,Integer::sum);
        System.out.println(demo6);
        // 最高
        int demo7 = transactions.stream()
                .map(Transaction::getValue)
                .reduce(0,Integer::max);
        System.out.println(demo7);
        // 最高
        Optional<Transaction> demo8 = transactions.stream()
                .reduce((a,b)->a.getValue()<b.getValue()?a:b);
        System.out.println(demo8);
    }
}
