package cn.chenzw.java7.feature.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.Properties;

@Slf4j
@RunWith(JUnit4.class)
public class PropertiesTests {


    /**
     * 支持加载properties、yaml、xml文件
     *
     * @throws IOException
     */
    @Test
    public void testLoad() throws IOException {
        loadResource("test/a.properties");

        loadResource("test/a.yml");
    }

    public void loadResource(String path) throws IOException {
        // 先找到文件路径
        Enumeration<URL> resources = Thread.currentThread().getContextClassLoader().getResources("test/a.yml");

        while (resources.hasMoreElements()) {
            URL url = resources.nextElement();
            URLConnection con = null;
            try {
                con = url.openConnection();
            } catch (IOException ex) {
                if (con instanceof HttpURLConnection) {
                    ((HttpURLConnection) con).disconnect();
                }
                throw ex;
            }

            // 解析文件
            Properties properties = new Properties();
            properties.load(con.getInputStream());

            log.info("{} => {}", path, properties);
        }
    }
}
