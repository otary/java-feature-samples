import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

/**
 * 数字证书加密解密操作
 * Created by User on 2017/6/19.
 */
public class CertificateCoder {
    public static final String CERT_TYPE = "X.509";


    /**
     * 获取私匙
     *
     * @param keyStorePath
     * @param pwd
     * @param alias
     * @return PrivateKey 私匙
     * @throws Exception
     */
    private static PrivateKey getPrivateKey(String keyStorePath, String pwd, String alias) throws Exception {
        KeyStore ks = getKeyStore(keyStorePath, pwd);
        return (PrivateKey) ks.getKey(alias, pwd.toCharArray());

    }


    /**
     * @param keyStorePath
     * @param pwd
     * @return keyStore 密匙库
     * @throws Exception
     */
    private static KeyStore getKeyStore(String keyStorePath, String pwd) throws Exception {
        KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
        FileInputStream in = new FileInputStream(keyStorePath);
        ks.load(in, pwd.toCharArray());
        in.close();
        return ks;
    }


    /**
     * @param certificatePath
     * @return Certificate 证书
     * @throws Exception
     */
    private static Certificate getCertificate(String certificatePath) throws Exception {
        CertificateFactory factory = CertificateFactory.getInstance(CERT_TYPE);
        FileInputStream in = new FileInputStream(certificatePath);
        Certificate certificate = factory.generateCertificate(in);
        in.close();
        return certificate;

    }


    /**
     * 通过证书返回公匙
     *
     * @param certificatePath
     * @return Publickey 返回公匙
     * @throws Exception
     */
    private static PublicKey getPublicKeyByCertificate(String certificatePath) throws Exception {
        Certificate certificate = getCertificate(certificatePath);
        return certificate.getPublicKey();
    }


    /**
     * @param keyStorePath
     * @param alias
     * @param pwd
     * @return Certificate 证书
     * @throws Exception
     */
    private static Certificate getCertificate(String keyStorePath, String alias, String pwd) throws Exception {
        KeyStore ks = getKeyStore(keyStorePath, pwd);
        //获取证书
        return ks.getCertificate(alias);
    }


    /**
     * 私匙加密
     *
     * @param data
     * @param keyStorePath
     * @param alias
     * @param pwd
     * @return byte[] 被私匙加密的数据
     * @throws Exception
     */
    public static byte[] encryptByPrivateKey(byte[] data, String keyStorePath, String alias, String pwd) throws Exception {


        PrivateKey privateKey = getPrivateKey(keyStorePath, pwd, alias);
        //对数据进行加密

        Cipher cipher = Cipher.getInstance(privateKey.getAlgorithm(), new org.bouncycastle.jce.provider.BouncyCastleProvider());
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        // 获得加密块大小，如：加密前数据为128个byte，而key_size=1024
        int blockSize = cipher.getBlockSize();
        // 加密块大小为127
        // byte,加密后为128个byte;因此共有2个加密块，第一个127
        // byte第二个为1个byte
        int outputSize = cipher.getOutputSize(data.length);// 获得加密块加密后块大小
        int leavedSize = data.length % blockSize;
        int blocksSize = leavedSize != 0 ? data.length / blockSize + 1 : data.length / blockSize;
        byte[] raw = new byte[outputSize * blocksSize];
        int i = 0;
        while (data.length - i * blockSize > 0) {
            if (data.length - i * blockSize > blockSize) {
                cipher.doFinal(data, i * blockSize, blockSize, raw, i * outputSize);
            } else {
                cipher.doFinal(data, i * blockSize, data.length - i * blockSize, raw, i * outputSize);
            }
            i++;
        }
        return raw;

    }


    /**
     * 私匙解密
     *
     * @param data
     * @param keyStorePath
     * @param alias
     * @param pwd
     * @return byte[] 私匙解密的数据
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(byte[] data, String keyStorePath, String alias, String pwd) throws Exception {

        PrivateKey privateKey = getPrivateKey(keyStorePath, pwd, alias);
        Cipher cipher = Cipher.getInstance(privateKey.getAlgorithm(), new org.bouncycastle.jce.provider.BouncyCastleProvider());
        cipher.init(cipher.DECRYPT_MODE, privateKey);
        int blockSize = cipher.getBlockSize();
        ByteArrayOutputStream bout = new ByteArrayOutputStream(64);
        int j = 0;
        while (data.length - j * blockSize > 0) {
            bout.write(cipher.doFinal(data, j * blockSize, blockSize));
            j++;
        }
        return bout.toByteArray();
    }


    /**
     * 公匙加密
     *
     * @param data
     * @param cerPath
     * @return byte[] 被公匙加密的数据
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(byte[] data, String cerPath) throws Exception {
        //获取公匙
        PublicKey publicKey = getPublicKeyByCertificate(cerPath);
        //    System.out.println(publicKey.getAlgorithm());
        Cipher cipher = Cipher.getInstance(publicKey.getAlgorithm(), new org.bouncycastle.jce.provider.BouncyCastleProvider());
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        // 获得加密块大小，如：加密前数据为128个byte，而key_size=1024
        int blockSize = cipher.getBlockSize();
        // 加密块大小为127
        // byte,加密后为128个byte;因此共有2个加密块，第一个127
        // byte第二个为1个byte
        int outputSize = cipher.getOutputSize(data.length);// 获得加密块加密后块大小
        int leavedSize = data.length % blockSize;
        int blocksSize = leavedSize != 0 ? data.length / blockSize + 1 : data.length / blockSize;
        byte[] raw = new byte[outputSize * blocksSize];
        int i = 0;
        while (data.length - i * blockSize > 0) {
            if (data.length - i * blockSize > blockSize) {
                cipher.doFinal(data, i * blockSize, blockSize, raw, i * outputSize);
            } else {
                cipher.doFinal(data, i * blockSize, data.length - i * blockSize, raw, i * outputSize);
            }
            i++;
        }
        return raw;
    }

    /**
     * 公匙解密
     *
     * @param data
     * @param cerPath
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPublicKey(byte[] data, String cerPath) throws Exception {
        PublicKey publicKey = getPublicKeyByCertificate(cerPath);
        Cipher cipher = Cipher.getInstance(publicKey.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        return cipher.doFinal(data);
    }

    /**
     * 签名
     *
     * @param sign
     * @param keyStorePath
     * @param pwd
     * @param alias
     * @return
     * @throws Exception
     */
    public static byte[] sign(byte[] sign, String keyStorePath, String pwd, String alias) throws Exception {
        //获取证书
        X509Certificate x509 = (X509Certificate) getCertificate(keyStorePath, alias, pwd);
        //构建签名,由证书指定签名算法
        Signature sa = Signature.getInstance(x509.getSigAlgName());
        //获取私匙
        PrivateKey privateKey = getPrivateKey(keyStorePath, pwd, alias);
        sa.initSign(privateKey);
        sa.update(sign);
        return sa.sign();
    }

    /**
     * 验证签名
     *
     * @param data
     * @param sign
     * @param cerPath
     * @return
     * @throws Exception
     */
    public static boolean verify(byte[] data, byte[] sign, String cerPath) throws Exception {
        X509Certificate x509 = (X509Certificate) getCertificate(cerPath);
        Signature sa = Signature.getInstance(x509.getSigAlgName());
        sa.initVerify(x509);
        sa.update(data);
        return sa.verify(sign);
    }
}

