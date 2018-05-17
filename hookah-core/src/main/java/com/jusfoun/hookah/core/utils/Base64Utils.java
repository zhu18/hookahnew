package com.jusfoun.hookah.core.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName: Base64Utils base64编码
 * @Description:
 * @author zhangxiong
 * @date 2015-7-21 下午6:27:37
 * 
 */
public class Base64Utils {

	/*** 缓存大小 ***/
	private static final int CACHE_SIZE = 1024;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(Base64Utils.class);

	/**
	 * @Title: decode 解码
	 * @Description:
	 * @param base64
	 * @return
	 */
	public static byte[] decode(String codes) {
		try {
			return Base64.decode(codes);
		} catch (Exception ex) {
			LOGGER.error("base64 decode:" + ex.getMessage(), ex);
			return null;
		}
	}

	/**
	 * @Title: encode 编码
	 * @Description:
	 * @param bytes
	 * @return
	 */
	public static String encode(byte[] bytes) {
		try {
			return new String(Base64.encode(bytes));
		} catch (Exception ex) {
			LOGGER.error("base64 encode:" + ex.getMessage(), ex);
			return null;
		}
	}

	/**
	 * @Title: encodeFile
	 * @Description: 编码文件
	 * @param filePath
	 * @return
	 */
	public static String encodeFile(String filePath) {
		try {
			byte[] bytes = fileToByte(filePath);
			return encode(bytes);
		} catch (Exception ex) {
			LOGGER.error("encodeFile:" + ex.getMessage(), ex);
			return null;
		}
	}

	/**
	 * @Title: decodeFile
	 * @Description: 解码文件
	 * @param filePath
	 * @param base64
	 */
	public static void decodeFile(String filePath, String base64) {
		byte[] bytes = decode(base64);
		try {
			byteArrayToFile(bytes, filePath);
		} catch (Exception ex) {
			LOGGER.error("decodeToFile:" + ex.getMessage(), ex);
			return;
		}
	}

	/**
	 * @Title: fileToByte
	 * @Description:
	 * @param filePath
	 * @return
	 * @throws Exception
	 */
	public static byte[] fileToByte(String filePath) throws Exception {
		byte[] data = new byte[0];
		File file = new File(filePath);
		if (file.exists()) {
			FileInputStream in = new FileInputStream(file);
			ByteArrayOutputStream out = new ByteArrayOutputStream(2048);
			byte[] cache = new byte[CACHE_SIZE];
			int nRead = 0;
			while ((nRead = in.read(cache)) != -1) {
				out.write(cache, 0, nRead);
				out.flush();
			}
			out.close();
			in.close();
			data = out.toByteArray();
		}
		return data;
	}

	/**
	 * @Title: byteArrayToFile
	 * @Description:
	 * @param bytes
	 * @param filePath
	 * @throws Exception
	 */
	public static void byteArrayToFile(byte[] bytes, String filePath)
			throws Exception {
		InputStream in = new ByteArrayInputStream(bytes);
		File destFile = new File(filePath);
		if (!destFile.getParentFile().exists()) {
			destFile.getParentFile().mkdirs();
		}
		destFile.createNewFile();
		OutputStream out = new FileOutputStream(destFile);
		byte[] cache = new byte[CACHE_SIZE];
		int nRead = 0;
		while ((nRead = in.read(cache)) != -1) {
			out.write(cache, 0, nRead);
			out.flush();
		}
		out.close();
		in.close();
	}

}
