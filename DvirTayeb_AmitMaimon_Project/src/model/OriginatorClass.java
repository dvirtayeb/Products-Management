package model;

public class OriginatorClass implements Originator {
	
	
	private ProductMemento pm;

	public void setProduct(ProductMemento productMemento) {
		this.pm = productMemento;
	}

	@Override
	public MementoClass save() {
		return new MementoClass(pm);
	}

	public ProductMemento getProductMemento() {
		return pm;
	}

	public boolean getProductFromMemento(MementoClass memento) {
		if(memento==null)
			return false;
		pm = memento.restore();
		return true;
	}

}
