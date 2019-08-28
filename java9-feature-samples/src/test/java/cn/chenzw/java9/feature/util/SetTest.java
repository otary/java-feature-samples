package cn.chenzw.java9.feature.util;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Set;

@RunWith(JUnit4.class)
public class SetTest {

    /**
     * 初始化
     */
    @Test
    public void testOf() {
        Set<String> sets = Set.of("A", "B", "C");
        Assert.assertEquals("[A, C, B]", sets.toString());
    }
}
