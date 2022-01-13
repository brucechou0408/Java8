package com.bruce.java8stream.demo5;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

import static java.util.stream.Collector.Characteristics.CONCURRENT;
import static java.util.stream.Collector.Characteristics.IDENTITY_FINISH;

public class ToListCollector<T> implements Collector<T, List<T>,List<T>> {
    @Override
    public Supplier<List<T>> supplier() {
//        return () -> new ArrayList<T>();
        // 创建空的累加器
        return ArrayList::new;
    }


    @Override
    public BiConsumer<List<T>, T> accumulator() {
        // 元素添加到结果容器
//        return (list,item)->list.add(item);
        return List::add;
    }


    @Override
    public BinaryOperator<List<T>> combiner() {
        // 合并容器结果
//        方法会返回一个供归约操作使用的函数，
//        它定义了对流的各个子部分进行并行处理时，
//        各个子部分归约所得的累加器要如何合并。
//        对于toList而言，这个方法的实现非常简单，
//        只要把从流的第二个部分收集到的项目列表加到遍历第一部分时得到的列表后面就行了
        return (list1,list2)->{
            list1.addAll(list2);
            return list1;
        };
//        return null;
    }

    @Override
    public Function<List<T>, List<T>> finisher() {
        // 对容器最终应用转换
        // list因为无需转换所以直接返回
        return Function.identity();
    }

    @Override
    public Set<Characteristics> characteristics() {
//        返回一个不可变的Characteristics集合，
//        它定义了收集器的行为——尤其是关于流是否可以并行归约，
//        以及可以使用哪些优化的提示。
//        Characteristics是一个包含三个项目的枚举。
//        UNORDERED——归约结果不受流中项目的遍历和累积顺序的影响。
//        CONCURRENT——accumulator函数可以从多个线程同时调用，
//        且该收集器可以并行归约流。如果收集器没有标为UNORDERED，
//        那它仅在用于无序数据源时才可以并行归约。
//        IDENTITY_FINISH——这表明完成器方法返回的函数是一个恒等函数，
//        可以跳过。这种情况下，累加器对象将会直接用作归约过程的最终结果。
//        这也意味着，将累加器A不加检查地转换为结果R是安全的。
//        我们迄今开发的ToListCollector是IDENTITY_FINISH的，因为用来累积流中元素的
//        List已经是我们要的最终结果，用不着进一步转换了，
//        但它并不是UNORDERED，因为用在有序流上的时候，
//        我们还是希望顺序能够保留在得到的List中。
//        最后，它是CONCURRENT的，但我们刚才说过了，仅仅在背后的数据源无序时才会并行处理。
        return Collections.unmodifiableSet(EnumSet.of(IDENTITY_FINISH,CONCURRENT));
//        return null;
    }
}
