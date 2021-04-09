package cn.chenzw.java9.feature.net.http;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Slf4j
@RunWith(JUnit4.class)
public class HttpClientTests {


    @Test
    public void testGet() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest req =
                HttpRequest.newBuilder(URI.create("https://www.baidu.com"))
                        .header("User-Agent", "Java")
                        .GET()
                        .build();
        HttpResponse<String> resp = client.send(req, HttpResponse.BodyHandlers.ofString());

        log.info("body => {}", resp.body());
    }
}
