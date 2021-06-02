package cn.chenzw.java.feature.security.cert;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.FileInputStream;
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


//        //获得证书版本
//        System.out.println("证书生效日期:"+info);
//        Date afterdate = oCert.getNotAfter();
//        info = dateformat.format(afterdate);
//        System.out.println("证书失效日期:"+info);
//        //获得证书主体信息
//        info = oCert.getSubjectDN().getName();
//        System.out.println("证书拥有者:"+info);
//        //获得证书颁发者信息
//        info = oCert.getIssuerDN().getName();
//        System.out.println("证书颁发者:"+info);
//        //获得证书签名算法名称
//        info = oCert.getSigAlgName();
//        System.out.println("证书签名算法:"+info);


    }
}
