package cn.chenzw.java8.feature.util;

import cn.chenzw.java8.feature.domain.Book;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;

@RunWith(JUnit4.class)
public class CollectionTest {

    private static List<Book> books = new ArrayList<>();

    @BeforeClass
    public static void init() {
        books.add(new Book(1L, "JVM构造原理", 50.2));
        books.add(new Book(2L, "Mavan实践", 10.4));
        books.add(new Book(3L, "JDK8实践", 34.7));
    }

    @Test
    public void testRemoveIf() {
        books.removeIf(book -> book.getId().equals(1L));

        Assert.assertEquals(2, books.size());
    }

    @Test
    public void testRemoveAll() {
        List<Book> books2 = new ArrayList<>();
        books2.add(new Book(1L, "JVM构造原理", 50.2));

        // 移除多个
        books.removeAll(books2);

        Assert.assertEquals(2, books.size());
    }


}
