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
	private int fileSize;
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
		fileSize = 0;
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
		raf.setLength(fileSize);
	}

	public void writeProduct(Map.Entry<String, Product> product) throws IOException {
		String name = product.getValue().getName(), priceM = "" + product.getValue().getCostPriceManager(),
				priceC = "" + product.getValue().getCostPriceClient(), barcode = product.getValue().getBarCode(),
				clientName = product.getValue().getClient().getName(),
				phone = product.getValue().getClient().getPhoneNumber(),
				saleUpdate = "" + product.getValue().getClient().isSaleUpdate();
		Client c = new Client(clientName, phone, Boolean.parseBoolean(saleUpdate));
		Product p = new Product(name, Integer.parseInt(priceM), Integer.parseInt(priceC), c, barcode);
		fileSize += p.toStringForFile().length();
		raf.write(name.getBytes().length);
		raf.write(name.getBytes());
		raf.write(priceM.getBytes().length);
		raf.write(priceM.getBytes());
		raf.write(priceC.getBytes().length);
		raf.write(priceC.getBytes());
		raf.write(barcode.getBytes().length);
		raf.write(barcode.getBytes());
		raf.write(clientName.getBytes().length);
		raf.write(clientName.getBytes());
		raf.write(phone.getBytes().length);
		raf.write(phone.getBytes());
		raf.write(saleUpdate.getBytes().length);
		raf.write(saleUpdate.getBytes());
	}

	public boolean initProductsFromFile() {
		try {
			Iterator<Product> iterator = iterator();
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

	public boolean removeProductFromFile(String barcode) throws IOException, Exception {
		Iterator<Product> iterator = iterator();
		raf.seek(0);
		boolean hasDeleted = false;
		while (raf.read() != -1) {
			if (iterator.hasNext()) {
				Product p = iterator.next();
				if (p.getBarCode().equals(barcode)) {
					iterator.remove();
					resetMap();
					hasDeleted = true;
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

	public void removeAllProducts() throws IOException {
		Iterator<Product> iterator = iterator();
		raf.seek(0);
		while (raf.read() != -1) {
			if (iterator.hasNext()) {
				iterator.remove();
			}
		}
		clearMap();
	}

	public boolean deleteLastInsertion() throws Exception {
		originC.getProductFromMemento(ct.restore());
		ProductMemento pm = originC.getProductMemento();
		if (pm == null)
			return false;
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
		spamBot.setMsg(msg);
		for (int i = 0; i < clients.size(); i++) {
			clients.get(i).update(this, spamBot);
		}
		
	}


	
	
}
