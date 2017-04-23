package com.entities;

public class Person {
	
	private int id;
	private String firstName;
	private String lastName;
	private String userName;
	private String pWord;
	private int level;
	private String dateOfLastAdd; 
	private int numberOfMeets;
	
	public Person(){}
	public Person(String firstName, String lastName, String userName, String pWord, int level, String dateOfLastAdd, int numberOfMeets){
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;
		this.pWord = pWord;
		this.level = level;
		this.dateOfLastAdd = dateOfLastAdd;
		this.numberOfMeets = numberOfMeets;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassWord() {
		return pWord;
	}
	public void setPassword(String pWord) {
		this.pWord = pWord;
	}
	public int getLevel(){
		return level;
	}
	public void setLevel(int level){
		this.level = level;
	}
	public String getDateOfLastAdd() {
		return dateOfLastAdd;
	}
	public void setDateOfLastAdd(String dateOfLastAdd) {
		this.dateOfLastAdd = dateOfLastAdd;
	}
	public int getNumberOfMeets(){
		return numberOfMeets;
	}
	public void setNumberofMeets(int numberOfMeets){
		this.numberOfMeets = numberOfMeets;
	}
	
	
	/*
	 * setInt(5,  person.getLevel());
			preparedStatement.setString(6,  person.getDateOfLastAdd());
			preparedStatement.setInt(7,  person.getNumberOfMeets());
	 * */

}
