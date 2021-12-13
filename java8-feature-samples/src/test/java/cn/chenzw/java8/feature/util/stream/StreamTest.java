package cn.chenzw.java8.feature.util.stream;

import cn.chenzw.java8.feature.domain.Book;
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
import java.util.function.IntUnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;


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
        List<String> list = stringStream.collect(toList());

        Assert.assertEquals(3, list.size());
    }

    /**
     * 元素值转化
     */
    @Test
    public void testStreamMap() {
        Stream<String> stringStream = Stream.of("zhangsan", "lisi", "wangwu");
        List<String> list = stringStream.map((s) -> s.toUpperCase())
                .collect(toList());  // => [ZHANGSAN, LISI, WANGWU]

        Assert.assertThat(list, Matchers.hasItems("ZHANGSAN", "LISI", "WANGWU"));
    }

    /**
     * 统计个数
     */
    @Test
    public void testStreamCount() {
        long count = books.stream().count();
        Assert.assertEquals(3, count);
    }

    /**
     * 取最小值
     */
    @Test
    public void testStreamMin() {
        Optional<Book> minBook = books.stream().min(Comparator.comparing(Book::getPrice));

        Assert.assertEquals((Long) 2L, minBook.get().getId());
    }

    /**
     * 取最大值
     */
    @Test
    public void testStreamMax() {
        Optional<Book> maxBook = books.stream().max(Comparator.comparing(Book::getPrice));

        Assert.assertEquals((Long) 1L, maxBook.get().getId());
    }


    /**
     * 排序
     */
    @Test
    public void testStreamSort() {
        List<Book> books = StreamTest.books.stream().sorted(Comparator.comparing(Book::getPrice)).collect(toList());

        Assert.assertEquals("[Book{id=2, name='Mavan实践', price=10.4}, Book{id=3, name='JDK8实践', price=34.7}, Book{id=1, name='JVM构造原理', price=50.2}]", books.toString());
    }

    /**
     * 每次传入上一次计算结果的值
     */
    @Test
    public void testStreamReduce() {
        Integer[] intArray = {1, 2, 3, 4};

        int sum = Stream.of(intArray).reduce(Integer::sum).orElse(0).intValue();
        Assert.assertEquals(10, sum);

        int sum2 = Stream.of(intArray).reduce(5, (a, b) -> a + b).intValue();
        Assert.assertEquals(15, sum2);

        int max = Stream.of(intArray).reduce(Integer::max).orElse(0).intValue();
        Assert.assertEquals(4, max);

        int min = Stream.of(intArray).reduce(Integer::min).orElse(0).intValue();
        Assert.assertEquals(1, min);

        // 如果是对象，则先提取数值字段，再进行相加
        double sum3 = books.stream().mapToDouble(book -> book.getPrice()).reduce(Double::sum).getAsDouble();
        // Assert.assertTrue(Double.valueOf("95.3").compareTo(sum3) == 0);

    }


    /**
     * 遍历（forEach后返回的是void）
     */
    @Test
    public void testStreamForEach() {
        books.stream().forEach(book -> {
            System.out.println(book);
        });
    }

    /**
     * 遍历（类似于ForEach，但返回Stream）
     */
    @Test
    public void testStreamPeek() {
        List<Book> list = books.stream().peek(book -> {
            book.setName("xxx");
        }).collect(toList());

        Assert.assertEquals("[Book{id=1, name='xxx', price=50.2}, Book{id=2, name='xxx', price=10.4}, Book{id=3, name='xxx', price=34.7}]", list.toString());
    }

    /**
     * 去重
     */
    @Test
    public void testStreamDistinct() {
        Integer[] intArray = {1, 2, 3, 4, 3, 5};
        Stream<Integer> distinct = Stream.of(intArray).distinct();

        Assert.assertArrayEquals(new Integer[]{1, 2, 3, 4, 5}, distinct.toArray());
    }


    /**
     * 取前n个元素
     */
    @Test
    public void testStreamLimit() {
        Integer[] intArray = {1, 2, 3, 4, 3, 5};
        Stream<Integer> limit = Stream.of(intArray).limit(3);

        Assert.assertEquals(3, limit.count());
    }

    /**
     * 跳过n个元素
     */
    @Test
    public void testStreamSkip() {
        Integer[] intArray = {1, 2, 3, 4, 3, 5};
        Stream<Integer> skip = Stream.of(intArray).skip(3);

        Assert.assertArrayEquals(new Integer[]{4, 3, 5}, skip.toArray());
    }

    /**
     * 合并流
     */
    @Test
    public void testStreamFlatMap() {
        String[][] words = {
                {"H", "e", "l", "l", "o"},
                {"W", "o", "r", "d"}
        };
        Stream<String> flatMap = Stream.of(words)
                .flatMap(Arrays::stream);

        Assert.assertArrayEquals(new String[]{"H", "e", "l", "l", "o", "W", "o", "r", "d"}, flatMap.toArray());
    }

    /**
     * 合并多个List
     */
    @Test
    public void testListFlatMap() {
        List<String> a1 = Arrays.asList("1", "2", "3");
        List<String> a2 = Arrays.asList("4", "5", "6");

        List<String> lists = Arrays.asList(a1, a2).stream()
                .flatMap(Collection::stream)
                .collect(toList());

        Assert.assertEquals("[1, 2, 3, 4, 5, 6]", lists.toString());
    }

    /**
     * 转换成Int类型数组
     */
    @Test
    public void testStreamMapToInt() {
        String[] nums = new String[]{"12", "34", "56"};

        // 使用toArray方式
        int[] results = Stream.of(nums).mapToInt(Integer::valueOf).toArray();
        Assert.assertArrayEquals(new int[]{12, 34, 56}, results);

        // 使用collect方式（不推荐）
        List<Integer> results2 = Stream.of(nums).mapToInt(Integer::valueOf).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        Assert.assertArrayEquals(new Integer[]{12, 34, 56}, results2.toArray(new Integer[3]));
    }


    /**
     * 转换成Long类型数组
     */
    @Test
    public void testStreamMapToLong() {
        String[] nums = new String[]{"12", "34", "56"};

        long[] results = Stream.of(nums).mapToLong(Long::valueOf).toArray();
        Assert.assertArrayEquals(new long[]{12, 34, 56}, results);

    }


    /**
     * 全部匹配
     */
    @Test
    public void testStreamAllMatch() {
        // 所有书价格>10
        Assert.assertTrue(books.stream().allMatch(book -> book.getPrice() > 10));

        // 并非所有书价格>20
        Assert.assertFalse(books.stream().allMatch(book -> book.getPrice() > 20));
    }

    /**
     * 任意一个匹配
     */
    @Test
    public void testStreamAnyMatch() {
        // 任意一本书价格>10
        Assert.assertTrue(books.stream().anyMatch(book -> book.getPrice() > 10));

        // 任意一本书价格>20
        Assert.assertTrue(books.stream().anyMatch(book -> book.getPrice() > 20));
    }


    /**
     * 获取第一个元素
     */
    @Test
    public void testStreamFindFirst() {
        String[] strings = {"Java", "C++", "Golang"};

        Assert.assertEquals("Java", Stream.of(strings).findFirst().get());
    }

    /**
     * 获取第一个元素（常用于并行流）
     */
    @Test
    public void testStreamFindAny() {
        String[] strings = {"Java", "C++", "Golang"};

        Assert.assertEquals("C++", Stream.of(strings).parallel().findAny().get());
    }

    /**
     * 并行遍历
     */
    @Test
    public void testStreamSpliterator() {
        String[] strings = {"Java", "C++", "Golang"};

        Stream.of(strings).spliterator().forEachRemaining(System.out::println);
    }

    /**
     * 并行流
     */
    @Test
    public void testParallelStream() {
        // 串行流
        books.stream().forEach(System.out::println);

        // 并行流
        books.parallelStream().forEach(System.out::println);
        // 按照顺序执行
        books.parallelStream().forEachOrdered(System.out::println);
    }


    @Test
    public void testStreamRange() {
        List<Integer> list = IntStream.range(0, 10).map(new IntUnaryOperator() {

            @Override
            public int applyAsInt(int operand) {
                return 0;
            }

        }).boxed().collect(toList());

        Assert.assertEquals("[0, 0, 0, 0, 0, 0, 0, 0, 0, 0]", list.toString());
    }

}
