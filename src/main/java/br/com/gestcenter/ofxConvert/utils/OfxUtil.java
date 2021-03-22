package br.com.gestcenter.ofxConvert.utils;

import static br.com.gestcenter.ofxConvert.utils.CsvUtil.writeLine;
import static br.com.gestcenter.ofxConvert.utils.StringUtil.toUTF8InputStream;
import static br.com.gestcenter.ofxConvert.utils.XmlUtil.generateXml;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import br.com.gestcenter.ofxConvert.objects.BankTransaction;
	
/**
 * 
 * @author Jeimison Soares
 * 
 */
public class OfxUtil {
	
	
	/**
	 * @param file
	 */
	public static void generateCsvFile(String file) {
		List<BankTransaction> list = generateBankTransaction(file);
		
		if (list!=null) {
			try {
				OutputStreamWriter writer = new OutputStreamWriter(
				        new FileOutputStream(getBaseDirectory(file)+".csv"),StandardCharsets.UTF_8);
				
				writeLine(writer, getColumnsFile());
				
				for (BankTransaction bankingMovement : list) {
					writeLine(writer, bankingMovement.getListData());
				}
				
				writer.flush();
				writer.close();				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}	
		}
	}
	
	/**
	 * @param file
	 * @return List BankTransaction
	 */
	public static List<BankTransaction> generateBankTransaction(final InputStreamReader file) {
		StringBuffer xml = generateXml(file);
		return generateBankTransaction(xml);	
	}
	
	/**
	 * @param file
	 * @return List BankTransaction
	 */
	public static List<BankTransaction> generateBankTransaction(String file) {
		StringBuffer xml = generateXml(file);
		return generateBankTransaction(xml);	
	}
	
	/**
	 * @param file
	 * @return List BankTransaction
	 */
	public static List<BankTransaction> generateBankTransaction(StringBuffer xml) {
		
		if (xml==null) {
			return null;
		}
		
		List<BankTransaction> movement = new ArrayList<>();
		
		DocumentBuilder dBuilder = null;
		Document doc = null;

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();

		try {
			dBuilder = dbFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			return null;
		}

		try {
			doc = dBuilder.parse(toUTF8InputStream(xml.toString()));
		} catch (SAXException | IOException e) {
			e.printStackTrace();
			return null;
		}

		doc.getDocumentElement().normalize();
		
		NodeList nList = doc.getElementsByTagName("STMTTRN");

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
				
				movement.add(new BankTransaction(tipo, data, valor, ident, memo, checknum));
				
			}
		}
		
		return movement;	
	}

	/**
	 * @return List String
	 */
	public static List<String> getColumnsFile() {
		return Arrays.asList("tipo","data","valor","ident","memo","checknum");
	}
	
	/**
	 * @param file
	 * @return string
	 */
	public static String getBaseDirectory(String file) {
		return file.substring(0, file.length() - 4);
	}
		
	/**
	 * @param file
	 * @param xml
	 * @return boolean
	 */
	@Deprecated
	public static boolean writeCsvFile(String file, StringBuffer xml) {
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

		return true;
	}
	
}
