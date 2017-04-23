package com.entities;

public class Hoot {
	
	private int id;
	private int hootID;
	private String message;
	private String date;
	
	public Hoot(){}
	
	public Hoot(int hootID, String message, String date){
		this.hootID = hootID;
		this.message = message;
		this.date = date;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		//System.out.println("id passed to setID = " + id);
		this.id = id;
	}
	
	public int getHootID() {
		return hootID;
	}
	public void setHootID(int hootID) {
		//System.out.println("id passed to setID = " + hootID);
		this.hootID = hootID;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getDate() {
		return date;
	}	
	public void setDate(String date) {
		this.date = date;;
	}	
}
