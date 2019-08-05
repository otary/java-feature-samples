package cn.chenzw.java8.feature.util;

import cn.chenzw.java8.feature.domain.Book;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(JUnit4.class)
public class OptionalTest {

    private static List<Book> books = new ArrayList<>();

    @BeforeClass
    public static void init() {
        books.add(new Book(1L, "JVM构造原理", 50.2));
        books.add(new Book(2L, "Mavan实践", 10.4));
        books.add(new Book(3L, "JDK8实践", 34.7));
    }

    /**
     * Optional.of()、Optional.ofNullable()示例
     */
    @Test
    public void testOptionalOf() {
        Optional<Integer> intOptional = Optional.of(1); // of不允许null值
        // Optional.of(null);  // 报空指针异常
        Assert.assertTrue(intOptional.isPresent());

        Optional<Integer> intOptional2 = Optional.ofNullable(null); // ofNullable允许null值
        Assert.assertFalse(intOptional2.isPresent());
    }

    @Test(expected = NullPointerException.class)
    public void testOptionalOfNull() {
        Optional.of(null);
    }


    @Test
    public void testOptionalFilter() {

        // 10~30块之间
        Optional<Book> bookOptional = Optional.ofNullable(new Book(1L, "JVM构造原理", 50.2))
                .filter((book) -> book.getPrice() < 30).filter((book) -> book.getPrice() > 10);

        Assert.assertFalse(bookOptional.isPresent());
    }

    @Test
    public void test() {

    }

}
