package br.com.gestcenter.ofxConvert;

import static br.com.gestcenter.ofxConvert.utils.OfxUtil.generateBankTransaction;
import static br.com.gestcenter.ofxConvert.utils.OfxUtil.generateCsvFile;

import java.util.List;

import br.com.gestcenter.ofxConvert.objects.BankTransaction;
import br.com.gestcenter.ofxConvert.utils.LogUtil;

/**
 * 
 * @author Jeimison Soares
 * 
 */
public class Ofx {
	
	public static void main(String[] args) {
		List<BankTransaction> list = generateBankTransaction("C:\\Users\\jeimison\\Downloads\\extrato debora 09.ofx");
		
		for (BankTransaction bankTransaction : list) {
			LogUtil.log(Ofx.class, bankTransaction.toString());
		}
	}

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
