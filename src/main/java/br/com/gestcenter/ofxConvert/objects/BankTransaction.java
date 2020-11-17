package br.com.gestcenter.ofxConvert.objects;

import java.util.ArrayList;
import java.util.List;

import static br.com.gestcenter.ofxConvert.utils.StringUtil.generateString;

/**
 * 
 * @author Jeimison Soares
 * 
 */
public class BankTransaction {
	String tipo;
	String data;
	String valor;
	String ident;
	String memo;
	String checknum;
	
	public BankTransaction() {
		super();
	}

	public BankTransaction(String tipo, String data, String valor, String ident, String memo, String checknum) {
		super();
		this.tipo = tipo;
		this.data = data;
		this.valor = valor;
		this.ident = ident;
		this.memo = memo;
		this.checknum = checknum;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getIdent() {
		return ident;
	}

	public void setIdent(String ident) {
		this.ident = ident;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getChecknum() {
		return checknum;
	}

	public void setChecknum(String checknum) {
		this.checknum = checknum;
	}

	public List<String> getListData() {
		List<String> list = new ArrayList<>();
				
		list.add(generateString(this.tipo));
		list.add(generateString(this.data));
		list.add(generateString(this.valor));
		list.add(generateString(this.ident));
		list.add(generateString(this.memo));
		list.add(generateString(this.checknum));
		
		return list;
	}
}
