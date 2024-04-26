package cn.chenzw.java7.feature.io;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


/**
 * @author chenzw
 */
@Slf4j
@RunWith(JUnit4.class)
public class AutoCloseableTests {

    /**
     * 由于obtainInputStream()中使用try...catch...自动关闭InputStream流，所以此处会报错
     */
    @Test
    public void testError() {
        InputStream is = obtainInputStream();
        try (BufferedReader buffer = new BufferedReader(new InputStreamReader(is))) {
            log.info(" => {}", buffer.readLine());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * InputStream流应该放在外层关闭
     */
    @Test
    public void testSuccess() {
        try (InputStream is = obtainInputStream2()) {
            try (BufferedReader buffer = new BufferedReader(new InputStreamReader(is))) {
                log.info(" => {}", buffer.readLine());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public InputStream obtainInputStream() {
        try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("a.txt")) {
            return is;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public InputStream obtainInputStream2() {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream("a.txt");
    }
}
