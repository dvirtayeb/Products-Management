package model;

public class ProductMemento {

	private String name;
	private int costPriceManager;
	private int costPriceClient;
	private Client client;
	private String barCode;

	public ProductMemento(Product p) {
		this.name = p.getName();
		this.costPriceManager = p.getCostPriceManager();
		this.costPriceClient = p.getCostPriceClient();
		this.client = p.getClient();
		this.barCode = p.getBarCode();
	}

	public String getBarCode() {
		return barCode;
	}

	public String getName() {
		return name;
	}

	public int getCostPriceManager() {
		return costPriceManager;
	}

	public int getCostPriceClient() {
		return costPriceClient;
	}

	public Client getClient() {
		return client;
	}



}
