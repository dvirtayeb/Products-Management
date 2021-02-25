package model;

import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public interface StoreManagementFunc extends Iterable<Product>, Serializable{
	
	public void sort() throws Exception ;
	public TreeMap<String, Product> getSortedByAscending();

	public TreeMap<String, Product> getSortedByDescending();

	public LinkedHashMap<String, Product> getSortedByInserting();

	public void setSortedByAscending(TreeMap<String, Product> sortedByAscending);

	public void setSortedByDescending(TreeMap<String, Product> sortedByDescending);

	public void setSortedByInserting(LinkedHashMap<String, Product> sortedByInserting);

	public int getSelectedSort() ;

	public void setSelectedSort(int selectedSort);

	public void addProduct(Product product);

	public void removeProduct(String key);
	
	public boolean removeProductFromFile(String barcode) throws IOException, Exception;
	
	public void removeAllProducts() throws IOException;
	
	public void resetMap() throws IOException, Exception;

	public Map<String, Product> getProductMap();
	
	public void clearMap();
	
	public boolean deleteLastInsertion() throws Exception;
	
	public String showProfits();
	
	public String searchProduct(String barcode);
}
