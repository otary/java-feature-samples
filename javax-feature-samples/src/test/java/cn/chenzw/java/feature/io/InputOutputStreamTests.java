package cn.chenzw.java.feature.io;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Slf4j
@RunWith(JUnit4.class)
public class InputOutputStreamTests {

    /**
     * StringReader
     *
     * @throws IOException
     */
    @Test
    public void testString() throws IOException {
        char[] chars = new char[10];
        StringReader reader = new StringReader("12345678910111213");
        int len = -1;
        while ((len = reader.read(chars)) > -1) {
            log.info("read => {}", new String(chars, 0, len));
        }

        reader.close();
    }


    @Test
    public void test() throws IOException {
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("io/test.zip");
        ZipInputStream zis = new ZipInputStream(is);
        ZipEntry zipEntry = null;
        while ((zipEntry = zis.getNextEntry()) != null) {
            String name = zipEntry.getName();
            System.out.println(name);
        }

        zis.close();
    }


}
