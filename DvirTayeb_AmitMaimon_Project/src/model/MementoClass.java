package model;

public class MementoClass implements Memento {
	
	private ProductMemento pmc;
	
	public MementoClass(ProductMemento pm) {
		this.pmc = pm;
	}
	@Override
	public ProductMemento restore() {
		return pmc;
	}
	
}
