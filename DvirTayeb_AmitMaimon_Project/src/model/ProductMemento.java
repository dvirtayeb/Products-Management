package model;

public class ProductMemento {
	
	private Product product; 
	private String name;
	private int costPriceManager;
	private int costPriceClient;
	private Client client;
	private String barCode;
	
	
	public ProductMemento (Product p) {
		product=p;
		this.name = p.getName();
		this.costPriceManager = p.getCostPriceManager();
		this.costPriceClient = p.getCostPriceClient();
		this.client = p.getClient();
		this.barCode = p.getBarCode();
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
	public void restore() {
		product.setName(name);
		product.setClient(client);
		product.setCostPriceClient(costPriceClient);
		product.setCostPriceManager(costPriceManager);
	}

}
