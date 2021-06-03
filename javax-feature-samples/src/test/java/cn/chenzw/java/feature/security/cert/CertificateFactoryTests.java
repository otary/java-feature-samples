package cn.chenzw.java.feature.security.cert;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

/**
 * 证书
 *
 * @author chenzw
 */
@Slf4j
@RunWith(JUnit4.class)
public class CertificateFactoryTests {

    @Test
    public void test() throws CertificateException, FileNotFoundException {
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("certs/xxxx.chain.pem");

        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        X509Certificate x509Certificate = (X509Certificate) certificateFactory.generateCertificate(is);

        log.info("证书版本 => {}", x509Certificate.getVersion());
        log.info("证书序列号 => {}", x509Certificate.getSerialNumber().toString(16));
        log.info("证书生效日期 => {}", x509Certificate.getNotBefore());
        log.info("证书失效日期 => {}", x509Certificate.getNotAfter());

        log.info("证书拥有者 => {}", x509Certificate.getSubjectDN().getName());
        log.info("证书颁发者 => {}", x509Certificate.getIssuerDN().getName());
        log.info("证书签名算法 => {}", x509Certificate.getSigAlgName());

        byte[] publicKey = x509Certificate.getPublicKey().getEncoded();
        log.info("证书 =>", new String(publicKey));

    }
}
