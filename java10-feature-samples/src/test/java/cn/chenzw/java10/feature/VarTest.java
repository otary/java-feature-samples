package cn.chenzw.java10.feature;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;

@RunWith(JUnit4.class)
public class VarTest {

    @Test
    public void test() {
        // 变量推断
        var a = 1;
        Assert.assertEquals(1, a);

        var list = new ArrayList<>();
        list.add("test");
        Assert.assertEquals("test", list.get(0));
    }

    @Test
    public void testError(){

        /*

        var list4; // 错误！必须赋值
        var g = null; // 错误！不能赋null值
        var k = { 1 , 2 }; // 错误！必须要有显式的目标类型


        * */
    }
}
