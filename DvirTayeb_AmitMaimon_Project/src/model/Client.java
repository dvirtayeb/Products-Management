package model;

import java.io.Serializable;

public class Client implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
		StringBuilder builder = new StringBuilder();
		builder.append("[ name=");
		builder.append(name);
		builder.append(", phoneNumber=");
		builder.append(phoneNumber);
		builder.append(", saleUpdate=");
		builder.append(saleUpdate);
		builder.append("]");
		return builder.toString();
	}
	
	
}
