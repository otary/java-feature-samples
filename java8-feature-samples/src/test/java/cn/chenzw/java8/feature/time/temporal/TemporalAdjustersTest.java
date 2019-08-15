package cn.chenzw.java8.feature.time.temporal;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

@RunWith(JUnit4.class)
public class TemporalAdjustersTest {


    @Test
    public void testBasic() {

        LocalDate localDate = LocalDate.of(2019, 1, 21);

        // 当月第一天
        Assert.assertEquals("2019-01-01", localDate.with(TemporalAdjusters.firstDayOfMonth()).toString());

        // 当月最后一天
        Assert.assertEquals("2019-01-31", localDate.with(TemporalAdjusters.lastDayOfMonth()).toString());

        // 当年第一天
        Assert.assertEquals("2019-01-01", localDate.with(TemporalAdjusters.firstDayOfYear()).toString());

        // 当年最后一天
        Assert.assertEquals("2019-12-31", localDate.with(TemporalAdjusters.lastDayOfYear()).toString());

        // 下个月的第一天
        Assert.assertEquals("2019-02-01", localDate.with(TemporalAdjusters.firstDayOfNextMonth()).toString());

        // 下一年的第一天
        Assert.assertEquals("2020-01-01", localDate.with(TemporalAdjusters.firstDayOfNextYear()).toString());

        // 上周五
        Assert.assertEquals("2019-01-18", localDate.with(TemporalAdjusters.previous(DayOfWeek.FRIDAY)).toString());

        // 下周五
        Assert.assertEquals("2019-01-25", localDate.with(TemporalAdjusters.next(DayOfWeek.FRIDAY)).toString());
        Assert.assertEquals("2019-01-25", localDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.FRIDAY)).toString());

    }


}
