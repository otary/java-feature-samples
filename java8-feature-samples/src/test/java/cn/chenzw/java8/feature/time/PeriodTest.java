package cn.chenzw.java8.feature.time;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.time.LocalDate;
import java.time.Period;

@RunWith(JUnit4.class)
public class PeriodTest {


    @Test
    public void test() {
        LocalDate day1 = LocalDate.of(2015, 10, 2);
        LocalDate day2 = LocalDate.of(2019, 1, 3);

        Period period = Period.between(day1, day2);

        // 差3个月
        Assert.assertEquals(3, period.getYears());

        // 差3个月
        Assert.assertEquals(3, period.getMonths());

        // 差1天
        Assert.assertEquals(1, period.getDays());
    }

}
