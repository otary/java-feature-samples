import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

/**
 * Created by User on 2017/6/19.
 */
public class KeyGen {
    public static KeyPair gen(String keyType, int keySize) {
        KeyPairGenerator generator = null;
        try {
            generator = KeyPairGenerator.getInstance(keyType);
            generator.initialize(keySize);
            return generator.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        System.out.println(gen("RSA", 1024).getPublic());
    }
}

