package cn.chenzw.java.feature.security.cert;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import sun.security.provider.certpath.OCSP;

import javax.net.ssl.HttpsURLConnection;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.URI;
import java.net.URL;
import java.security.*;
import java.security.cert.*;
import java.security.cert.Certificate;
import java.util.*;

/**
 * 证书
 *
 * @author chenzw
 */
@Slf4j
@RunWith(JUnit4.class)
public class CertificateFactoryTests {

    /**
     * 解析证书
     *
     * @throws CertificateException
     */
    @Test
    public void testParseFromChain() throws CertificateException {
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
        log.info("证书公钥 => {}", Base64.getEncoder().encodeToString(publicKey));

        log.info("{}", x509Certificate);
    }

    /**
     * 生成 X509Certificate 对象
     */
    @Test
    public void testCreateX509Certificate() throws NoSuchAlgorithmException, CertificateException, IOException, OperatorCreationException {
        //产生公私钥对
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(2048);
        KeyPair keyPair = kpg.generateKeyPair();
        PublicKey publicKey = keyPair.getPublic();    // 公钥
        PrivateKey privateKey = keyPair.getPrivate();   // 私钥

        /**
         * CN: 通用名称
         * OU：部门
         * O：组织
         * L：城市
         * ST：省份
         * C：国家
         */
        String issuer = "C=CN,ST=BJ,L=BJ,O=testserver,OU=testserver,CN=testserver";

        // 组装证书
        X500Name issueDn = new X500Name(issuer);
        X500Name subjectDn = new X500Name(issuer);

        // 组装公钥信息
        SubjectPublicKeyInfo subjectPublicKeyInfo = SubjectPublicKeyInfo
                .getInstance(new ASN1InputStream(publicKey.getEncoded())
                        .readObject());

        X509v3CertificateBuilder certBuilder = new X509v3CertificateBuilder(
                // 发行者信息
                issueDn,
                // 序列号
                BigInteger.valueOf(System.currentTimeMillis()),
                // 证书有效期 - 起始
                Calendar.getInstance().getTime(),
                // 证书有效期 - 截止
                new Date("2032/09/27"),
                subjectDn,
                // 公钥信息
                subjectPublicKeyInfo
        );

        // 证书的签名数据
        ContentSigner sigGen = new JcaContentSignerBuilder("SHA1withRSA").build(privateKey);
        byte[] certBuff = certBuilder.build(sigGen).getEncoded();

        // 生成X509证书对象
        X509Certificate certificate = (X509Certificate) CertificateFactory.getInstance("X509")
                .generateCertificate(new ByteArrayInputStream(certBuff));
        log.info("X509Certificate => {}", certificate);

        // 生成PEM证书
        String pemStr = generatePem(certificate);
        log.info("PEM => {}", pemStr);


        // 生成私钥文件
    }

    /**
     * 生成PEM证书
     *
     * @param certificate
     * @return
     * @throws CertificateEncodingException
     */
    private String generatePem(X509Certificate certificate) throws CertificateEncodingException {
        Base64.Encoder encoder = Base64.getEncoder();
        String certBegin = "-----BEGIN CERTIFICATE-----\n";
        String certEnd = "\n-----END CERTIFICATE-----";

        byte[] derCert = certificate.getEncoded();
        String pemCertPre = new String(encoder.encode(derCert));
        String pemCert = certBegin + pemCertPre + certEnd;
        return pemCert;
    }


    @Test
    public void testGetCertFromSite() throws IOException, CertificateParsingException, CertificateEncodingException {
        URL url = new URL("https://www.bqrdh.com/");
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        conn.connect();
        Certificate[] certificates = conn.getServerCertificates();    // 拿到完整的证书链
        for (Certificate certificate : certificates) {

            X509Certificate cert = (X509Certificate) certificate; // sun.security.x509.X509CertImpl
            log.info("证书版本 => {}", cert.getVersion());
            log.info("证书类型 => {}", cert.getType());
            log.info("证书序列号 => {}", cert.getSerialNumber());
            log.info("证书生效日期 => {}", cert.getNotBefore());
            log.info("证书失效日期 => {}", cert.getNotAfter());
            log.info("证书拥有者 => {}", cert.getSubjectDN().getName());
            log.info("证书颁发者 => {}", cert.getIssuerDN().getName());
            log.info("证书签名算法 => {}", cert.getSigAlgName());
            log.info("签名值 => {}", Arrays.toString(cert.getSignature()));
            log.info("CriticalExtensionOIDs => {}", cert.getCriticalExtensionOIDs());
            log.info("NonCriticalExtensionOIDs => {}", cert.getNonCriticalExtensionOIDs());
            log.info(" => {}", cert.getIssuerAlternativeNames());
            log.info(" => {}", cert.getBasicConstraints());
            log.info(" => {}", cert.getKeyUsage());
            log.info(" => {}", cert.getSigAlgParams());

            log.info("备用域名： => {}", cert.getSubjectAlternativeNames());

            byte[] publicKey = cert.getPublicKey().getEncoded();
            log.info("证书公钥 => {}", Base64.getEncoder().encodeToString(publicKey));
            log.info("证书CRT => {}", generatePem(cert));

            URI responderURI = OCSP.getResponderURI(cert);
            log.info("OCSP URI => {}", responderURI);

            log.info("MD5指纹 => {}", DigestUtils.md5Hex(cert.getEncoded()));
            log.info("SHA指纹 => {}", DigestUtils.sha1Hex(cert.getEncoded()));
            log.info("SHA256指纹 => {}", DigestUtils.sha256Hex(cert.getEncoded()));

            log.info("------------------------------------");
        }
        conn.disconnect();
    }

}
