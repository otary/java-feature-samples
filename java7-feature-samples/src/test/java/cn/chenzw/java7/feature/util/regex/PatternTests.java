package cn.chenzw.java7.feature.util.regex;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式
 */
@Slf4j
@RunWith(JUnit4.class)
public class PatternTests {

    @Test
    public void test() {
        Pattern pattern = Pattern.compile("(\\d)+");

        Matcher matcher = pattern.matcher("中12国34");
        if (matcher.find()) {
            log.info(matcher.group());
        }
    }

    @Test
    public void test2() {
        Integer ii = new Integer(1);

        System.out.println(ii == 1);
    }
}
