package br.com.gestcenter.ofxConvert;

import static br.com.gestcenter.ofxConvert.utils.OfxUtil.generateBankTransaction;
import static br.com.gestcenter.ofxConvert.utils.OfxUtil.generateCsvFile;

import java.util.List;

import br.com.gestcenter.ofxConvert.objects.BankTransaction;

/**
 * 
 * @author Jeimison Soares
 * 
 */
public class Ofx {

	/**
	 * Generates CSV file
	 * 
	 * @param file
	 */
	public static void convertToCsvFile(String file) {
		generateCsvFile(file);
	}
	
	/**
	 * Generates bank transaction list
	 * 
	 * @param file
	 * @return ArrayList BankTransaction
	 */
	public static List<BankTransaction> convertToList(String file) {
		return generateBankTransaction(file);
	}

}
