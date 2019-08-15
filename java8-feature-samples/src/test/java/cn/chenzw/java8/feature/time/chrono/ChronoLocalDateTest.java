package cn.chenzw.java8.feature.time.chrono;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.time.chrono.ChronoLocalDate;
import java.time.chrono.Chronology;
import java.util.Locale;

@RunWith(JUnit4.class)
public class ChronoLocalDateTest {

    @Test
    public void testBasic() {
        Chronology japaneseChronology = Chronology.ofLocale(Locale.JAPAN);

        ChronoLocalDate chronoLocalDate = japaneseChronology.date(2019, 1, 21);
        Assert.assertEquals("2019-01-21", chronoLocalDate.toString());
    }
}
