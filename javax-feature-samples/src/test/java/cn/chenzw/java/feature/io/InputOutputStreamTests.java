package cn.chenzw.java.feature.io;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

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
    public void testZipInputStream() throws IOException {
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("io/test.zip");
        ZipInputStream zis = new ZipInputStream(is);
        ZipEntry zipEntry = null;
        while ((zipEntry = zis.getNextEntry()) != null) {
            String name = zipEntry.getName();
            System.out.println(name);
        }
        zis.close();
    }

    @Test
    public void testZipOutputStream() throws IOException {
        ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(new File("result.zip")));
        for (int i = 0; i < 10; i++) {
            ZipEntry zipEntry = new ZipEntry("pp" + i + "/test" + i + ".txt");
            zos.putNextEntry(zipEntry);

            String content = "内容" + i;
            zos.write(content.getBytes(), 0, content.getBytes().length);

            zos.closeEntry();
        }
        zos.finish();
        zos.close();
    }


    /**
     * 根据字节流判断文件类型
     */
    @Test
    public void testGetFileType() throws IOException {
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("io/test.pdf");

        // 缓存is
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buff = new byte[1024];
        int bytesRead = 0;
        while ((bytesRead = is.read(buff)) != -1) {
            baos.write(buff, 0, bytesRead);
        }

        //
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());

        // 读取文件的前4个字节来判断文件类型
        byte[] bytes = new byte[8];
        bais.read(bytes, 0, 8);

        String type = Hex.encodeHexString(bytes).toUpperCase();
        log.info("type => {}", type);
    }

}
