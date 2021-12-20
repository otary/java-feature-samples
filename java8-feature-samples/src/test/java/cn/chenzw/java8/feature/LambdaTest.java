package cn.chenzw.java8.feature;

import cn.chenzw.java8.feature.domain.Book;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RunWith(JUnit4.class)
public class LambdaTest {

    private static List<Book> books = new ArrayList<>();

    @BeforeClass
    public static void init() {
        books.add(new Book(1L, "JVM构造原理", 50.2));
        books.add(new Book(2L, "Mavan实践", 10.4));
        books.add(new Book(3L, "JDK8实践", 34.7));
    }

    /**
     * 旧式写法
     */
    @Test
    public void testOld() {
        Comparator<Book> comparator = new Comparator<Book>() {
            @Override
            public int compare(Book b1, Book b2) {
                return b1.getPrice().compareTo(b2.getPrice());
            }
        };
        Collections.sort(books, comparator);

        for (Book book : books) {
            System.out.println(book);
        }
    }

    /**
     * 使用lambad表达式
     */
    @Test
    public void testWithLambad() {
        Comparator<Book> comparator = (Book b1, Book b2) -> b1.getPrice().compareTo(b2.getPrice());
        // 简化写法
        // Comparator<Book> comparator = Comparator.comparing(Book::getPrice);
        Collections.sort(books, comparator);

        books.stream().forEach((book) -> System.out.println(book));
        // 简化写法
        books.stream().forEach(System.out::println);

    }
}
