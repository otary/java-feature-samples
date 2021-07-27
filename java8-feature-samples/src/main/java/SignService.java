import java.security.PrivateKey;
import java.security.Signature;

/**
 * Created by User on 2017/6/19.
 */
public class SignService {
    public static byte[] sign(byte[] plainText, String signAlg, PrivateKey privateKey) {
        try {
            Signature sign = Signature.getInstance(signAlg, Providers.provider);
            sign.initSign(privateKey);
            sign.update(plainText);
            return sign.sign();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }
}
