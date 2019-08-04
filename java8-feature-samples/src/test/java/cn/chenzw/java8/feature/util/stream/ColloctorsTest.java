package cn.chenzw.java8.feature.util.stream;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @see {@link java.util.stream.Collectors}
 * @see {@link Stream#collect(java.util.stream.Collector)}
 */
@RunWith(JUnit4.class)
public class ColloctorsTest {

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

    @Test
    public void testMaxBy() {
        Integer[] intArray = {4, 5, 3, 9, 10};

        // Stream.of(intArray).collect(Collectors.maxBy())

    }


}
