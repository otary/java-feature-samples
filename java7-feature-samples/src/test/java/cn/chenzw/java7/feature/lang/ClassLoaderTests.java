package cn.chenzw.java7.feature.lang;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

@Slf4j
@RunWith(JUnit4.class)
public class ClassLoaderTests {


    /**
     * 加载文件
     *
     * @throws IOException
     */
    @Test
    public void testGetResources() throws IOException {
        ClassLoader classLoader = getClassLoader();

        // 加载class文件
        Enumeration<URL> resources = classLoader.getResources("cn/chenzw/java7/feature/util/concurrent/MyTask.class");
        while (resources.hasMoreElements()) {
            log.info("resource => {}", resources.nextElement());
        }

        // 加载文件
        Enumeration<URL> resources2 = classLoader.getResources("log4j2.xml");
        while (resources2.hasMoreElements()) {
            log.info("resource2 => {}", resources2.nextElement());
        }
    }


    private ClassLoader getClassLoader() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader == null) {
            classLoader = ClassLoaderTests.class.getClassLoader();
            if (classLoader == null) {
                classLoader = ClassLoader.getSystemClassLoader();
            }
        }
        return classLoader;
    }
}
