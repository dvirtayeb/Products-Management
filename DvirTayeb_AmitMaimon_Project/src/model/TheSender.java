package model;

public class TheSender {
	private String msg;
	private static TheSender sender;
	
	private TheSender() {
		this.msg = "";
	}
	
	public static TheSender getSender() { 
		// Singleton:
		if(sender == null)
			sender = new TheSender();
		return sender;
	}

	public String getMsg() {
		return msg;
	}
	
	@Override
	public String toString() {
		return "The message is: " + msg;
	}
}
