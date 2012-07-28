import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class KeyGen {

	public static void main(String[] args) throws NoSuchAlgorithmException {
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		kgen.init(128);
		SecretKey skey = kgen.generateKey();
		byte[] raw = skey.getEncoded();

		String key = new String(org.apache.commons.codec.binary.Hex.encodeHex(raw));
		System.out.printf("My Secret Key: %s\n", key);

	}

}
