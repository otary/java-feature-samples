package cn.chenzw.java7.feature.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Map;
import java.util.Properties;

@Slf4j
@RunWith(JUnit4.class)
public class SystemTests {

    @Test
    public void test() {
        Properties properties = System.getProperties();
        log.info("System.getProperties => {}", properties);

        Map<String, String> envMap = System.getenv();
        log.info("System.getenv => {}", envMap);
    }
}
