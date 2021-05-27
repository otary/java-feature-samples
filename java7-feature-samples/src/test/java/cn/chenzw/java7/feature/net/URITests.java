package cn.chenzw.java7.feature.net;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.net.URI;
import java.net.URISyntaxException;

@Slf4j
@RunWith(JUnit4.class)
public class URITests {

    @Test
    public void test() throws URISyntaxException {
        URI uri = new URI("https://www.baidu.com#tt?q=xxx&s=yyy");

        log.info("getFragment => {}", uri.getFragment()); // => tt?q=xxx&s=yyy
        log.info("getHost => {}", uri.getHost());  // => www.baidu.com
        log.info("getPath => {}", uri.getPath()); // => 空
        log.info("getPort => {}", uri.getPort());  // => -1
        log.info("getQuery => {}", uri.getQuery());  // => null
        log.info("getRawPath => {}", uri.getRawPath());  // => 空
        log.info("getRawFragment => {}", uri.getRawFragment());  // tt?q=xxx&s=yyy
        log.info("getRawQuery => {}", uri.getRawQuery());  // => null
        log.info("getScheme => {}", uri.getScheme());  // => https
        log.info("getSchemeSpecificPart => {}", uri.getSchemeSpecificPart()); // => //www.baidu.com
    }
}
