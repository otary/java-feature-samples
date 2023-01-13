package cn.chenzw.java.feature.net.ssl;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import sun.misc.BASE64Encoder;
import sun.security.pkcs10.PKCS10;
import sun.security.tools.keytool.CertAndKeyGen;
import sun.security.x509.X500Name;

import javax.net.ssl.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Date;

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
        File caCertsFile = findCacertsFile();

        // keystore默认密码: changeit
        String defaultPassphrase = "changeit";

        // 加载KeyStore
        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        keyStore.load(new FileInputStream(caCertsFile), defaultPassphrase.toCharArray());

        log.info("Loading KeyStore [{}] Success!", caCertsFile);

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


    @Test
    public void testCreateKeyStore() throws KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException, NoSuchProviderException, InvalidKeyException, SignatureException {
        KeyStore keyStore = KeyStore.getInstance("pkcs12");
        keyStore.load(null, null);

        CertAndKeyGen keyGen = new CertAndKeyGen("RSA", "SHA1WithRSA", null);
        X500Name x500Name = new X500Name(
                "com.bqrdh.www", // 通用名称
                "ff",  // 组织
                "ffcs",  // 部门
                "fz",  // 城市
                "fj",  // 省份
                "cn" // 国家
        );
        int keySize = 2048;
        keyGen.generate(keySize);

        PrivateKey privateKey = keyGen.getPrivateKey();
        long validity = 3600; // 有效期 - 10年
        X509Certificate[] x509Certificate = new X509Certificate[]{
                keyGen.getSelfCertificate(x500Name, new Date(), (long) validity * 24 * 60 * 60)
        };
        String alias = "bqrdh";  // 别名
        //char[] keyPassword = "123456".toCharArray();  // 密钥口令
        char[] keyPassword = new char[]{};
        FileOutputStream fos = new FileOutputStream("a.keystore");
        keyStore.setKeyEntry(alias, privateKey, keyPassword, x509Certificate);
        keyStore.store(fos, keyPassword);
        fos.close();
    }


    @Test
    public void testCreateCsr() throws NoSuchProviderException, NoSuchAlgorithmException, InvalidKeyException, IOException, CertificateException, SignatureException {
        CertAndKeyGen keyGen = new CertAndKeyGen("RSA", "SHA1WithRSA", null);
        X500Name x500Name = new X500Name(
                "com.bqrdh.www", // 通用名称
                "ff",  // 组织
                "ffcs",  // 部门
                "fz",  // 城市
                "fj",  // 省份
                "cn" // 国家
        );
        keyGen.generate(2048);

        PublicKey publicKey = keyGen.getPublicKey();
        PrivateKey privateKey = keyGen.getPrivateKey();
        Signature signature = Signature.getInstance("SHA1WithRSA");
        signature.initSign(privateKey);

        PKCS10 pkcs10 = new PKCS10(publicKey);
        pkcs10.encodeAndSign(x500Name, signature);

        BASE64Encoder base64 = new BASE64Encoder();
        String content = base64.encode(pkcs10.getEncoded());

        log.info("{}", content);

    }
}
