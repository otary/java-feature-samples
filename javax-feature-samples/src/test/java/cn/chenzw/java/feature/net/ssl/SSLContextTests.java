package cn.chenzw.java.feature.net.ssl;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import javax.net.ssl.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.Arrays;

@Slf4j
@RunWith(JUnit4.class)
public class SSLContextTests {


    /**
     * 查看当前JRE支持的SSL协议版本
     *
     * @throws IOException
     */
    @Test
    public void testSSLSupport() throws IOException {
        SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        SSLSocket sslSocket = (SSLSocket) factory.createSocket();

        String[] protocols = sslSocket.getEnabledProtocols();

        // 默认不支持SSL协议，只支持TLS协议
        log.info("protocols => {}", Arrays.toString(protocols)); // => [TLSv1.3, TLSv1.2, TLSv1.1, TLSv1]
    }


    /**
     * 查看当前JRE启用的加密算法套件
     */
    @Test
    public void testCipherSuitesEnabled() throws IOException {
        SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        SSLSocket sslSocket = (SSLSocket) factory.createSocket();
        String[] cipers = sslSocket.getEnabledCipherSuites();

        log.info("EnabledCipherSuites => {}", Arrays.toString(cipers));  // => [TLS_AES_128_GCM_SHA256, TLS_AES_256_GCM_SHA384, TLS_CHACHA20_POLY1305_SHA256, TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384, TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256, TLS_ECDHE_ECDSA_WITH_CHACHA20_POLY1305_SHA256, TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384, TLS_ECDHE_RSA_WITH_CHACHA20_POLY1305_SHA256, TLS_RSA_WITH_AES_256_GCM_SHA384, TLS_ECDH_ECDSA_WITH_AES_256_GCM_SHA384, TLS_ECDH_RSA_WITH_AES_256_GCM_SHA384, TLS_DHE_RSA_WITH_AES_256_GCM_SHA384, TLS_DHE_RSA_WITH_CHACHA20_POLY1305_SHA256, TLS_DHE_DSS_WITH_AES_256_GCM_SHA384, TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256, TLS_RSA_WITH_AES_128_GCM_SHA256, TLS_ECDH_ECDSA_WITH_AES_128_GCM_SHA256, TLS_ECDH_RSA_WITH_AES_128_GCM_SHA256, TLS_DHE_RSA_WITH_AES_128_GCM_SHA256, TLS_DHE_DSS_WITH_AES_128_GCM_SHA256, TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA384, TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA384, TLS_RSA_WITH_AES_256_CBC_SHA256, TLS_ECDH_ECDSA_WITH_AES_256_CBC_SHA384, TLS_ECDH_RSA_WITH_AES_256_CBC_SHA384, TLS_DHE_RSA_WITH_AES_256_CBC_SHA256, TLS_DHE_DSS_WITH_AES_256_CBC_SHA256, TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA, TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA, TLS_RSA_WITH_AES_256_CBC_SHA, TLS_ECDH_ECDSA_WITH_AES_256_CBC_SHA, TLS_ECDH_RSA_WITH_AES_256_CBC_SHA, TLS_DHE_RSA_WITH_AES_256_CBC_SHA, TLS_DHE_DSS_WITH_AES_256_CBC_SHA, TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA256, TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA256, TLS_RSA_WITH_AES_128_CBC_SHA256, TLS_ECDH_ECDSA_WITH_AES_128_CBC_SHA256, TLS_ECDH_RSA_WITH_AES_128_CBC_SHA256, TLS_DHE_RSA_WITH_AES_128_CBC_SHA256, TLS_DHE_DSS_WITH_AES_128_CBC_SHA256, TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA, TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA, TLS_RSA_WITH_AES_128_CBC_SHA, TLS_ECDH_ECDSA_WITH_AES_128_CBC_SHA, TLS_ECDH_RSA_WITH_AES_128_CBC_SHA, TLS_DHE_RSA_WITH_AES_128_CBC_SHA, TLS_DHE_DSS_WITH_AES_128_CBC_SHA, TLS_EMPTY_RENEGOTIATION_INFO_SCSV]
    }

    /**
     * 查看JRE支持的TrustManager
     *
     * @throws IOException
     * @throws KeyStoreException
     * @throws CertificateException
     * @throws NoSuchAlgorithmException
     */
    @Test
    public void testTrustManagerSupport() throws IOException, KeyStoreException, CertificateException, NoSuchAlgorithmException {
        // 查找本地证书库
        File cacertsFile = findCacertsFile();

        // keystore默认密码: changeit
        String defaultPassphrase = "changeit";

        // 加载KeyStore
        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        keyStore.load(new FileInputStream(cacertsFile), defaultPassphrase.toCharArray());

        log.info("Loading KeyStore [{}] Success!", cacertsFile);

        TrustManagerFactory trustManagerFactory =
                TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keyStore);

        TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
        for (TrustManager trustManager : trustManagers) {
            log.info("=> {}", trustManager.getClass().getName()); // => sun.security.ssl.X509TrustManagerImpl
        }
    }

    @Test
    public void test() throws NoSuchAlgorithmException, KeyStoreException, IOException, CertificateException {
        // 查找本地证书库
        File cacertsFile = findCacertsFile();

        // keystore默认密码: changeit
        String defaultPassphrase = "changeit";

        // 加载KeyStore
        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        keyStore.load(new FileInputStream(cacertsFile), defaultPassphrase.toCharArray());

        log.info("Loading KeyStore [{}] Success!", cacertsFile);

        SSLContext sslContext = SSLContext.getInstance("TLS");
        TrustManagerFactory trustManagerFactory =
                TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keyStore);

        X509TrustManager defaultTrustManager = (X509TrustManager) trustManagerFactory.getTrustManagers()[0];

//        SavingTrustManager tm = new SavingTrustManager(defaultTrustManager);
//        context.init(null, new TrustManager[] {tm}, null);
//        SSLSocketFactory factory = context.getSocketFactory();



    }

    /**
     * 查找证书库文件
     *
     * @return
     */
    private static File findCacertsFile() {

        /**
         * 查找顺序
         * javax.net.ssl.trustStore
         * java-home/lib/security/jssecacerts
         * java-home/lib/security/cacerts
         */
        File securityDir = new File(System.getProperty("java.home") +
                File.separator
                + "lib" +
                File.separator
                + "security");
        File file = new File(securityDir, "jssecacerts");
        if (!file.isFile()) {
            file = new File(securityDir, "cacerts");
        }
        return file;
    }


}
