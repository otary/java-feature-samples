package cn.chenzw.java7.feature.lang;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * 基本数据类型测试
 */
@RunWith(JUnit4.class)
public class PrimitiveTests {


    /**
     * 字符串 与 数字 比大小（不同类型的equals永远不相等）
     */
    @Test
    public void testEquals() {
        Integer ia = 1;
        String sa = "1";

        // 先 == 比较(不相等)，接着比较类型，类型不相同，直接false
        Assert.assertFalse(sa.equals(ia));
    }


    /**
     * 数字间加下划线（增强可读性）
     */
    @Test
    public void testUnderLine() {
        int a = 1_233_212;
        Assert.assertTrue(a == 1233212);
    }


}
