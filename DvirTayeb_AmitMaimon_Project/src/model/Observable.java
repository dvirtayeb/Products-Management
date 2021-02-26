package model;

public interface Observable {
	boolean addObserver(Client o);
	void deleteObserver(Client o);
	void notifyObservers(String msg);
}
