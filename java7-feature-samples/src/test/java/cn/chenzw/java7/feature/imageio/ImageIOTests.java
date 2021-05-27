package cn.chenzw.java7.feature.imageio;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

@Slf4j
@RunWith(JUnit4.class)
public class ImageIOTests {


    /**
     * 支持的文件名
     */
    @Test
    public void testSupport() {
        String[] readerFormatNames = ImageIO.getReaderFormatNames();
        log.info("支持读取的文件名 => {}", Arrays.asList(readerFormatNames));

        String[] writerFormatNames = ImageIO.getWriterFormatNames();
        log.info("支持写入的文件名 => {}", Arrays.asList(writerFormatNames));

        File cacheDirectory = ImageIO.getCacheDirectory();
        log.info("cacheDirectory => {}", cacheDirectory);

        String[] readerMIMETypes = ImageIO.getReaderMIMETypes();
        log.info("支持读的MIME类型 => {}", Arrays.asList(readerMIMETypes));

        String[] writerMIMETypes = ImageIO.getWriterMIMETypes();
        log.info("支持写的MIME类型 => {}", Arrays.asList(writerMIMETypes));

        String[] readerFileSuffixes = ImageIO.getReaderFileSuffixes();
        log.info("支持读文件后缀 => {}", Arrays.asList(readerFileSuffixes));

        String[] writerFileSuffixes = ImageIO.getWriterFileSuffixes();
        log.info("支持写文件后缀 => {}", Arrays.asList(writerFileSuffixes));
    }


    @Test
    public void testRead() throws IOException {
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("images/1.gif");

        BufferedImage bufferedImage = ImageIO.read(is);
        log.info("图片宽度 => {}", bufferedImage.getWidth());
        log.info("图片高度 => {}", bufferedImage.getHeight());
        log.info("图片属性 => {}", bufferedImage.getPropertyNames() == null ? bufferedImage : Arrays.asList(bufferedImage.getPropertyNames()));
        log.info("Type => {}", bufferedImage.getType());
        log.info("透明度 => {}", bufferedImage.getTransparency());
        log.info("getTileWidth => {}", bufferedImage.getTileWidth());




        //   ImageIO.read(is);
    }
}
