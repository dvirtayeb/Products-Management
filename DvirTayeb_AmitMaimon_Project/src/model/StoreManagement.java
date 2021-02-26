package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class StoreManagement implements StoreManagementFunc, Comparator<Product>, Observable {

	private static final long serialVersionUID = 1L;

	Map<String, Product> productMap;
	private CareTaker ct;
	private OriginatorClass originC;

	TreeMap<String, Product> sortedByAscending;
	TreeMap<String, Product> sortedByDescending;
	LinkedHashMap<String, Product> sortedByInserting;
	int selectedSort = -1;

	private File productFile;
	private boolean isAppendableProductFile;
	private RandomAccessFile raf;
	private ArrayList<Client> clients;
	private TheSender spamBot; // his name is spamBot because he is spamming people with discounts.
	

	public StoreManagement() throws FileNotFoundException {
		productMap = new HashMap<String, Product>();
		initAppandable();
		raf = new RandomAccessFile(productFile, "rw");
		originC = new OriginatorClass();
		ct = new CareTaker();
		clients = new ArrayList<Client>();
	}

	// create file:
	private void initAppandable() {
		productFile = new File("products.txt");
		isAppendableProductFile = productFile.exists();
	}

	public void sort() throws Exception {
		switch (selectedSort) {
		case 0:
			sortedByAscending.putAll(productMap);
			break;
		case 1:
			sortedByDescending.putAll(productMap);
			break;
		case 2:
			sortedByInserting.putAll(productMap);
			break;
		default:
			break;
		}

	}

	public void saveProductsToFile() throws Exception {
		raf.seek(0);
		switch (selectedSort) {
		case 0:
			for (Map.Entry<String, Product> product : sortedByAscending.entrySet()) {
				writeProduct(product);
			}
			break;
		case 1:
			for (Map.Entry<String, Product> product : sortedByDescending.entrySet()) {
				writeProduct(product);
			}
			break;
		case 2:
			for (Map.Entry<String, Product> product : sortedByInserting.entrySet()) {
				writeProduct(product);
			}
			break;
		default:
			throw new Exception();
		}
	}

	public void writeProduct(Map.Entry<String, Product> product) throws IOException {
		String name = product.getValue().getName(), barcode = product.getValue().getBarCode(),
				clientName = product.getValue().getClient().getName(),
				phone = product.getValue().getClient().getPhoneNumber();
		boolean saleUpdate = product.getValue().getClient().isSaleUpdate();
		int priceM = product.getValue().getCostPriceManager(), priceC = product.getValue().getCostPriceClient();

		raf.writeUTF(name);
		raf.writeUTF(String.valueOf(priceM));
		raf.writeUTF(String.valueOf(priceC));
		raf.writeUTF(barcode);
		raf.writeUTF(clientName);
		raf.writeUTF(phone);
		raf.writeUTF(String.valueOf(saleUpdate));
	}
	public boolean initProductsFromFile() {
		try {
			Iterator<Product> iterator = iterator();
			if(!clients.isEmpty())
				clients.clear();
			while (raf.read() != -1) {
				if (iterator.hasNext())
					addProduct(iterator.next());
			}
			return true;
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (Exception e) {
			System.out.println("No product to load");
		}
		return false;
	}

	public TreeMap<String, Product> getSortedByAscending() {
		return sortedByAscending;
	}

	public TreeMap<String, Product> getSortedByDescending() {
		return sortedByDescending;
	}

	public LinkedHashMap<String, Product> getSortedByInserting() {
		return sortedByInserting;
	}

	public void setSortedByAscending(TreeMap<String, Product> sortedByAscending) {
		this.sortedByAscending = sortedByAscending;
	}

	public void setSortedByDescending(TreeMap<String, Product> sortedByDescending) {
		this.sortedByDescending = sortedByDescending;
	}

	public void setSortedByInserting(LinkedHashMap<String, Product> sortedByInserting) {
		this.sortedByInserting = sortedByInserting;
	}

	public int getSelectedSort() {
		return selectedSort;
	}

	public void setSelectedSort(int selectedSort) {
		this.selectedSort = selectedSort;
	}

	public boolean isAppendableProductFile() {
		return isAppendableProductFile;
	}

	public void setAppendableProductFile(boolean isAppendableProductFile) {
		this.isAppendableProductFile = isAppendableProductFile;
	}

	public void addProduct(Product product) {
		productMap.put(product.getBarCode(), product);
		originC.setProduct(new ProductMemento(product));
		addObserver(product.getClient());
		ct.save(originC.save());

	}

	public void removeProduct(String key) {
		productMap.remove(key);
	}

	public Map<String, Product> getProductMap() {
		return productMap;
	}

	@Override
	public int compare(Product p1, Product p2) {
		return p2.getBarCode().compareTo(p1.getBarCode());
	}

	@Override
	public Iterator<Product> iterator() {
		try {
			return new FileIterator(raf);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public int removeProductFromFile(String barcode) throws IOException, Exception {
		Iterator<Product> iterator = iterator();
		raf.seek(0);
		int hasDeleted = 0;
		if(raf.read()==-1)
			return -1;
		while (raf.read() != -1) {
			if (iterator.hasNext()) {
				Product p = iterator.next();
				if (p.getBarCode().equals(barcode)) {
					deleteObserver(p.getClient());
					iterator.remove();
					resetMap();
					hasDeleted = 1;
					break;
				}
			}
		}

		return hasDeleted;
	}

	public void resetMap() throws IOException, Exception {
		clearMap();
		raf.seek(0);
		initProductsFromFile();
		sort();

	}

	public boolean removeAllProducts() throws IOException {
		Iterator<Product> iterator = iterator();
		raf.seek(0);
		if(raf.read()==-1)
			return false;
		while (raf.read() != -1) {
			if (iterator.hasNext()) {
				iterator.remove();
				raf.seek(0);
			}
		}
		clients.clear();
		clearMap();
		return true;
	}

	public int deleteLastInsertion() throws Exception {
		originC.getProductFromMemento(ct.restore());
		ProductMemento pm = originC.getProductMemento();
		if (pm == null)
			return -1;
		return removeProductFromFile(pm.getBarCode());
	}

	public void clearMap() {
		switch (selectedSort) {
		case 0:
			sortedByAscending.clear();
			break;
		case 1:
			sortedByDescending.clear();
			break;
		case 2:
			sortedByInserting.clear();
			break;
		default:
			break;
		}
		productMap.clear();
	}

	public String showProfits() {
		StringBuffer sb = new StringBuffer("");
		int totalProfit = 0;
		for (Map.Entry<String, Product> product : productMap.entrySet()) {
			Product prod = product.getValue();
			int profit = prod.getCostPriceClient() - prod.getCostPriceManager();
			totalProfit += profit;
			sb.append("Profit on " + prod.getName() + ": " + profit + "\n");
		}
		sb.append("Total profit for the store: " + totalProfit);
		return sb.toString();
	}

	public String searchProduct(String barcode) {
		for (Map.Entry<String, Product> product : productMap.entrySet()) {
			Product prod = product.getValue();
			if(prod.getBarCode().equals(barcode)) {
				return prod.toString();
			}
		}
		return "Product not found!";
	}
	
	@Override
	public boolean addObserver(Client c) {
		if(c.isSaleUpdate()) {
			clients.add(c);
			return true;
		}
		return false;
	}

	@Override
	public void deleteObserver(Client c) {
		clients.remove(c);
		
	}

	@Override
	public void notifyObservers(String msg) {
		spamBot=TheSender.getSender();
		spamBot.resetClientList();
		spamBot.setMsg(msg);
		for (int i = 0; i < clients.size(); i++) {
			clients.get(i).update(this, spamBot);
		}
		
	}


	
	
}
