package br.com.gestcenter.ofxConvert.utils;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author Jeimison Soares
 * 
 */
public class LogUtil {

	private static Logger LOGGER = Logger.getLogger("");

	static {
		System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tD-%1$tT][%2$1s]%5$s %n");
	}

	/**
	 * Log INFO.
	 * 
	 * @param msg
	 */
	@SuppressWarnings("rawtypes")
	public static void log(Class classe, String msg) {
		LOGGER.logp(Level.INFO, classe.getSimpleName(), null, msg);
	}

	/**
	 * Log WARNING.
	 * 
	 * @param msg
	 */
	@SuppressWarnings("rawtypes")
	public static void logAlert(Class classe, String msg) {
		LOGGER.logp(Level.WARNING, classe.getSimpleName(), null, msg);
	}

	/**
	 * Log SEVERE.
	 * 
	 * @param msg
	 */
	@SuppressWarnings("rawtypes")
	public static void logError(Class classe, String msg) {
		LOGGER.logp(Level.SEVERE, classe.getSimpleName(), null, msg);
	}
}
