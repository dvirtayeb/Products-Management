package model;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Stack;

public class CareTaker { 
	private Map<String, ProductMemento> productMap;

	
	public CareTaker() {
		this.productMap = new LinkedHashMap<String, ProductMemento>();
	}
	
	public void save(ProductMemento pm,String key) {
		productMap.put(key,pm);
	}
	
	public void restore(String key) {
		productMap.get(key).restore();
	}

	public void remove(String index) {
		productMap.remove(index);
	}
	
//	public void print() {
//		Iterator<Map.Entry<String, ProductMemento>> iterator = productMap.entrySet().iterator();
//		System.out.println("Saves:");
//		while (iterator.hasNext()) {
//			System.out.println(iterator);
//			Map.Entry<String, ProductMemento> entry = (Map.Entry<String, ProductMemento>) iterator.next();
//		}
//	}
}
