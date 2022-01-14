package com.bruce.java8stream.demo6;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.function.Function;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class Demo {
    public static void main(String[] args) {
//        System.out.println("Sequential sum done in:" +     measureSumPerf(Demo::sequentialSum, 100_000_000) + " msecs");
//        System.out.println("iterative sum done in:" +     measureSumPerf(Demo::iterativeSum, 100_000_000) + " msecs");
//        System.out.println("parallel sum done in:" +     measureSumPerf(Demo::parallelSum, 100_000_000) + " msecs");
        int n = 1000_000_000;
        System.out.println("range sum done in:" +     measureSumPerf(Demo::rangeSum, n) + " msecs");
        System.out.println("parallelRange sum done in:" +     measureSumPerf(Demo::parallelRangeSum, n) + " msecs");
        System.out.println("forkJoinSum sum done in:" +     measureSumPerf(Demo::forkJoinSum, n) + " msecs");
    }
    public static long measureSumPerf(Function<Long, Long> addr, long n) {
        long fastest = Long.MAX_VALUE;
        for (int i = 0; i < 10; i++) {
            long start = System.nanoTime();
            long sum = addr.apply(n);
            long duration = (System.nanoTime() - start)/1_000_000;
//            System.out.println("Result: " + sum);
            if (duration < fastest) fastest = duration;
        }
        return fastest;
    }

    public static long sequentialSum(long n) {
        return Stream.iterate(1L, i -> i + 1).limit(n).reduce(0L, Long::sum);
    }

    public static long iterativeSum(long n) {
        long result = 0;
        for (long i = 1L; i <= n; i++) {
            result += i;
        }
        return result;
    }

    public static long parallelSum(long n) {
        return Stream.iterate(1L, i -> i + 1).limit(n).parallel().reduce(0L, Long::sum);
    }

    public static long rangeSum(long n){
        return LongStream.rangeClosed(1,n).reduce(0L,Long::sum);
    }

    public static long parallelRangeSum(long n){
        return LongStream.rangeClosed(1,n).parallel().reduce(0L,Long::sum);
    }
//    public static long forkJoinSum(long n){
//        return ForkJoinSumCalculator.forkJoinSum(n);
//    }
    public static long forkJoinSum(long n) {
        long[] numbers = LongStream.rangeClosed(1, n).toArray();
        ForkJoinTask<Long> task = new ForkJoinSumCalculator(numbers);
        return new ForkJoinPool().invoke(task);
    }
}
