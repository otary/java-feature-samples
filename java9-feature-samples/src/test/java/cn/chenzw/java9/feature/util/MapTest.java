package cn.chenzw.java9.feature.util;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.HashMap;
import java.util.Map;

@RunWith(JUnit4.class)
public class MapTest {


    /**
     * of实例化
     */
    @Test
    public void testOf() {
        Map<String, Integer> levels = Map.of("A", 30, "B", 38, "C", 32, "D", 23);

        Assert.assertThat(levels, Matchers.allOf(Matchers.hasKey("A"), Matchers.hasKey("B")));
    }

    /**
     * ofEntries实例化
     */
    @Test
    public void testOfEntries() {
        Map<String, Integer> levels = Map.ofEntries(
                Map.entry("A", 30),
                Map.entry("B", 28),
                Map.entry("C", 33)
        );

        Assert.assertThat(levels, Matchers.allOf(Matchers.hasKey("A"), Matchers.hasKey("B")));
    }


    /**
     * 排序
     */
    @Test
    public void testSorted() {
        Map<String, Integer> levels = Map.of("A", 30, "B", 38, "C", 32, "D", 23);

        levels.entrySet().stream().sorted(Map.Entry.comparingByValue()).forEachOrdered(System.out::println);

        levels.entrySet().stream().sorted(Map.Entry.comparingByValue()).forEachOrdered(System.out::println);

    }


    /**
     * 获取默认值
     */
    @Test
    public void testGetOrDefault() {
        Map<String, Integer> levels = Map.of("A", 30, "B", 38, "C", 32, "D", 23);

        Assert.assertEquals(30, levels.getOrDefault("A", 20).intValue());
        Assert.assertEquals(50, levels.getOrDefault("E", 50).intValue());

    }

    /**
     * 重写
     */
    @Test
    public void testComputeIfAbsent() {
        Map<String, Integer> levels = new HashMap<>();
        levels.put("A", 30);
        levels.put("B", 28);
        levels.put("C", 33);

        // key不存在时覆盖
        levels.computeIfAbsent("D", key -> Integer.valueOf(60));
        Assert.assertEquals(60, levels.get("D").intValue());

        // key存在时覆盖
        levels.computeIfPresent("A", (key, value) -> 70);
        Assert.assertEquals(70, levels.get("A").intValue());

        // key无论存不存在，都会覆盖
        levels.compute("A", (key, value) -> 50);
        Assert.assertEquals(50, levels.get("A").intValue());
    }

    /**
     * 移除
     */
    @Test
    public void testRemove() {
        Map<String, Integer> levels = new HashMap<>();
        levels.put("A", 30);
        levels.put("B", 28);
        levels.put("C", 33);

        levels.remove("B");

        Assert.assertThat(levels, Matchers.not(Matchers.hasKey("B")));
    }

    /**
     * 替换
     */
    @Test
    public void testReplaceAll() {
        Map<String, Integer> levels = new HashMap<>();
        levels.put("A", 30);
        levels.put("B", 28);
        levels.put("C", 33);

        levels.replaceAll((key, value) -> value + 10);

        Assert.assertEquals(40, levels.get("A").intValue());
        Assert.assertEquals(38, levels.get("B").intValue());
        Assert.assertEquals(43, levels.get("C").intValue());
    }

    @Test
    public void testMerge() {
        Map<String, Integer> levels = new HashMap<>();
        levels.put("A", 30);
        levels.put("B", 28);
        levels.put("C", 33);

        // key存在，则替换
        levels.merge("A", 12, (k, v) -> 22);
        Assert.assertEquals(22, levels.get("A").intValue());

        // key不存在，则添加
        levels.merge("B", 12, (k, v) -> 22);
        Assert.assertEquals(22, levels.get("B").intValue());
    }


}
