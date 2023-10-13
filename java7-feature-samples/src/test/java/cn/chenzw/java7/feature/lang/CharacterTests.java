package cn.chenzw.java7.feature.lang;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * @author chenzw
 */
@Slf4j
@RunWith(JUnit4.class)
public class CharacterTests {

    /**
     * 判断字符类型
     */
    @Test
    public void testCheckType() {
        // 判断是否空格，包括TAB（一个Tab等于4个空格）
        Assert.assertEquals(true, Character.isWhitespace(" ".charAt(0)));
        // 判断是否空格，不包括TAB
        Assert.assertEquals(true, Character.isSpaceChar(" ".charAt(0)));

        // 判断是否数字
        Assert.assertEquals(true, Character.isDigit("1".charAt(0)));
        Assert.assertEquals(true, Character.isDigit("1.2".charAt(0)));

        // 是否字母
        Assert.assertEquals(true, Character.isAlphabetic("a".charAt(0)));

        // 是否字符或数字
        Assert.assertEquals(true, Character.isLetterOrDigit("a".charAt(0)));

        Assert.assertEquals(true, Character.isLetter("a".charAt(0)));
    }
}
