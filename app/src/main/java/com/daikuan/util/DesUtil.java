package com.daikuan.util;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import android.util.Base64;

public class DesUtil {

	private DesUtil() {
	};

	private final static String DES = "DES";

	/**
	 * 
	 * 加密
	 * 
	 * @param src
	 *            数据源
	 *            密钥，长度必须是8的倍数
	 * @return 返回加密后的数据
	 * @throws Exception
	 */
	static byte[] encrypt(byte[] src) throws Exception {
		SecureRandom sr = new SecureRandom();
		DESKeySpec dks = new DESKeySpec(getEncryptKey());
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
		SecretKey securekey = keyFactory.generateSecret(dks);
		Cipher cipher = Cipher.getInstance(DES);
		cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
		return cipher.doFinal(src);
	}

	/**
	 * 解密
	 * 
	 * @param src
	 *            数据源
	 *            密钥，长度必须是8的倍数
	 * @return 返回解密后的原始数据
	 * @throws Exception
	 */
	static byte[] decrypt(byte[] src) throws Exception {
		SecureRandom sr = new SecureRandom();
		DESKeySpec dks = new DESKeySpec(getEncryptKey());
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
		SecretKey securekey = keyFactory.generateSecret(dks);
		Cipher cipher = Cipher.getInstance(DES);
		cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
		return cipher.doFinal(src);
	}

	public static String encode(String src) {
		try {
			String src_ = Base64.encodeToString(encrypt(src.getBytes("utf-8")),
					Base64.NO_WRAP);
			return src_;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String decode(String src) {
		try {
			return new String(decrypt(Base64.decode(src, Base64.NO_WRAP)), "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static byte[] CRYPT_KEY = null;

	private static byte[] getEncryptKey() {
		if (CRYPT_KEY != null)
			return CRYPT_KEY;
		try {
			String k1 = "fEJ";
			String k2 = "yi@";
			String k3 = "%x";
			StringBuilder sb = new StringBuilder(k1);
			sb.append(k2);
			sb.append(k3);
			sb = sb.reverse();

			CRYPT_KEY = sb.toString().getBytes();
			return CRYPT_KEY;
		} catch (Exception e) {
			return null;
		}
	}
}
