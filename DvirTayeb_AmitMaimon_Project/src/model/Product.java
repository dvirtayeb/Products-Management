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

	@Override
	public int compareTo(Product product) {
		String barCode = product.getBarCode();
		return this.barCode.toString().compareTo(barCode);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Product: name=" + name + ", costPriceManager=" + costPriceManager + ", costPriceClient="
				+ costPriceClient + ", client=" + client + ", barCode=" + barCode + "\n");
		return builder.toString();
	}
}
