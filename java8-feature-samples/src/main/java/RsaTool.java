import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.*;

/**
 * <p>Title: </p>
 * <p>Description: 实现RSA加解密以及密钥读取，默认使用PKCS1PADDING</p>
 *
 * @author zhaoyuntao
 *         <p>
 *         Modified History:
 * @version 1.00 Jun 10, 2009
 */
public class RsaTool extends AbstractSecureTool {
    private final static String DEFAULT_ALGORITHM = "RSA/NONE/PKCS1PADDING";
    private static Provider provider = new BouncyCastleProvider();

    static {
        //把BouncyCastleProvider加入到JAVA JCE中，才能使用BouncyCastle提供的算法
        Security.addProvider(provider);
    }

    public RsaTool(String rsaAlgorithm) {
        super(rsaAlgorithm);
    }

    public RsaTool() {
        this(DEFAULT_ALGORITHM);
    }

    /**
     * @param keySize
     * @return 生成一个密钥对
     */
    public KeyPair generateKeyPair(int keySize) {
        try {
            KeyPairGenerator keypairGen = KeyPairGenerator.getInstance("RSA", provider);
            keypairGen.initialize(keySize);
            KeyPair pair = keypairGen.generateKeyPair();
            return pair;
        } catch (NoSuchAlgorithmException e) {
            // 不会发生
            throw new RuntimeException(e);
        }

    }


    /**
     * 先对数据用MD5进行摘要，再用RSA进行加密
     *
     * @param key
     * @param data
     * @return
     */
    public byte[] signWithMD5(PrivateKey key, byte[] data) {
        try {
            Signature md5WithRsa = Signature.getInstance("md5WithRsa", provider);
            md5WithRsa.initSign(key);
            md5WithRsa.update(data);
            return md5WithRsa.sign();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (SignatureException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 先用RSA进行解密，再和原数据的MD5值进行比较
     *
     * @param key
     * @param srcData
     * @param signedData
     * @return
     */
    public boolean verifySignWithMD5(PublicKey key, byte[] srcData, byte[] signedData) {
        try {
            Signature md5WithRsa = Signature.getInstance("md5WithRsa", provider);
            md5WithRsa.initVerify(key);
            md5WithRsa.update(srcData);
            return md5WithRsa.verify(signedData);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (SignatureException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }


}

