package model;

import java.io.Serializable;

public class Product implements Serializable, Comparable<Product> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private int costPriceManager;
	private int costPriceClient;
	private Client client;
//	private Barcode barCode; // need new Class for this
	private String barCode;

	public Product(String name, int costPriceManager, int costPriceClient, Client client, String barCode) {
		this.name = name;
		this.costPriceManager = costPriceManager;
		this.costPriceClient = costPriceClient;
		this.client = client;
		this.barCode = barCode;

	}

	public String getBarCode() {
		return barCode.toString();
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCostPriceManager() {
		return costPriceManager;
	}

	public void setCostPriceManager(int costPriceManager) {
		this.costPriceManager = costPriceManager;
	}

	public int getCostPriceClient() {
		return costPriceClient;
	}

	public void setCostPriceClient(int costPriceClient) {
		this.costPriceClient = costPriceClient;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public ProductMemento saveToMememnto() {
		return new ProductMemento(this);
	}

	@Override
	public int compareTo(Product product) {
		String barCode = product.getBarCode();
		return this.barCode.toString().compareTo(barCode);
	}

	@Override
	public String toString() {
		return "Product:\nname=" + name + ", costPriceManager=" + costPriceManager + ", costPriceClient="
				+ costPriceClient + "\nclient=" + client + "\nbarCode=" + barCode + "\n";
	}

	public String toStringForFile() {
		String costC = "" + costPriceClient;
		String costM = "" + costPriceManager;
		String salesUpdate=""+client.isSaleUpdate();
		return name.length() + name + costM.length() + costM + costC.length() + costC+
				barCode.length() + barCode+ client.getName().length() + client.getName() + client.getPhoneNumber().length()
				+ client.getPhoneNumber()+salesUpdate.length()+salesUpdate;
	}
}
