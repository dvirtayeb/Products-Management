package model;

public interface Observable {
	void addObserver(Observer o);
	void deleteObserver(Observer o);
	void notifyObservers(Client client);
	void notifyObserver(Observer o, int index);
}
