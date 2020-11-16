package br.com.gestcenter.ofxToCsv;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import br.com.gestcenter.ofxToCsv.utils.LogUtil;

/**
 * 
 * @author Jeimison Soares
 * 
 */
public class OfxToCsv {

	public static void main(String[] args) {
		convert("C:\\Users\\jeimison\\Downloads\\extrato debora 09.ofx");
	}

	public static void convert(String file) {

		if (!writeCsvFile(file, generateXml(file))) {
			// TODO: Tratar erro;
		}
	}
	
	public static void convertToCsvFile(String file) {

		if (!writeCsvFile(file, generateXml(file))) {
			LogUtil.log(OfxToCsv.class, "Not convert to CSV file.");
		}
	}
	
	public static void convertToList(String file) {

		if (!writeCsvFile(file, generateXml(file))) {
			// TODO: Tratar erro;
		}
	}

	@SuppressWarnings("resource")
	private static StringBuffer generateXml(String file) {
		StringBuffer xml = new StringBuffer();
		
		BufferedReader br = null;
		String str, sufixo;
		String lineSeparator = System.getProperty("line.separator");

		try {
			br = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			LogUtil.log(OfxToCsv.class, "File Not Found.");
			e.printStackTrace();
			return null;
		}

		xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>").append(lineSeparator);

		try {
			while ((str = br.readLine()) != null) {
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
			br.close();
		} catch (IOException e) {
			LogUtil.log(OfxToCsv.class, "Error when generating xml.");
			e.printStackTrace();
			return null;
		}

		return xml;
	}

	private static boolean writeCsvFile(String file, StringBuffer xml) {
		DocumentBuilder dBuilder = null;
		Document doc = null;
		PrintWriter writerCSV = null;

		String baseDirectory = getBaseDirectory(file);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();

		try {
			dBuilder = dbFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			return false;
		}

		try {
			doc = dBuilder.parse(toUTF8InputStream(xml.toString()));
		} catch (SAXException | IOException e) {
			e.printStackTrace();
			return false;
		}

		doc.getDocumentElement().normalize();
		NodeList nList = doc.getElementsByTagName("STMTTRN");

		try {
			writerCSV = new PrintWriter(baseDirectory + ".csv", "ISO-8859-1");
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
			return false;
		}

		writerCSV.println("tipo;data;valor;ident;memo;checknum");

		for (int i = 0; i < nList.getLength(); i++) {
			Node nNode = nList.item(i);

			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;

				String tipo = eElement.getElementsByTagName("TRNTYPE").item(0).getTextContent();
				String data = eElement.getElementsByTagName("DTPOSTED").item(0).getTextContent().substring(0, 8);
				data = data.substring(6, 8) + "/" + data.substring(4, 6) + "/" + data.substring(0, 4);
				String valor = eElement.getElementsByTagName("TRNAMT").item(0).getTextContent().replace(".", ",");
				String ident = eElement.getElementsByTagName("FITID").item(0).getTextContent();
				String memo = eElement.getElementsByTagName("MEMO").item(0).getTextContent();
				String checknum = eElement.getElementsByTagName("CHECKNUM").item(0).getTextContent();

				writerCSV.println(tipo + ";" + data + ";" + valor + ";" + ident + ";" + memo + ";" + checknum);
			}
		}
		writerCSV.close();
//		inputXML.delete();

		return true;
	}

	private static String getBaseDirectory(String file) {
		return file.substring(0, file.length() - 4);
	}

	private static InputStream toUTF8InputStream(String str) {
		InputStream is = null;
		try {
			is = new ByteArrayInputStream(str.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			throw new AssertionError();
		}
		return is;
	}
}
