package cn.chenzw.java8.feature.time;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

/**
 * 日期
 */
@Slf4j
@RunWith(JUnit4.class)
public class LocalDateTest {


    @Test
    public void testNow() {
        LocalDate now = LocalDate.now();

        log.info("=> {}", now);  // => 2019-08-07
    }


    @Test
    public void testLocalDate() {
        LocalDate date = LocalDate.of(2019, 10, 1);

        Assert.assertEquals(2019, date.getYear());
        Assert.assertEquals(10, date.getMonthValue());
        Assert.assertEquals(1, date.getDayOfMonth());

        // 星期几
        Assert.assertEquals("TUESDAY", date.getDayOfWeek().toString());

        // 星期几
        Assert.assertEquals(2, date.getDayOfWeek().getValue());

        // 当年的第几天
        Assert.assertEquals(274, date.getDayOfYear());

        // 当月的天数
        Assert.assertEquals(31, date.lengthOfMonth());

        // 当年的天数
        Assert.assertEquals(365, date.lengthOfYear());

        // 是否闰年
        Assert.assertEquals(false, date.isLeapYear());

    }


    /**
     * 修改日期
     */
    @Test
    public void testLocalDateWith() {
        LocalDate date = LocalDate.of(2019, 10, 1);

        Assert.assertEquals("2019-10-01", date.toString());

        // 修改年份
        Assert.assertEquals("2018-10-01", date.withYear(2018).toString());

        // 修改月份
        Assert.assertEquals("2019-02-01", date.withMonth(2).toString());

        // 修改日期
        Assert.assertEquals("2019-10-05", date.withDayOfMonth(5).toString());

        // 指定特定字段值
        Assert.assertEquals("2019-02-20", date.with(ChronoField.MONTH_OF_YEAR, 2).with(ChronoField.DAY_OF_MONTH, 20).toString());

    }


    /**
     * 增加/减少日期
     */
    @Test
    public void testLocalDatePlusMinus() {
        LocalDate date = LocalDate.of(2019, 10, 1);

        // 增加日期
        Assert.assertEquals("2019-10-02", date.plusDays(1).toString());

        // 增加月份
        Assert.assertEquals("2019-11-01", date.plusMonths(1).toString());

        // 增加年份
        Assert.assertEquals("2020-10-01", date.plusYears(1).toString());

        // 增加周数
        Assert.assertEquals("2019-10-08", date.plusWeeks(1).toString());

        // 减少日期
        Assert.assertEquals("2019-09-30", date.minusDays(1).toString());

        // 减少月份
        Assert.assertEquals("2019-09-01", date.minusMonths(1).toString());

        // 减少年份
        Assert.assertEquals("2018-10-01", date.minusYears(1).toString());

        // 减少周
        Assert.assertEquals("2019-09-24", date.minusWeeks(1).toString());

        // 自定义
        Assert.assertEquals("2019-11-03", date.plus(1, ChronoUnit.MONTHS).plus(2, ChronoUnit.DAYS).toString());
    }


    /**
     * 解析日期
     */
    @Test
    public void testParse() {
        // 只能解析日期，不能带时间
        LocalDate date = LocalDate.parse("2021-10-01");
        log.info(" => {}", date);

        // 解析报错（默认只解析yyyy-MM-dd格式）
        //LocalDate date2 = LocalDate.parse("2021/10/01");


        // 自定义解析格式
        LocalDate date3 = LocalDate.parse("2021/10/01", DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        log.info("自定义解析格式 => {}", date3);

    }

    /**
     * LocalDate 转 Date对象
     */
    @Test
    public void testLocalDate2Date() {
        LocalDate today = LocalDate.now();
        Date date = Date.from(today.atStartOfDay(ZoneId.systemDefault()).toInstant());

        log.info("=> {}", date);
    }

    /**
     * Date 转 LocalDate对象
     */
    @Test
    public void testDate2LocalDate() {

    }


    /**
     * 获取第一天、最后一天
     */
    @Test
    public void testTemporalAdjusters() {
        LocalDate today = LocalDate.now();

        // 月份第一天
        LocalDate firstDayOfMonth = today.with(TemporalAdjusters.firstDayOfMonth());
        log.info("firstDayOfMonth => {}", firstDayOfMonth);

        // 月份最后一天
        LocalDate lastDayOfMonth = today.with(TemporalAdjusters.lastDayOfMonth());
        log.info("lastDayOfMonth => {}", lastDayOfMonth);
    }


}
