package cn.chenzw.java8.feature.time;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.time.chrono.JapaneseDate;

@RunWith(JUnit4.class)
public class JapaneseDateTest {


    @Test
    public void testBasic() {
        JapaneseDate japaneseDate = JapaneseDate.of(2019, 10, 2);

        Assert.assertEquals("Japanese Heisei 31-10-02", japaneseDate.toString());
    }

}
