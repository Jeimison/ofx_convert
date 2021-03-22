package br.com.gestcenter.ofxConvert.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import br.com.gestcenter.ofxConvert.Ofx;

/**
 * 
 * @author Jeimison Soares
 * 
 */
public class XmlUtil {

	public static StringBuffer generateXml(final InputStreamReader file) {
		return generateXml(new BufferedReader(file));
	}
	
	public static StringBuffer generateXml(String file) {
		BufferedReader br = null;

		try {
			br = new BufferedReader(new FileReader(file));	
		} catch (FileNotFoundException e) {
			LogUtil.log(Ofx.class, "File Not Found.");
			e.printStackTrace();
			return null;
		}

		return generateXml(br);
	}
	
	public static StringBuffer generateXml(BufferedReader file) {
		StringBuffer xml = new StringBuffer();

		String str, sufixo;
		String lineSeparator = System.getProperty("line.separator");

		xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>").append(lineSeparator);

		try {
			while ((str = file.readLine()) != null) {
				if (str.trim().length() > 0) {
					if (str.trim().substring(0, 1).equals("<")) {

						if (!str.trim().endsWith(">")) {
							sufixo = "</" + str.trim().substring(1, str.trim().indexOf(">") + 1);
							str = str + sufixo;
						}
						xml.append(str).append(lineSeparator);
					}
				}
			}
			file.close();
		} catch (IOException e) {
			LogUtil.log(Ofx.class, "Error when generating xml.");
			e.printStackTrace();
			return null;
		}

		return xml;
	}
}
