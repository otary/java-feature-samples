package cn.chenzw.java7.feature.imageio;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Iterator;

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
    }

    /**
     * 加水印
     *
     * @throws IOException
     */
    @Test
    public void testAddWaterMask() throws IOException {
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("images/flower.jpg");
        BufferedImage bufferedImage = ImageIO.read(is);

        int imageWidth = bufferedImage.getWidth();
        int imageHeight = bufferedImage.getHeight();

        // 加水印层
        BufferedImage bufImg =
                new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        //获取 Graphics2D 对象
        Graphics2D g = bufImg.createGraphics();
        //设置绘图区域
        g.drawImage(bufferedImage, 0, 0, imageWidth, imageHeight, null);
        //设置字体
        Font font = new Font("宋体", Font.PLAIN, 20);
        // 根据图片的背景设置水印颜色
        g.setColor(Color.green);
        g.setFont(font);

        String waterMaskText = "测试水印";
        //获取文字长度
        int len = g.getFontMetrics(
                g.getFont()).charsWidth(waterMaskText.toCharArray(),
                0, waterMaskText.length());
        int x = imageWidth - len - 10;
        int y = imageHeight - 20;
        g.drawString(waterMaskText, x, y);
        g.dispose();

        File resultFile = new File("result/flower.png");
        if (!resultFile.getParentFile().exists()) {
            resultFile.getParentFile().mkdirs();
        }
        ImageIO.write(bufferedImage, "png", resultFile);
    }

    @Test
    public void thumbnail() throws IOException {
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("images/flower.jpg");
        BufferedImage srcImage = ImageIO.read(is);

        //定义一个BufferedImage对象，用于保存缩小后的位图
        BufferedImage bufferedImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = bufferedImage.getGraphics();
        graphics.drawImage(srcImage, 0, 0, 100, 100, null);
        graphics.dispose();

        File resultFile = new File("result/flower.png");
        if (!resultFile.getParentFile().exists()) {
            resultFile.getParentFile().mkdirs();
        }
        ImageIO.write(bufferedImage, "png", resultFile);
    }

    /**
     * 范围截取1
     */
    @Test
    public void cut1() throws IOException {
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("images/flower.jpg");
        BufferedImage srcImage = ImageIO.read(is);

        //定义一个BufferedImage对象，用于保存缩小后的位图
        BufferedImage bufferedImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = bufferedImage.getGraphics();
        graphics.drawImage(srcImage, 0, 0, 500, 500, null);
        graphics.dispose();

        File resultFile = new File("result/flower.png");
        if (!resultFile.getParentFile().exists()) {
            resultFile.getParentFile().mkdirs();
        }
        ImageIO.write(bufferedImage, "png", resultFile);
    }

    /**
     * 范围截取2
     */
    @Test
    public void cut2() throws IOException {
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("images/flower.jpg");

        ImageInputStream imageStream = null;
        try {
            // 根据图片格式获取ImageReader
            Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName("jpg");
            ImageReader reader = readers.next();
            imageStream = ImageIO.createImageInputStream(is);
            reader.setInput(imageStream, true);
            ImageReadParam param = reader.getDefaultReadParam();

            // 截取的图片范围
            Rectangle rect = new Rectangle(0, 0, 500, 100);
            param.setSourceRegion(rect);
            BufferedImage bufferedImage = reader.read(0, param);

            File resultFile = new File("result/flower.jpg");
            if (!resultFile.getParentFile().exists()) {
                resultFile.getParentFile().mkdirs();
            }
            ImageIO.write(bufferedImage, "jpg", resultFile);

        } finally {
            imageStream.close();
        }
    }
}
