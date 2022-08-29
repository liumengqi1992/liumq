package com.deepblue.cloudmsg.utils;

import android.util.Base64;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;


public class DesEncryptDecrypt {

	private static DesEncryptDecrypt ourInstance = new DesEncryptDecrypt();

	public static DesEncryptDecrypt getInstance() {
		return ourInstance;
	}

	private Cipher ecipher, dcipher;

	private DesEncryptDecrypt() {
		DESKeySpec dks;
		try {
			dks = new DESKeySpec("deepblueai.com".getBytes());
			SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
			SecretKey desKey = skf.generateSecret(dks);
			ecipher = Cipher.getInstance("DES");
			dcipher = Cipher.getInstance("DES");
			ecipher.init(Cipher.ENCRYPT_MODE, desKey);
			dcipher.init(Cipher.DECRYPT_MODE, desKey);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		}

	}

	public String encrypt(String str) throws Exception {
		// Encode the string into bytes using utf-8
		byte[] utf8 = str.getBytes("UTF8");
		// Encrypt
		byte[] enc = ecipher.doFinal(utf8);
		// Encode bytes to base64 to get a string
		return Base64.encodeToString(enc, 0);
	}

//	public String decrypt(String str) throws Exception {
//		// Decode base64 to get bytes
//		byte[] dec = new sun.misc.BASE64Decoder().decodeBuffer(str);
//		byte[] utf8 = dcipher.doFinal(dec);
//		// Decode using utf-8
//		return new String(utf8, "UTF8");
//	}

	public static void main(String args[]) throws Exception {

		// 加密
//		String encryptedStr = DesEncryptDecrypt.getInstance().encrypt("853909");

		// 解密
//		String decryptedStr = DesEncryptDecrypt.getInstance().decrypt(encryptedStr);

//		System.out.println(">>>>>>>>>>>>>encryptedStr:" + encryptedStr);
		
		
//		System.out.println(">>>>>>>>>>>>>decryptedStr:" + decryptedStr);

	}

}
