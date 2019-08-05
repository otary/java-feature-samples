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

    @Test
    public void testFunctionCompose(){
        Function<String, Integer> f = (x) -> x.length();
        //Assert.assertEquals("3", f.compose(f).apply((20)));
    }

}
