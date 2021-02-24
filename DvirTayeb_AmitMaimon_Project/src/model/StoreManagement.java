package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class StoreManagement implements StoreManagementFunc, Comparator<Product> {

	private static final long serialVersionUID = 1L;
	Map<String, Product> productMap;
	TreeMap<String, Product> sortedByAscending;
	TreeMap<String, Product> sortedByDescending;
	LinkedHashMap<String, Product> sortedByInserting;
	int selectedSort = -1;

	private File productFile;
	private boolean isAppendableProductFile;
	private RandomAccessFile raf;
	private int lastLocation = 0;

	public StoreManagement() throws FileNotFoundException {
		productMap = new HashMap<String, Product>();
		initAppandable();
		raf = new RandomAccessFile(productFile, "rw");
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
			throw new Exception();
		}

	}

	public void saveProductsToFile() throws Exception {
		raf.seek(0);
		lastLocation = 0;
		switch (selectedSort) {
		case 0:
			for (Map.Entry<String, Product> product : sortedByAscending.entrySet()) {
				raf.setLength(lastLocation);
				writeProduct(product);
			}
			break;
		case 1:
			for (Map.Entry<String, Product> product : sortedByDescending.entrySet()) {
				raf.setLength(lastLocation);
				writeProduct(product);
			}
			break;
		case 2:
			for (Map.Entry<String, Product> product : sortedByInserting.entrySet()) {
				raf.setLength(lastLocation);
				writeProduct(product);
			}
			break;
		default:
			throw new Exception();
		}
	}

	public void writeProduct(Map.Entry<String, Product> product) throws IOException {
		String name = product.getValue().getName(), priceM = "" + product.getValue().getCostPriceManager(),
				priceC = "" + product.getValue().getCostPriceClient(), barcode = product.getValue().getBarCode(),
				clientName = product.getValue().getClient().getName(),
				phone = product.getValue().getClient().getPhoneNumber(),
				saleUpdate = "" + product.getValue().getClient().isSaleUpdate();
		Client c = new Client(clientName, phone, Boolean.parseBoolean(saleUpdate));
		Product p = new Product(name,Integer.parseInt(priceM), Integer.parseInt(priceC), c, barcode);
		lastLocation += p.toStringForFile().length();
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
			System.out.println("maybe the problem in the objectInputS");
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
}
