package cn.chenzw.java8.feature.time.format;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RunWith(JUnit4.class)
public class DateTimeFormatterTest {

    /**
     * 日期 -> 字符串
     */
    @Test
    public void testFormat() {
        LocalDate localDate = LocalDate.of(2019, 1, 20);

        Assert.assertEquals("20190120", localDate.format(DateTimeFormatter.BASIC_ISO_DATE));
        Assert.assertEquals("2019-01-20", localDate.format(DateTimeFormatter.ISO_LOCAL_DATE));
    }

    /**
     * 字符串 -> 日期
     */
    @Test
    public void testParse() {

        LocalDate localDate = LocalDate.parse("20190121",
                DateTimeFormatter.BASIC_ISO_DATE);
        Assert.assertEquals("2019-01-21", localDate.toString());

        LocalDate localDate2 = LocalDate.parse("2019-01-21", DateTimeFormatter.ISO_LOCAL_DATE);
        Assert.assertEquals("2019-01-21", localDate2.toString());


        LocalDateTime localDateTime = LocalDateTime.parse("2017-09-28 17:07:05", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Assert.assertEquals("2017-09-28T17:07:05", localDateTime.toString());
    }


    /**
     * 格式化
     */
    @Test
    public void testPatternFormat() {
        LocalDate localDate = LocalDate.of(2019, 1, 20);

        Assert.assertEquals("20/01/2019", localDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));



    }


}
