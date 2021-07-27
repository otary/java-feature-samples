import org.bouncycastle.asn1.x500.X500Name;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.Enumeration;
import java.util.Random;

/**
 * Created by User on 2017/6/19.
 */
public class JksKeyStore {
    public static File jks = new File("keys.jks");
    public static String pwd = "password";
    private static KeyStore keyStore;

    static {
        try {
            keyStore = KeyStore.getInstance("jks");
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
    }

    public static void load() {
        if (!jks.exists()) {
            try {
                jks.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            keyStore.load(new FileInputStream(jks), pwd.toCharArray());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("load key store fail.............");
        }
    }

    public static void genKey() {
        try {
            keyStore.load(null, null);
            for (int i = 0; i < 10; i++) {
                //密钥生成
                KeyPair keyPair = KeyGen.gen("RSA", 1024);
                PublicKey publicKey = keyPair.getPublic();
                PrivateKey privateKey = keyPair.getPrivate();

                X500Name x500Name = X500NameGen.gen("org", "key", "container");
                BigInteger serial = BigInteger.probablePrime(32, new Random());
                X509Certificate certificate = CertGen.gen(publicKey, privateKey, x500Name, x500Name, serial, new Date(), new Date(), null);
                //存公钥和私钥
                keyStore.setKeyEntry("key" + i, privateKey, pwd.toCharArray(), new Certificate[]{certificate});
                //只存用户证书
                //keyStore.setCertificateEntry("key"+i,certificate);
            }
            keyStore.store(new FileOutputStream(jks), pwd.toCharArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void alias() {

        load();
        try {
            Enumeration<String> enumeration = keyStore.aliases();
            while (enumeration.hasMoreElements()) {
                String alias = enumeration.nextElement();
                System.out.println(alias);
                Certificate certificate = keyStore.getCertificate(alias);
                System.out.println(certificate.getPublicKey());
                PrivateKey privateKey = (PrivateKey) keyStore.getKey(alias, pwd.toCharArray());
                System.out.println(privateKey);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void clear() {
        load();
        try {
            Enumeration<String> enumeration = keyStore.aliases();
            while (enumeration.hasMoreElements()) {
                String alias = enumeration.nextElement();
                keyStore.deleteEntry(alias);
            }
            keyStore.store(new FileOutputStream(jks), pwd.toCharArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static KeyPair getKeyPair(String alias) {
        load();
        try {
            Certificate certificate = keyStore.getCertificate(alias);
            Key key = keyStore.getKey(alias, pwd.toCharArray());
            PrivateKey privateKey = null;
            if (key != null && key instanceof PrivateKey) {
                privateKey = (PrivateKey) key;
            }
            //公钥还原
            //X509EncodedKeySpec keySpec = new X509EncodedKeySpec(certificate.getPublicKey().getEncoded());
            //KeyFactory keyFactory = KeyFactory.getInstance("RSA", Providers.provider);
            //PublicKey publicKey = keyFactory.generatePublic(keySpec);
            KeyPair keyPair = new KeyPair(certificate.getPublicKey(), privateKey);
            return keyPair;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        genKey();
        //load();
        //alias();
        //clear();
        //System.out.println(getKeyPair("key1"));
    }
}
