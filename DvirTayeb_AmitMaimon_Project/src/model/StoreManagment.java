package model;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class StoreManagment implements Comparator<Product> {

	Map<String, Product> productMap;
	TreeMap<String, Product> sortedByAscending;
	TreeMap<String, Product> sortedByDescending;
	LinkedHashMap<String, Product> sortedByInserting;
	int selectedSort = -1;

	public StoreManagment() {
		productMap = new HashMap<String, Product>();
	}

	public void sort() throws Exception {
//		int selected = storeM.getSelectedSort();
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

	public void addProduct(Product product) {
		productMap.put(product.getBarCode(), product);
	}

	public void removeProducts(String key) {
		productMap.remove(key);
	}

	public Map<String, Product> getProductMap() {
		return productMap;
	}

	@Override
	public int compare(Product p1, Product p2) {
		return p2.getBarCode().compareTo(p1.getBarCode());
	}
}
