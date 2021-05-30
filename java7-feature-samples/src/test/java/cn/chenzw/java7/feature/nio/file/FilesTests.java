package cn.chenzw.java7.feature.nio.file;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;

@Slf4j
@RunWith(JUnit4.class)
public class FilesTests {


    /**
     * 根据文件获取ContentType类型
     */
    @Test
    public void testProbeContentType() throws IOException {
        URL resource = Thread.currentThread().getContextClassLoader().getResource("images/1.gif");
        String contentType = Files.probeContentType(new File(resource.getPath()).toPath());

        log.info("contentType => {}", contentType);

        Assert.assertEquals("image/gif", contentType);
    }

}
