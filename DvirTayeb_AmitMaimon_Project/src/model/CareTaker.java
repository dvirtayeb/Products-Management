package model;

import java.util.Stack;

public class CareTaker { 
	private Stack<MementoClass> pmStack;

	
	public CareTaker() {
		this.pmStack = new Stack<>();
		
	}
	
	public void save(MementoClass pm) {
		pmStack.push(pm);
	}
	
	public MementoClass restore() {
		if(pmStack.isEmpty())
			return null;
		return pmStack.pop();
	}


}
