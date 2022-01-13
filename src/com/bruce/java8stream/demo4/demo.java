package com.bruce.java8stream.demo4;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class demo {
    public static void main(String[] args) {
//        int a = 3;
//        IntStream.rangeClosed(1, 100)
//                .filter(b -> Math.sqrt(a * a + b * b) % 1 == 0)
//                .boxed()
//                .map(b->new int[]{a,b,(int)Math.sqrt(a * a + b * b)});
//        IntStream.rangeClosed(1, 100)
//                .filter(b -> Math.sqrt(a * a + b * b) % 1 == 0)
//                .mapToObj(b->new int[]{a,b,(int)Math.sqrt(a * a + b * b)});
        // 三元数
        Stream<int[]> stream = IntStream
                .rangeClosed(1, 100)
                .boxed()
                .flatMap(a ->
                        IntStream.rangeClosed(a, 100)
                                .filter(b -> Math.sqrt(a * a + b * b) % 1 == 0)
                                .mapToObj(b -> new int[]{a, b, (int) Math.sqrt(a * a + b * b)}
                                )
                );
//     b   stream.forEach(t ->
//                System.out.println(t[0] + "," + t[1] + "," + t[2])
//        );
        Stream<double[]> streamD = IntStream
                .rangeClosed(1, 100)
                .boxed()
                .flatMap(a ->
                        IntStream
                                .rangeClosed(a, 100)
                                .mapToObj(
                                        b ->
                                                new double[]{a, b, Math.sqrt(a * a + b * b)}
                                ).filter(
                                        t -> t[2] % 1 == 0
                                )

                );
//        streamD.forEach(t ->
//                System.out.println(t[0] + "," + t[1] + "," + t[2])
//        );
        buildStream();


    }

    /**
     * 构建流
     */
    public static void buildStream() {
        // 静态方法of
        Stream.of("java 8", "bruce", "champion").map(String::toUpperCase).forEach(System.out::println);
        // 空流
        Stream.empty();
        // 数组
        int[] num = new int[]{1, 2, 3, 4, 5, 6, 7};
        int sum = Arrays.stream(num).sum();
        // 文件
        try (Stream<String> fileStream =
                     Files.lines(Paths.get("/Users/bruce/Downloads/centitSettings.xml"), Charset.defaultCharset())
        ) {
            long wordsNum = fileStream
                    .flatMap(line -> Arrays.stream(line.split(" ")))
                    .distinct()
                    .count();
            System.out.println(wordsNum);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 创造无限流 iterate
        Stream.iterate(0, n -> n + 2).limit(10).forEach(System.out::print);
        // 斐波那契
        Stream.iterate(new int[]{0, 1}, a -> new int[]{a[1], a[1] + a[0]}).limit(20).map(t -> t[0]).forEach(System.out::println);
        // generate
        IntStream.generate(()->1).limit(10);


    }
}
