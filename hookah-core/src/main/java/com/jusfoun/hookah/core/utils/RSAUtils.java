package com.jusfoun.hookah.core.utils;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.Cipher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName: RSAUtils
 * @Description: RSA加密，解密，签名，验签，密钥对生成
 * @author zhangxiong
 * @date 2015-7-22 上午9:37:26
 * 
 */
public class RSAUtils {
	/*** 加密算法RSA ***/
	private static final String KEY_ALGORITHM = "RSA";

	/*** 获取公钥的key ***/
	private static final String PUBLIC_KEY = "RSAPublicKey";

	/*** 获取私钥的key ***/
	private static final String PRIVATE_KEY = "RSAPrivateKey";

	/*** 签名算法 ***/
	private static final String SIGNATURE_ALGORITHM = "MD5withRSA";

	/*** RSA最大加密明文大小 ***/
	private static final int MAX_ENCRYPT_BLOCK = 117;

	/*** RSA最大解密密文大小 ***/
	private static final int MAX_DECRYPT_BLOCK = 128;

	/*** 密钥长度 ***/
	private static final int KEY_LENGTH = 512;

	// 编码
	public final static String ENCODE = "utf-8";

	private static final Logger LOGGER = LoggerFactory
			.getLogger(RSAUtils.class);

	/**
	 * @Title: genKeyPair
	 * @Description: 生成密钥对
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> genKeyPair() {
		Map<String, Object> keyMap = new HashMap<String, Object>();
		try {
			KeyPairGenerator keyPairGenerator = null;
			keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
			SecureRandom secureRandom = new SecureRandom();
			String currentDateTime = new SimpleDateFormat("yyyyMMddHHmmssSSS")
					.format(new Date());
			secureRandom.setSeed(currentDateTime.getBytes());
			keyPairGenerator.initialize(KEY_LENGTH, secureRandom);
			KeyPair keyPair = keyPairGenerator.genKeyPair();
			PublicKey publicKey = keyPair.getPublic();
			PrivateKey privateKey = keyPair.getPrivate();
			keyMap.put(PUBLIC_KEY, publicKey.getEncoded());
			keyMap.put(PRIVATE_KEY, privateKey.getEncoded());
			return keyMap;
		} catch (Exception ex) {
			ex.printStackTrace();
			LOGGER.error("生成密钥对：" + ex.getMessage(), ex);
			return null;
		}
	}

	/**
	 * @Title: sign
	 * @Description: 用私钥对数字进行签名
	 * @param data
	 *            加密串
	 * @param privateKey
	 *            私钥
	 * @return
	 * @throws Exception
	 */
	public static String sign(byte[] data, String privateKey) {
		try {
			byte[] keyBytes = Base64Utils.decode(privateKey);
			PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
			PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
			Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
			signature.initSign(privateK);
			signature.update(data);
			return Base64Utils.encode(signature.sign());
		} catch (Exception ex) {
			ex.printStackTrace();
			LOGGER.error("数字签名：" + ex.getMessage());
			return null;
		}
	}

	/**
	 * @Title: verify
	 * @Description: 对数字签名进行验签
	 * @param data
	 *            加密串
	 * @param publicKey
	 *            公钥
	 * @param sign
	 *            签名串
	 * @return
	 * @throws Exception
	 */
	public static boolean verify(byte[] data, String publicKey, String sign) {
		try {
			byte[] keyBytes = Base64Utils.decode(publicKey);
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
			PublicKey publicK = keyFactory.generatePublic(keySpec);
			Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
			signature.initVerify(publicK);
			signature.update(data);
			return signature.verify(Base64Utils.decode(sign));
		} catch (Exception ex) {
			ex.printStackTrace();
			LOGGER.error("数字签名验签：" + ex.getMessage(), ex);
			return false;
		}
	}

	/**
	 * @Title: decryptByPrivateKey
	 * @Description: 私钥解密
	 * @param encrypted
	 *            加密串
	 * @param privateKey
	 *            私钥
	 * @return
	 */
	public static byte[] decryptByPrivateKey(byte[] encrypted, byte[] privateKey) {
		try {
			byte[] encryptedData = Base64Utils.decode(new String(encrypted,ENCODE));
			byte[] privateKeyByte = Base64Utils.decode(new String(privateKey,ENCODE));
			PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(
					privateKeyByte);
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
			Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
			Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
			cipher.init(Cipher.DECRYPT_MODE, privateK);
			int inputLen = encryptedData.length;
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int offSet = 0;
			byte[] cache;
			int i = 0;
			// 对数据分段解密
			while (inputLen - offSet > 0) {
				if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
					cache = cipher.doFinal(encryptedData, offSet,
							MAX_DECRYPT_BLOCK);
				} else {
					cache = cipher.doFinal(encryptedData, offSet, inputLen
							- offSet);
				}
				out.write(cache, 0, cache.length);
				i++;
				offSet = i * MAX_DECRYPT_BLOCK;
			}
			byte[] decryptedData = out.toByteArray();
			out.close();
			return decryptedData;
		} catch (Exception ex) {
			ex.printStackTrace();
			LOGGER.error("私钥解密：" + ex.getMessage(), ex);
			return null;
		}
	}

	/**
	 * @Title: decryptByPublicKey
	 * @Description: 公钥解密
	 * @param encrypted
	 *            加密串
	 * @param publicKey
	 *            公钥
	 * @return
	 */
	public static byte[] decryptByPublicKey(byte[] encrypted, byte[] publicKey) {
		try {
			byte[] encryptedData = Base64Utils.decode(new String(encrypted,ENCODE));
			byte[] publicKeyByte = Base64Utils.decode(new String(publicKey,ENCODE));
			X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(
					publicKeyByte);
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
			Key publicK = keyFactory.generatePublic(x509KeySpec);
			Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
			cipher.init(Cipher.DECRYPT_MODE, publicK);
			int inputLen = encryptedData.length;
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int offSet = 0;
			byte[] cache;
			int i = 0;
			// 对数据分段解密
			while (inputLen - offSet > 0) {
				if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
					cache = cipher.doFinal(encryptedData, offSet,
							MAX_DECRYPT_BLOCK);
				} else {
					cache = cipher.doFinal(encryptedData, offSet, inputLen
							- offSet);
				}
				out.write(cache, 0, cache.length);
				i++;
				offSet = i * MAX_DECRYPT_BLOCK;
			}
			byte[] decryptedData = out.toByteArray();
			out.close();
			return decryptedData;
		} catch (Exception ex) {
			ex.printStackTrace();
			LOGGER.error("公钥解密：" + ex.getMessage(), ex);
			return null;
		}
	}

	/**
	 * @Title: encryptByPublicKey
	 * @Description: 公钥加密
	 * @param datastr
	 *            待加密串
	 * @param publicKey
	 *            公钥
	 * @return
	 */
	public static byte[] encryptByPublicKey(byte[] plaintext, byte[] publicKey) {
		try {
			byte[] publickeyBytes = Base64Utils.decode(new String(publicKey,ENCODE));
			X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(
					publickeyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
			Key publicK = keyFactory.generatePublic(x509KeySpec);
			// 对数据加密
			Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
			cipher.init(Cipher.ENCRYPT_MODE, publicK);
			int inputLen = plaintext.length;
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int offSet = 0;
			byte[] cache;
			int i = 0;
			// 对数据分段加密
			while (inputLen - offSet > 0) {
				if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
					cache = cipher
							.doFinal(plaintext, offSet, MAX_ENCRYPT_BLOCK);
				} else {
					cache = cipher
							.doFinal(plaintext, offSet, inputLen - offSet);
				}
				out.write(cache, 0, cache.length);
				i++;
				offSet = i * MAX_ENCRYPT_BLOCK;
			}
			byte[] encryptedData = out.toByteArray();
			out.close();
			return Base64Utils.encode(encryptedData).getBytes(ENCODE);
		} catch (Exception ex) {
			ex.printStackTrace();
			LOGGER.error("公钥加密：" + ex.getMessage(), ex);
			return null;
		}
	}

	/**
	 * @Title: encryptByPrivateKey
	 * @Description: 私钥加密
	 * @param datastr
	 *            待加密串
	 * @param privateKey
	 *            私钥
	 * @return
	 */
	public static byte[] encryptByPrivateKey(byte[] plaintext, byte[] privateKey) {
		try {
			byte[] privateKeyByte = Base64Utils.decode(new String(privateKey,ENCODE));
			PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(
					privateKeyByte);
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
			Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
			Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
			cipher.init(Cipher.ENCRYPT_MODE, privateK);
			int inputLen = plaintext.length;
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int offSet = 0;
			byte[] cache;
			int i = 0;
			// 对数据分段加密
			while (inputLen - offSet > 0) {
				if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
					cache = cipher
							.doFinal(plaintext, offSet, MAX_ENCRYPT_BLOCK);
				} else {
					cache = cipher
							.doFinal(plaintext, offSet, inputLen - offSet);
				}
				out.write(cache, 0, cache.length);
				i++;
				offSet = i * MAX_ENCRYPT_BLOCK;
			}
			byte[] encryptedData = out.toByteArray();
			out.close();
			return Base64Utils.encode(encryptedData).getBytes(ENCODE);
		} catch (Exception ex) {
			ex.printStackTrace();
			LOGGER.error("私钥加密：" + ex.getMessage(), ex);
			return null;
		}
	}

	/**
	 * @Title: getPrivateKey
	 * @Description: 从密钥对中获取私钥
	 * @param keyMap
	 * @return
	 */
	public static byte[] getPrivateKey(Map<String, Object> keyMap) {
		byte[] privatekey = (byte[]) keyMap.get(PRIVATE_KEY);
		return Base64Utils.encode(privatekey).getBytes();
	}

	/**
	 * @Title: getPublicKey
	 * @Description: 从密钥中获取公钥
	 * @param keyMap
	 * @return
	 * @throws Exception
	 */
	public static byte[] getPublicKey(Map<String, Object> keyMap) {
		byte[] publickey = (byte[]) keyMap.get(PUBLIC_KEY);
		return Base64Utils.encode(publickey).getBytes();
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		// Map<String,Object> params = genKeyPair();
//		byte[] privatekey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAIk9mZWXX9ad5Ixcn8hmCbkHndqxztnoGrQw+ebE87gAjNzZ/MLNUngBOLXR5wcYsQAW0ut4rm9ipd1jAfKLRqChQwPS0KIoZ6O7vJCl3KEfpdy8VIPP93mE64TK/BQ2+JW5DjcaIS7VNbsRks4iTsMt9N1F5LrfMaqJhKRrVD+NAgMBAAECgYB+gziXe8mvUDIlaiUpWVbRKMJNneevXJVxUkysfRamoms4FAM7mIbTdVzPz0WHdy2DhYa95qv2pht8sA5Vhip+Au4yKiOyD7bO7KkJLty69uNJrhNwfb5ZiL5n6x0yqqW3J+oEGIq9j9CIbqyLGSdOPxhk5q8md0mTxqDTzIWqiQJBAMS9bpo++CywP60C0l16oI5GCJChJUp367LB0b/FgSmtVNGVsl44zQJgJ/lyKpnSfi2mzD3p+abXmpDMMatwM0sCQQCylDBcO1aX2nuG0qsjcyOc7tbvqyNCoMl2XQ3hHSRuJsdRJbPACjjHCXCN2PwfQohqvxZU9NfJbJnSnW/jSLmHAkA/NycMxOL1aQRs0RVLGBXy4SnrXvwee33uiDyfRBUGkT1JwrH3eITXGJJ/omFx36LPhGLoGgAvzeNKV3I2Re0VAkANgoR+FcchSdgahQmJbwZ8stzz2MCcI8nZ+IQ6SZgd9TUTAIUuvBc7h41KTeYU/1WOrdIkrzk79clVU6/JRBE1AkAS7cx8sI4L7EKKYy0PJ2Tc5l1CWIM4XqhFZTM4/4pjCWcDlCvPDEPqk4Sg4CZtNU+egpUGXRKrZKxl+0Czj1xu"
//				.getBytes();
//		byte[] publickey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCJPZmVl1/WneSMXJ/IZgm5B53asc7Z6Bq0MPnmxPO4AIzc2fzCzVJ4ATi10ecHGLEAFtLreK5vYqXdYwHyi0agoUMD0tCiKGeju7yQpdyhH6XcvFSDz/d5hOuEyvwUNviVuQ43GiEu1TW7EZLOIk7DLfTdReS63zGqiYSka1Q/jQIDAQAB".getBytes();
//
//		String data = "{\"enterprisename\":\"贵阳\"}";
//		byte[] encrypteddata = encryptByPrivateKey(data.getBytes(), privatekey);
//		System.out.println("encrypteddata:" + new String(encrypteddata));
//		String md5 = Md5Digest.md5WithKey(data,
//				"d3d9446802a44259755d38e6d163e820");
//		System.out.println("md5:" + md5);
//		
//		/*byte[] decryptdata = decryptByPublicKey("H4ohMVHOxTtB9ofHEJxTAei1vWsCjWjrqizopMgK6eiJga2dYJM9ULxeQxndszwMLID/a+bXGJXHrF3X1lmmAKh+xhxpc7SN3Xeny9bKI6uA+UqiuQJ3NhJaCRjZbBW1WqRwFmR0VJ3gCm9naOZP985rjDcLN0vorE2igVtB/TE=".getBytes(), publickey);
//		System.out.println(new String(decryptdata));*/
//		byte[] str=decryptByPrivateKey("UzHkWNH6akPSznTqxt6T4CqOK3toUzL9xxOxokXpkyKCotuacfaoDJEfuB8iMh+ivWS/Dp68odETmQ+njCuQvzsyBddwLmpvZSVJMC6+cBjqQgEhPA6YX7Tn37usfd1mKv2hbNGvlWgYy1eqqm+VIj4VxNCw9PGThsNderC8pLpe4cfGElL/b1vnN2zngvohAHL1UbWkpqigYF0uyTnM+NrNzGF+Nftn34vRmT5OtrldqAwlHQCg82v5FcXbKMTSmO8Idi62mjyRKbraTab+2uvxU3vHfVqJN8EnYcdAj+2qfmBn6PsTGTEIyn+tfvIaEw48bJgYkO6aGoRNe8G81AaaCeZw62v65aCpZ7fNUzx+vzAl7M7iJLvvms+DRbqFwwVUq20t74aPl52xEAgOYlN1tDd4o/fiM50TWXdvCPbxb+KkQhWSjRSZQdNc9frB2kXUNQL4FCCXBV4qkKkixYIFsjRWJ1zdFRDzPGEf7Ve04d2WzAq0I3DTeDsY7mUKYiNCoVb7Ub3NvoSjGdpk7Z4lO4gNEflr6bCu/fBOdykHr4swY+Nrcc+3SFUuwq2UnAmAllVr/Fcj2uhWBpYA7POLfx9jQD7cF5EViCb+naHY43ddqKFXIFC9VVDRq660N7gD5boot1/ycBso+MIUZ5afBmLYcpSmXxnQ6peAeSg4VEpLHNchiNHkyKjVpVsP+wNEjC5d6tZLlR/j9YlPXWbG/dsV5qNz+RbNCY854V8qCbcFE/CCWqR44NUnQqVob2Q14GPNI3TvJSvtSXY+DlP61y/c1zaGqniu/U7DtnZTuFxpRk9y6EF8irwF1BhWVhv5WKCaco5tnappugQO/ynuZYopDgePlfrPzi4O1dS867Ia3HQzCoh8ZNy6+L1WszWb9k+R+7hBZK2p7rseuxhzxrUXr6V96ek60GL33HijJ93MEx7K9uTM7O7ccH5QS5UmoIm8rjKcOBeewJe3lEGJQPAx4a+5q6A22kKsERU5K6dyJVy2Y7sGeXsxdCglMSkoebFLpSTcSSi3+6Mpiv36Z6l1qpXTI1zewJY5jQL7pqJEEJWKp0vy+jOpvWqezR8CD/TZMOanwzh+EjkDzY76F+FVHtFXYNgOF9EfNKKHxWPePiyAOdok5O3g9BusAlx3b4ggk5EPMlNuac+2opuAjLUIW+u5ME8TqFg0orY=".getBytes(),
//				privatekey);
//		System.out.println("解密后的数据：" + new String(str,"gbk"));

		// byte[] decrypteddata =
		// decryptByPublicKey("eFXR0eXHOwEPDwmB1qxQaW+wpsYrHLA+foLiolsu8PjuPQO5ic+1XRzOCR9r9Foui9+GyruUzJ6IHN8j9lxsAj1rnN9bQVWaY1WvdEdanapQfRfJktNAEVZvvwg+I52YiQHZmwOKPi81SCAwZ1f3+X4jPbCTfIceZiI2xBRY6WE=".getBytes(),
		// publickey);
		// System.out.println("decrypteddata:" + new String(decrypteddata));

		/*
		 * byte[] encrypteddata = encryptByPrivateKey("zx".getBytes(),
		 * privatekey); System.out.println("encrypteddata:" + new
		 * String(encrypteddata)); byte[] decrypteddata =
		 * decryptByPublicKey(encrypteddata, publickey);
		 * System.out.println("decrypteddata:" + new String(decrypteddata));
		 * 
		 * byte[] encrypteddata1 = encryptByPublicKey("zx".getBytes(),
		 * publickey); System.out.println("encrypteddata1:" + new
		 * String(encrypteddata1)); byte[] decrypteddata1 =
		 * decryptByPrivateKey(encrypteddata1, privatekey);
		 * System.out.println("decrypteddata1:" + new String(decrypteddata1));
		 */
		System.out.println("11111111111");
		 Map<String,Object> params = genKeyPair();
		 System.out.println(new String(getPrivateKey(params)));
		 
//		 byte[] publicKey = (byte[]) params.get(PUBLIC_KEY);

//		 byte[] privateKey = (byte[])params.get(PRIVATE_KEY);
		 
//		 byte[] privateKey	= "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAIk9mZWXX9ad5Ixcn8hmCbkHndqxztnoGrQw+ebE87gAjNzZ/MLNUngBOLXR5wcYsQAW0ut4rm9ipd1jAfKLRqChQwPS0KIoZ6O7vJCl3KEfpdy8VIPP93mE64TK/BQ2+JW5DjcaIS7VNbsRks4iTsMt9N1F5LrfMaqJhKRrVD+NAgMBAAECgYB+gziXe8mvUDIlaiUpWVbRKMJNneevXJVxUkysfRamoms4FAM7mIbTdVzPz0WHdy2DhYa95qv2pht8sA5Vhip+Au4yKiOyD7bO7KkJLty69uNJrhNwfb5ZiL5n6x0yqqW3J+oEGIq9j9CIbqyLGSdOPxhk5q8md0mTxqDTzIWqiQJBAMS9bpo++CywP60C0l16oI5GCJChJUp367LB0b/FgSmtVNGVsl44zQJgJ/lyKpnSfi2mzD3p+abXmpDMMatwM0sCQQCylDBcO1aX2nuG0qsjcyOc7tbvqyNCoMl2XQ3hHSRuJsdRJbPACjjHCXCN2PwfQohqvxZU9NfJbJnSnW/jSLmHAkA/NycMxOL1aQRs0RVLGBXy4SnrXvwee33uiDyfRBUGkT1JwrH3eITXGJJ/omFx36LPhGLoGgAvzeNKV3I2Re0VAkANgoR+FcchSdgahQmJbwZ8stzz2MCcI8nZ+IQ6SZgd9TUTAIUuvBc7h41KTeYU/1WOrdIkrzk79clVU6/JRBE1AkAS7cx8sI4L7EKKYy0PJ2Tc5l1CWIM4XqhFZTM4/4pjCWcDlCvPDEPqk4Sg4CZtNU+egpUGXRKrZKxl+0Czj1xu"
//					.getBytes();
//		byte[] publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCJPZmVl1/WneSMXJ/IZgm5B53asc7Z6Bq0MPnmxPO4AIzc2fzCzVJ4ATi10ecHGLEAFtLreK5vYqXdYwHyi0agoUMD0tCiKGeju7yQpdyhH6XcvFSDz/d5hOuEyvwUNviVuQ43GiEu1TW7EZLOIk7DLfTdReS63zGqiYSka1Q/jQIDAQAB".getBytes();
		 
//		 byte[] privateKey = getPrivateKey(params);
//
//		 byte[] publicKey = getPublicKey(params);


		byte[] privateKey = "MIIBVQIBADANBgkqhkiG9w0BAQEFAASCAT8wggE7AgEAAkEAtdUAptxGWxxkcMmg1/86qbyc9Kbo6kkfbabD6HPqCcHOaVq/FgU0hM2U45ICzcSj51IYRJaQ7KZDCfPmMsDrUQIDAQABAkBZ55uO5VMUMZGoAsm4gsXqrwucG/sF/GnBubcb9FQ/xfDzXQY0hY+rieRn0/UWxaw5mYe/TvXFkA+eTPzZiY7BAiEA83Mc8KZ6CygbcMzGqaz00+TJmCSrUUOTBy6eTXtmdM0CIQC/NLN57B3iOC8vzKfKLNR3P5KSEUQqW3LRT58TXxqwlQIhAMyTTx5qxuPZMDX53nd2/8WP3FgdeFkxj7vZUO2LZO2BAiAbSaEnkEv/gfcyAiocD2PPFPKNCAvLY7uyJYPVVuhPNQIhALWSDOzzHraLzwqqd0weR388rpJb2rbPJZn44vQp9aRr".getBytes();

		byte[] publicKey = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBALXVAKbcRlscZHDJoNf/Oqm8nPSm6OpJH22mw+hz6gnBzmlavxYFNITNlOOSAs3Eo+dSGESWkOymQwnz5jLA61ECAwEAAQ==".getBytes();
		 
		 System.out.println("公钥长度：" + publicKey.length);
		 System.out.println("私钥长度：" + privateKey.length);
		 System.out.println("公钥：" + new String(publicKey));
		 System.out.println("私钥：" + new String(privateKey));
		 
		 String data = "11111111111";
		 System.out.println("源数据：" + data);
		
		byte[] encryptData = encryptByPublicKey(data.getBytes(),publicKey);
		System.out.println("加密后的长度：" + encryptData.length);
		String dataRSAKey = new String(encryptData);
//		String dataRSAKey = "fyqmVaQH3Kyux3umaJFRpzA6uJqNadl693m6yTSbQFN22bOSwNcNOuiicB6yxHbsS/0PpA9XqSQYMG0BJunAJA==";
		System.out.println("加密后的数据：" + dataRSAKey);
		 
		
		 byte[] descrptData = decryptByPrivateKey(dataRSAKey.getBytes(), privateKey);
		 System.out.println("解密后的长度：" + descrptData.length);
		 String dataRSADecry = new String(descrptData);
		 System.out.println("解密后的数据：" + dataRSADecry);
		
		 System.out.println("cccc:" + (int)'\uea60');
		 
		 int a = 'a';
	}

}
