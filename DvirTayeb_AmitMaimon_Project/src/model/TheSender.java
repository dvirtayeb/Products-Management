package model;

import java.util.ArrayList;

public class TheSender {
	private String msg;
	private static TheSender sender;
	private static ArrayList<String> clientsNames;
	
	private TheSender() {
		this.msg = "";
		clientsNames=new ArrayList<String>();
	}
	

	public static TheSender getSender() { 
		// Singleton:
		if(sender == null)
			sender = new TheSender();
		
		return sender;
	}
	
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public String getMsg() {
		return msg;
	}
	public static void addClient(String str) {
		clientsNames.add(str);
	}

	public static ArrayList<String> getClientsNames() {
		return clientsNames;
	}
	public void resetClientList() {
		clientsNames.clear();
	}
	
	@Override
	public String toString() {
		return "The message is: " + msg;
	}
}
