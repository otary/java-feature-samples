package cn.chenzw.java.feature.io;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.io.IOException;

@Slf4j
@RunWith(JUnit4.class)
public class FileTests {

    @Test
    public void test() throws IOException {
        File file = new File("/test/file/a.txt");
        // 创建文件时，需要先创建父级目录
        if (!file.exists()) {
            file.getParentFile().mkdirs(); // 创建目录
            file.createNewFile(); // 创建空文件
        }

        // 属性信息
        log.info("isHidden => {}", file.isHidden());
        log.info("isFile => {}", file.isFile());
        log.info("isDirectory => {}", file.isDirectory());
        log.info("isAbsolute => {}", file.isAbsolute());
        log.info("canRead => {}", file.canRead());
        log.info("canWrite => {}", file.canWrite());
        log.info("canExecute => {}", file.canExecute());

        // 获取路径信息
        log.info("getName => {}", file.getName());
        log.info("getPath => {}", file.getPath());  // 路径（可能是相对路径）
        log.info("getAbsolutePath => {}", file.getAbsolutePath());  // 全路径
        log.info("getCanonicalPath => {}", file.getCanonicalPath());  //
        log.info("toURI => {}", file.toURI());
        log.info("toPath => {}", file.toPath());

        // 空间大小
        log.info("getTotalSpace => {}", file.getTotalSpace());
        log.info("getFreeSpace => {}", file.getFreeSpace());
        log.info("getUsableSpace => {}", file.getUsableSpace());


        // 重命名
        boolean b = file.renameTo(new File("/test/file/b.txt"));
        log.info("重命名结果 => {}", b);

    }
}
