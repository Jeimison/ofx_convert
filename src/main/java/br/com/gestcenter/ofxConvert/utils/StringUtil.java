package br.com.gestcenter.ofxConvert.utils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

/**
 * 
 * @author Jeimison Soares
 * 
 */
public class StringUtil {
	
	/**
	 * @param str
	 * @return InputStream
	 */
	public static InputStream toUTF8InputStream(String str) {
		InputStream is = null;
		try {
			is = new ByteArrayInputStream(str.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			throw new AssertionError();
		}
		return is;
	}
	
	/**
	 * @param str
	 * @return string
	 */
	public static String generateString(String str) {
		return str != null ? str.replaceAll(";", ",") : "";
	}
	
}
