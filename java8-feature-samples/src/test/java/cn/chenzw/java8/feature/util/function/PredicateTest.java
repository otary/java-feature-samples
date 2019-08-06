package cn.chenzw.java8.feature.util.function;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.function.BiPredicate;
import java.util.function.IntPredicate;
import java.util.function.Predicate;

@RunWith(JUnit4.class)
public class PredicateTest {

    @Test
    public void testPredicate() {
        Predicate<String> stringPredicate = (s) -> s.length() > 5;

        Assert.assertFalse(stringPredicate.test("chen"));
        Assert.assertTrue(stringPredicate.test("chenzw"));
    }

    /**
     * negate(逻辑非)
     */
    @Test
    public void testNegate() {
        Predicate<String> stringPredicate = (s) -> s.length() > 5;

        Assert.assertTrue(stringPredicate.negate().test("chen"));
        Assert.assertFalse(stringPredicate.negate().test("chenzw"));
    }

    /**
     * or(逻辑或)
     */
    @Test
    public void testOr() {
        Predicate<String> stringPredicate = (s) -> s.length() > 5;

        // 字符长度大于5或以"chen"开头
        Assert.assertTrue(stringPredicate.or((s) -> s.startsWith("chen")).test("chenzw"));
    }

    /**
     * and（逻辑与）
     */
    @Test
    public void testAnd() {
        Predicate<String> stringPredicate = (s) -> s.length() > 5;

        // 字符长度大于5且以"chen"开头
        Assert.assertTrue(stringPredicate.and((s) -> s.startsWith("chen")).test("chenzw"));
    }

    @Test
    public void testIntPredicate() {
        IntPredicate intPredicate = (s) -> s > 5;

        Assert.assertTrue(intPredicate.test(7));
        Assert.assertFalse(intPredicate.test(3));
    }

    @Test
    public void testBitPredicate() {
        BiPredicate<Integer, Integer> biPredicate = (s, b) -> s + b < 15;

        Assert.assertTrue(biPredicate.test(12, 1));
        Assert.assertFalse(biPredicate.test(12, 4));
    }


}
