package cn.chenzw.java8.feature.time;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.time.LocalDate;

/**
 * 日期
 */
@RunWith(JUnit4.class)
public class LocalDateTest {


    @Test
    public void testNow() {
        LocalDate now = LocalDate.now();

        System.out.println(now);
    }




}
