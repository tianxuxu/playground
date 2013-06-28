import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import ch.rasc.sec.security.GoogleAuthenticatorUtil;


public class M {

	public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException {
//		byte[] buffer = new byte[10];
//		new SecureRandom().nextBytes(buffer);
//		String secret = new String(new Base32().encode(buffer));
//		System.out.println("secret: " + secret);
		String secret = "K76OJXIWCCCCLEAA";		
		System.out.println(GoogleAuthenticatorUtil.verifyCode(secret, 749377,2));
		
	}
	


}
