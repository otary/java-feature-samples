package cn.chenzw.java8.feature.util.function;

import cn.chenzw.java8.feature.domain.Book;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.function.*;

/**
 * @see {@link Supplier | BooleanSupplier | IntSupplier | LongSupplier | DoubleSupplier}
 */
@RunWith(JUnit4.class)
public class SupplierTest {

    /**
     * Supplier测试
     */
    @Test
    public void testSupplier() {
        Supplier<String> supplier = () -> "chenzw";

        Assert.assertEquals("chenzw", supplier.get());


    }

    @Test
    public void testSupplierConstructor() {
        Supplier<Book> bookSupplier = () -> new Book(1L, "书名", 20.45);

        Book book = bookSupplier.get();
        Assert.assertEquals((Long) 1L, book.getId());
        Assert.assertEquals("书名", book.getName());

    }

    /**
     * BooleanSupplier测试
     */
    @Test
    public void testBooleanSupplier() {
        BooleanSupplier booleanSupplier = () -> 1 == 2;

        Assert.assertEquals(false, booleanSupplier.getAsBoolean());
    }

    @Test
    public void testIntSupplier() {
        IntSupplier intSupplier = () -> 1 * 2;

        Assert.assertEquals(2, intSupplier.getAsInt());
    }

    @Test
    public void testLongSupplier() {
        LongSupplier longSupplier = () -> 1L * 2;

        Assert.assertEquals(2L, longSupplier.getAsLong());
    }

    @Test
    public void testDoubleSupplier() {
        DoubleSupplier doubleSupplier = () -> 1D * 2;

        Assert.assertEquals(2, doubleSupplier.getAsDouble());
    }

}

