package model;

import java.io.Serializable;

public class Client implements Observer{
	/**
	 * 
	 */
	//private static final long serialVersionUID = 1L;
	private String name;
	private String phoneNumber;
	private boolean saleUpdate;

	public Client(String name, String phone, boolean saleUpdate) {
		this.name = name;
		phoneNumber = phone;
		this.saleUpdate = saleUpdate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public boolean isSaleUpdate() {
		return saleUpdate;
	}

	public void setSaleUpdate(boolean saleUpdate) {
		this.saleUpdate = saleUpdate;
	}

	@Override
	public String toString() {
		StringBuilder strBuffer = new StringBuilder();
		strBuffer.append("[ name=");
		strBuffer.append(name);
		strBuffer.append(", phoneNumber=");
		strBuffer.append(phoneNumber);
		strBuffer.append(", saleUpdate=");
		strBuffer.append(saleUpdate);
		strBuffer.append("]");
		return strBuffer.toString();
	}

	@Override
	public void update(Observable obs, TheSender sender) {
		if(obs instanceof StoreManagement) {
			TheSender.addClient(name);
		}
	}
		
	
	
}
