package cn.chenzw.java7.feature.nio.file;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

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

    /**
     * 遍历、过滤文件树
     *
     * @throws IOException
     */
    @Test
    public void testWalkFileTree() throws IOException {
        // 当前项目目录
        String filePath = System.getProperty("user.dir");

        Files.walkFileTree(Paths.get(filePath), new SimpleFileVisitor<Path>() {

            @Override
            public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException {
                log.info("file => {}, attrs => {}", path, attrs);

                // 继续或跳过（可用于过滤文件）
                // return FileVisitResult.CONTINUE;
                return super.visitFile(path, attrs);
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc)
                    throws IOException {
                return FileVisitResult.CONTINUE;
            }
        });
    }

}
