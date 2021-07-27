import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;

/**
 * Created by User on 2017/6/19.
 */
public class Extension {
    private ASN1ObjectIdentifier oid;
    private boolean isCritical;
    private ASN1Encodable value;

    public Extension(ASN1ObjectIdentifier oid, boolean isCritical, ASN1Encodable value) {
        this.oid = oid;
        this.isCritical = isCritical;
        this.value = value;
    }

    public Extension() {
    }

    public ASN1ObjectIdentifier getOid() {
        return oid;
    }

    public void setOid(ASN1ObjectIdentifier oid) {
        this.oid = oid;
    }

    public boolean isCritical() {
        return isCritical;
    }

    public void setCritical(boolean critical) {
        isCritical = critical;
    }

    public ASN1Encodable getValue() {
        return value;
    }

    public void setValue(ASN1Encodable value) {
        this.value = value;
    }
}
