package cn.chenzw.java8.feature.util.stream;

import cn.chenzw.java8.feature.domain.Book;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.*;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @see {@link java.util.stream.Collectors}
 * @see {@link Stream#collect(java.util.stream.Collector)}
 */
@RunWith(JUnit4.class)
public class ColloctorsTest {

    private static List<Book> books = new ArrayList<>();

    @BeforeClass
    public static void init() {
        books.add(new Book(1L, "JVM构造原理", 50.2));
        books.add(new Book(2L, "Mavan实践", 10.4));
        books.add(new Book(3L, "JDK8实践", 34.7));
    }


    /**
     * 统计元素个数
     */
    @Test
    public void testCounting() {
        Integer[] intArray = {4, 5, 3, 9, 10};

        // 统计元素个数
        long counting = Stream.of(intArray).collect(Collectors.counting());
        Assert.assertEquals(5, counting);

        // 等同于
        long count = Stream.of(intArray).count();
        Assert.assertEquals(5, count);
    }

    /**
     * 获取最大值
     */
    @Test
    public void testMaxBy() {
        Integer[] intArray = {14, 5, 29, 9, 10};

        Optional<Integer> maxOptional = Stream.of(intArray).collect(Collectors.maxBy((o1, o2) -> o1.compareTo(o2)));

        // 等于于
        // Optional<Integer> maxOptional = Stream.of(intArray).max((o1, o2) -> o1.compareTo(o2));

        // 简写
        // Optional<Integer> maxOptional = Stream.of(intArray).max(Comparator.naturalOrder());

        Assert.assertEquals(29, maxOptional.get().intValue());
    }

    /**
     * 最小值
     */
    @Test
    public void testMin() {
        Integer[] intArray = {14, 5, 29, 9, 10};
        Optional<Integer> minOptional = Stream.of(intArray).collect(Collectors.minBy((o1, o2) -> o1.compareTo(o2)));

        Assert.assertEquals(5, minOptional.get().intValue());
    }


    /**
     * 求和
     */
    @Test
    public void testSummingInt() {
        Integer[] intArray = {14, 5, 29, 9, 10};
        int sum = Stream.of(intArray).collect(Collectors.summingInt((x) -> x.intValue()));

        Assert.assertEquals(67, sum); // 总和
    }


    /**
     * 平均值
     */
    @Test
    public void testAverageingInt() {
        Integer[] intArray = {14, 5, 29, 9, 10};
        double average = Stream.of(intArray).collect(Collectors.averagingInt((x) -> x.intValue()));

        Assert.assertEquals(13.4, average);
    }


    /**
     * 拼接
     */
    @Test
    public void testJoining() {
        String[] stringArray = {"Java", "C++", "Golang"};
        String joining = Stream.of(stringArray).collect(Collectors.joining(","));

        Assert.assertEquals("Java,C++,Golang", joining);
    }


    /**
     * 排序（list => map）
     */
    @Test
    public void testGroupBy() {
        Map<Double, List<Book>> groupBy = books.stream().collect(Collectors.groupingBy((book) -> book.getPrice()));

        Assert.assertEquals("{50.2=[Book{id=1, name='JVM构造原理', price=50.2}], 10.4=[Book{id=2, name='Mavan实践', price=10.4}], 34.7=[Book{id=3, name='JDK8实践', price=34.7}]}", groupBy.toString());
    }


    /**
     * 分组（list => map）
     */
    @Test
    public void testPartitioningBy() {
        Map<Boolean, List<Book>> partitioningBy = books.stream().collect(Collectors.partitioningBy((book) -> book.getPrice() > 20));

        Assert.assertEquals("{false=[Book{id=2, name='Mavan实践', price=10.4}], true=[Book{id=1, name='JVM构造原理', price=50.2}, Book{id=3, name='JDK8实践', price=34.7}]}", partitioningBy.toString());
    }

    /**
     * List转Map
     */
    @Test
    public void testToMap() {
        Map<Long, String> bookMap = books.stream().collect(Collectors.toMap((book) -> book.getId(), (book) -> book.getName()));

        Assert.assertEquals("{1=JVM构造原理, 2=Mavan实践, 3=JDK8实践}", bookMap.toString());
    }

    /**
     * Map => Map（修改key）
     */
    @Test
    public void testChangeMapKey() {
        // 对id=2的key转换成id=9
        Map<Long, String> bookMap = books.stream().collect(Collectors.toMap(book -> {
            if (book.getId().longValue() == 2) {
                return 9L;
            }
            return book.getId();
        }, (book) -> book.getName()));

        Assert.assertEquals("{1=JVM构造原理, 3=JDK8实践, 9=Mavan实践}", bookMap.toString());
    }

    /**
     * List转ConcurrentMap
     */
    @Test
    public void testToConcurrentMap() {
        ConcurrentMap<Long, String> bookMap = books.stream().collect(Collectors.toConcurrentMap((book) -> book.getId(), (book) -> book.getName()));

        Assert.assertEquals("{1=JVM构造原理, 2=Mavan实践, 3=JDK8实践}", bookMap.toString());
    }

    /**
     * List转Set
     */
    @Test
    public void testToSet() {
        Set<Book> bookSet = books.stream().collect(Collectors.toSet());
        Assert.assertEquals("[Book{id=2, name='Mavan实践', price=10.4}, Book{id=3, name='JDK8实践', price=34.7}, Book{id=1, name='JVM构造原理', price=50.2}]", bookSet.toString());
    }

    /**
     * 去重
     */
    @Test
    public void testComparing() {
        List<Book> uniqueBooks = books.stream().collect(
                Collectors.collectingAndThen(Collectors.toCollection(
                        () -> new TreeSet<>(Comparator.comparing(o -> o.getName()))), ArrayList::new)
        );

        Assert.assertEquals("[Book{id=3, name='JDK8实践', price=34.7}, Book{id=1, name='JVM构造原理', price=50.2}, Book{id=2, name='Mavan实践', price=10.4}]", uniqueBooks.toString());
    }

}
