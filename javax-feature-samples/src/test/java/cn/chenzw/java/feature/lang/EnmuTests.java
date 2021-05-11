package cn.chenzw.java.feature.lang;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class EnmuTests {

    @Test
    public void testValueOf() {
        // 字符串 转 Enum对象
        Color red = Color.valueOf("RED");

        Assert.assertEquals("red", red.name);
    }


    public enum Color {
        // 枚举实例必须写在最前面，并且以分号结尾
        RED("red"), YELLOW("yellow"), BLUE("bule");

        private String name;

        private Color(String name) {
            this.name = name;
        }
        public String getValue(){
            return this.name;
        }

        // 重写方法
        @Override
        public String toString() {
            return this.name;
        }
    }


    /**
     * 反编译（本质继承了Enum）
     *
     */
   /*
   public final class Color extends java.lang.Enum<Color> {
        public static final Color RED;
        public static final Color YELLOW;
        public static final Color BLUE;
        public static Color[] values();
        public static Color valueOf(java.lang.String);
        public java.lang.String getValue();
        public java.lang.String toString();
        public static void main(java.lang.String[]);
        static {};
    }*/
}
