package cn.chenzw.java8.feature.util.stream;

import cn.chenzw.java8.feature.domain.Book;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@RunWith(JUnit4.class)
public class StreamTest {

    private static List<Book> books = new ArrayList<>();

    @BeforeClass
    public static void init() {
        books.add(new Book(1L, "JVM构造原理", 50.2));
        books.add(new Book(2L, "Mavan实践", 10.4));
        books.add(new Book(3L, "JDK8实践", 34.7));
    }


    /**
     * 实例化
     */
    @Test
    public void testStreamOf() {
        // 集合
        Stream<String> stringStream = Stream.of("张三", "李四", "王五");  // 不能为null值
        Assert.assertEquals(3, stringStream.count());

        // 数组
        String[] stringArray = {"张三", "李四", "王五"};
        Stream<String> stringStream2 = Arrays.stream(stringArray);
        Assert.assertEquals(3, stringStream2.count());

        // 文件
        try {
            // 读取文件
            Stream<String> lines = Files.lines(Paths.get("pom.xml"), Charset.defaultCharset());

            // 统计行数
            Assert.assertTrue(lines.count() > 10);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 过滤
     */
    @Test
    public void testStreamFilter() {
        Stream<Book> bookStream = books.stream().filter((book) -> book.getPrice() > 30);

        // 价格>30的只有2条记录
        Assert.assertEquals(2, bookStream.count());
    }


    /**
     * 转换格式
     */
    @Test
    public void testStreamCollect() {
        Stream<String> stringStream = Stream.of("张三", "李四", "王五");
        // 详见
        List<String> list = stringStream.collect(Collectors.toList());

        Assert.assertEquals(3, list.size());
    }

    /**
     * 元素值转化
     */
    @Test
    public void testStreamMap() {
        Stream<String> stringStream = Stream.of("zhangsan", "lisi", "wangwu");
        List<String> list = stringStream.map((s) -> s.toUpperCase())
                .collect(Collectors.toList());  // => [ZHANGSAN, LISI, WANGWU]

        Assert.assertThat(list, Matchers.hasItems("ZHANGSAN", "LISI", "WANGWU"));
    }

    @Test
    public void testStreamCount() {
        long count = books.stream().count();
        Assert.assertEquals(3, count);
    }

    @Test
    public void testStreamMin() {
        Optional<Book> minBook = books.stream().min(Comparator.comparing(Book::getPrice));

        Assert.assertEquals((Long) 2L, minBook.get().getId());
    }

    @Test
    public void testStreamMax() {
        Optional<Book> maxBook = books.stream().max(Comparator.comparing(Book::getPrice));

        Assert.assertEquals((Long) 1L, maxBook.get().getId());
    }


    /**
     * 每次传入上一次计算结果的值
     */
    @Test
    public void testStreamReduce() {
        Integer[] intArray = {1, 2, 3, 4};

        int sum = Stream.of(intArray).reduce(Integer::sum).orElse(0).intValue();
        Assert.assertEquals(10, sum);

        int max = Stream.of(intArray).reduce(Integer::max).orElse(0).intValue();
        Assert.assertEquals(4, max);

        int min = Stream.of(intArray).reduce(Integer::min).orElse(0).intValue();
        Assert.assertEquals(1, min);
    }


}
