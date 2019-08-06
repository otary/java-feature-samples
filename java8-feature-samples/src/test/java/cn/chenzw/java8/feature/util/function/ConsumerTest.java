package cn.chenzw.java8.feature.util.function;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.function.*;

@RunWith(JUnit4.class)
public class ConsumerTest {

    @Test
    public void test() {
        Consumer<String> consumer = (x) -> System.out.println(x.toUpperCase());

        consumer.accept("chenzw");  // => CHENZW
    }

    @Test
    public void testAndThen() {
        Consumer<String> consumer = (x) -> System.out.println("consumer");
        Consumer<String> consumer2 = (x) -> System.out.println("consumer2");

        consumer.andThen(consumer2).accept("chenzw");
    }


    @Test
    public void testIntConsumer() {
        IntConsumer intConsumer = (x) -> System.out.println(x * 2);

        intConsumer.accept(3); // => 6
    }

    @Test
    public void testLongConsumer() {
        LongConsumer longConsumer = (x) -> System.out.println(x * 2);

        longConsumer.accept(3L); // => 6
    }

    @Test
    public void testDoubleConsumer() {
        DoubleConsumer doubleConsumer = (x) -> System.out.println(x * 2);

        doubleConsumer.accept(3d);  // => 6
    }

    @Test
    public void testBiConsumer() {
        BiConsumer<String, Integer> biConsumer = (x, i) -> System.out.println(x + i);

        biConsumer.accept("chenzw", 10);
    }
}
