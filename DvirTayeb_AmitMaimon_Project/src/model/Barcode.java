package model;

import java.io.Serializable;

public class Barcode implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String barcode;

	public Barcode(String barcode) {
		this.barcode = barcode;
	}
	
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	
	public String getBarcode() {
		return barcode;
	}
	
	public String toString() {
		return barcode;
	}
}
