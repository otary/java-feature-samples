package test;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.cert.*;
import java.util.Date;
import java.util.Enumeration;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;


public class X509CertDaoImpl implements X509Dao {


    public static final String Default_keyType = "PKCS12";
    public static final String Default_KeyPairGenerator = "RSA";
    public static final String Default_Signature = "SHA1withRSA";
    public static final String cert_type = "X509";
    public static final Integer Default_KeySize = 2048;

    static {
        // 系统添加BC加密算法 以后系统中调用的算法都是BC的算法
        Security.addProvider(new BouncyCastleProvider());
    }

    @Override
    public void createCert(String issuer, Date notBefore, Date notAfter, String certDestPath,
                           BigInteger serial, String keyPassword, String alias) throws Exception {
        //产生公私钥对
        KeyPairGenerator kpg = KeyPairGenerator.getInstance(Default_KeyPairGenerator);
        kpg.initialize(Default_KeySize);
        KeyPair keyPair = kpg.generateKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        // 组装证书
        X500Name issueDn = new X500Name(issuer);
        X500Name subjectDn = new X500Name(issuer);
        //组装公钥信息
        SubjectPublicKeyInfo subjectPublicKeyInfo = SubjectPublicKeyInfo
                .getInstance(new ASN1InputStream(publicKey.getEncoded())
                        .readObject());

        X509v3CertificateBuilder builder = new X509v3CertificateBuilder(
                issueDn, serial, notBefore, notAfter, subjectDn,
                subjectPublicKeyInfo);
        //证书的签名数据
        ContentSigner sigGen = new JcaContentSignerBuilder(Default_Signature).build(privateKey);
        X509CertificateHolder holder = builder.build(sigGen);
        byte[] certBuf = holder.getEncoded();
        X509Certificate certificate = (X509Certificate) CertificateFactory.getInstance(cert_type).generateCertificate(new ByteArrayInputStream(certBuf));

        String xxxx = convertToPem(certificate);
        System.out.println(xxxx);

        String s = convertToPrivateKey(privateKey);

        System.out.println(" => " + s);

        covertToPublicKey(certificate);

        // 创建KeyStore,存储证书
        KeyStore store = KeyStore.getInstance(Default_keyType);
        store.load(null, null);
        store.setKeyEntry(alias, keyPair.getPrivate(),
                keyPassword.toCharArray(), new Certificate[]{certificate});
        FileOutputStream fout = new FileOutputStream(certDestPath);
        store.store(fout, keyPassword.toCharArray());
        fout.close();
    }


    @Override
    public void printCert(String certPath, String keyPassword) throws Exception {
        char[] charArray = keyPassword.toCharArray();
        KeyStore ks = KeyStore.getInstance(Default_keyType);
        FileInputStream fis = new FileInputStream(certPath);
        ks.load(fis, charArray);
        fis.close();
        System.out.println("keystore type=" + ks.getType());
        Enumeration enumas = ks.aliases();
        String keyAlias = null;
        if (enumas.hasMoreElements()) {
            keyAlias = (String) enumas.nextElement();
            System.out.println("alias=[" + keyAlias + "]");
        }
        System.out.println("is key entry=" + ks.isKeyEntry(keyAlias));
        PrivateKey prikey = (PrivateKey) ks.getKey(keyAlias, charArray);
        Certificate cert = ks.getCertificate(keyAlias);
        PublicKey pubkey = cert.getPublicKey();
        System.out.println("cert class = " + cert.getClass().getName());
        System.out.println("cert = " + cert);
        System.out.println("public key = " + pubkey);
        System.out.println("private key = " + prikey);
    }

    /**
     * 生成PEM
     * @param cert
     * @return
     * @throws CertificateEncodingException
     */
    protected static String convertToPem(X509Certificate cert) throws CertificateEncodingException {
        Base64 encoder = new Base64(64);
        String cert_begin = "-----BEGIN CERTIFICATE-----\n";
        String end_cert = "-----END CERTIFICATE-----";

        byte[] derCert = cert.getEncoded();

        System.out.println(new String(derCert));
        String pemCertPre = new String(encoder.encode(derCert));
        String pemCert = cert_begin + pemCertPre + end_cert;
        return pemCert;
    }

    public String convertToPrivateKey(PrivateKey privateKey) {
        Base64 encoder = new Base64(64);
        String cert_begin = "-----BEGIN RSA PRIVATE KEY-----\n";
        String end_cert = "-----END RSA PRIVATE KEY-----";
        String pemCertPre = new String(encoder.encode(privateKey.getEncoded()));
        String pemCert = cert_begin + pemCertPre + end_cert;
        return pemCert;
    }

    public String covertToPublicKey(X509Certificate cert) throws CertificateException {
        //获取X.509对象工厂
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        //获取公钥对象
        PublicKey publicKey = cert.getPublicKey();

        Base64 base64Encoder = new Base64(64);
        String publicKeyString = new String(base64Encoder.encode(publicKey.getEncoded()));
        System.out.println("-----------------公钥--------------------");
        System.out.println(publicKeyString);
        System.out.println("-----------------公钥--------------------");

        return "";
    }


    @Override
    public PublicKey getPublicKey(String certPath, String keyPassword) throws Exception {
        char[] charArray = keyPassword.toCharArray();
        KeyStore ks = KeyStore.getInstance(Default_keyType);
        FileInputStream fis = new FileInputStream(certPath);
        ks.load(fis, charArray);
        fis.close();
        Enumeration enumas = ks.aliases();
        String keyAlias = null;
        if (enumas.hasMoreElements()) {
            keyAlias = (String) enumas.nextElement();
            Certificate certificate = ks.getCertificate(keyAlias);

            return ks.getCertificate(keyAlias).getPublicKey();
        }
        return null;
    }

    @Override
    public PrivateKey getPrivateKey(String certPath, String keyPassword) throws Exception {
        char[] charArray = keyPassword.toCharArray();
        KeyStore ks = KeyStore.getInstance(Default_keyType);
        FileInputStream fis = new FileInputStream(certPath);
        ks.load(fis, charArray);
        fis.close();
        Enumeration enumas = ks.aliases();
        String keyAlias = null;
        if (enumas.hasMoreElements()) {
            keyAlias = (String) enumas.nextElement();
            Certificate certificate = ks.getCertificate(keyAlias);

            return (PrivateKey) ks.getKey(keyAlias, charArray);
        }
        return null;
    }


    @Override
    public void certDelayTo(Date endTime, String certPath, String password) throws Exception {

    }

    @Override
    public void changePassword(String certPath, String oldPwd, String newPwd) throws Exception {
        KeyStore ks = KeyStore.getInstance(Default_keyType);
        FileInputStream fis = new FileInputStream(certPath);
        ks.load(fis, oldPwd.toCharArray());
        fis.close();
        FileOutputStream output = new FileOutputStream(certPath);
        ks.store(output, newPwd.toCharArray());
        output.close();
    }

    @Override
    public void deleteAlias(String certPath, String password, String alias, String entry) throws Exception {
        char[] charArray = password.toCharArray();
        KeyStore ks = KeyStore.getInstance(Default_keyType);
        FileInputStream fis = new FileInputStream(certPath);
        ks.load(fis, charArray);
        fis.close();
        if (ks.containsAlias(alias)) {
            ks.deleteEntry(entry);
            FileOutputStream output = new FileOutputStream(certPath);
            ks.store(output, password.toCharArray());
            output.close();
        } else {
            throw new Exception("该证书未包含别名--->" + alias);
        }
    }

    public static void main(String[] args) throws Exception {
        X509Dao impl = new X509CertDaoImpl();
        String issuer = "C=CN,ST=BJ,L=BJ,O=testserver,OU=testserver,CN=testserver";
        String certDestPath = "test.p12";
        BigInteger serial = BigInteger.valueOf(System.currentTimeMillis());
        String keyPassword = "123";
        String alias = "test";
        impl.createCert(issuer, new Date(), new Date("2017/09/27"), certDestPath, serial, keyPassword, alias);
        //impl.changePassword(certDestPath, "123", "123");
        //impl.createCert(issuer, new Date(), new Date("2017/09/27"), certDestPath, serial, keyPassword, alias);
        //未实现
        //impl.certDelayTo(new Date("2017/09/28"), certDestPath, keyPassword);
        impl.printCert(certDestPath, keyPassword);
    }


}
