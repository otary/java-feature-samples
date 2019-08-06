package cn.chenzw.java8.feature.util.function;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.function.Function;

/**
 * @see {@link }
 */
@RunWith(JUnit4.class)
public class FunctionTest {

    @Test
    public void testFunctionApply() {
        // 参数为Integer类型、返回值为String类型
        Function<Integer, String> f = (x) -> "age:" + x;
        Assert.assertEquals("age:20", f.apply(20));
    }

    /**
     * compose(先执行)
     */
    @Test
    public void testFunctionCompose() {
        Function<Integer, Integer> f = (x) -> x * x;
        Function<Integer, Integer> f2 = (x) -> x + 1;

        // 先执行3+1(compose方法),再执行4*4
        Assert.assertEquals((Integer) 16, f.compose(f2).apply(3));
    }

    /**
     * andThen示例（后执行）
     */
    @Test
    public void testFunctionAndThen() {
        Function<Integer, Integer> f = (x) -> x * x;
        Function<Integer, Integer> f2 = (x) -> x + 1;

        // 先执行3*3,再执行9+1（andThen方法）
        Assert.assertEquals((Integer) 10, f.andThen(f2).apply(3));
    }

    /**
     * Function.identity()
     */
    @Test
    public void testFunctionIdentify() {
        // 直接返回实例
        Object identity = Function.identity().apply(20);
        Assert.assertEquals(20, identity);
    }

    @Test
    public void testBitFunction() {

    }

}
