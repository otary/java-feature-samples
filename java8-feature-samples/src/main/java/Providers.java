import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Provider;
import java.security.Security;

/**
 * Created by User on 2017/6/19.
 */
public class Providers {
    public static Provider provider = new BouncyCastleProvider();

    static {
        Security.addProvider(provider);
    }
}
