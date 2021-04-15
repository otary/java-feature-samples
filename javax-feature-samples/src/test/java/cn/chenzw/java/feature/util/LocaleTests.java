package cn.chenzw.java.feature.util;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Locale;

@RunWith(JUnit4.class)
public class LocaleTests {


    @Test
    public void testGetDefault() {
        Locale defaultLocale = Locale.getDefault();

        Assert.assertEquals("CN", defaultLocale.getCountry());
        Assert.assertEquals("中国", defaultLocale.getDisplayCountry());
        Assert.assertEquals("中文", defaultLocale.getDisplayLanguage());
        Assert.assertEquals("zh", defaultLocale.getLanguage());
        Assert.assertEquals("中文 (中国)", defaultLocale.getDisplayName());
    }


}
