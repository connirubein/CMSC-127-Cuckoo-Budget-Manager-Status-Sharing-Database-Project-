package javaapp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.*;
import org.lwjgl.input.Mouse;

import com.entities.Person;
import com.util.ConnectionConfiguration;

import daoimpl.BudgetDaoImpl;
import daoimpl.PersonDaoImpl;


public class Register extends BasicGameState{
	public static int id = 2;
	public String mouse = "";
	public String notification = "";
	private boolean type1 = false;
	private boolean type2 = false;
	private boolean type3 = false;
	private boolean type4 = false;
	private TextField firstNameField;
	private TextField lastNameField;
	private TextField usernameField;
	private TextField passwordField;
	
	UserLogInId player;
	
	public Register(int reg) {

	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		
		player = UserLogInId.getInstance();
		
		firstNameField = new TextField(gc, gc.getDefaultFont(), 125, 251, 250, 50);
		firstNameField.setBorderColor(new Color(251, 237, 224));
		firstNameField.setBackgroundColor(new Color(251, 237, 224));    
		firstNameField.setText("ENTER FIRST NAME");
		
		lastNameField = new TextField(gc, gc.getDefaultFont(), 125, 325, 250, 50);
		lastNameField.setBorderColor(new Color(237, 228, 213));
		lastNameField.setBackgroundColor(new Color(237, 228, 213)); 
		lastNameField.setText("ENTER LAST NAME");
		
        usernameField = new TextField(gc, gc.getDefaultFont(), 125, 400, 250, 50);
        usernameField.setBorderColor(new Color(233, 220, 211));
		usernameField.setBackgroundColor(new Color(233, 220, 211));
		usernameField.setText("ENTER USERNAME");
		
        passwordField = new TextField(gc, gc.getDefaultFont(), 125, 475, 250, 50);
        passwordField.setBorderColor(new Color(226, 214, 202));
        passwordField.setBackgroundColor(new Color(226, 214, 202));
        passwordField.setText("ENTER PASSWORD");
}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		Image bg = new Image("res/register.png");
    	g.drawImage(bg, 0, 0);
    	g.setColor(Color.white);    	
    	
    	//g.drawString(mouse, 50, 50);
    	//g.drawString("naa ka sa register", 25, 25);
    	
    	firstNameField.setTextColor(Color.black);
    	lastNameField.setTextColor(Color.black);
    	usernameField.setTextColor(Color.black);
    	passwordField.setTextColor(Color.black);
    	
    	firstNameField.render(gc, g);
    	lastNameField.render(gc, g);
    	usernameField.render(gc, g);
    	passwordField.render(gc, g);
    	
    	g.setColor(Color.red);
    	g.drawString(notification, 50, 613);
    	
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		int xpos = Mouse.getX();
    	int ypos = Mouse.getY();
    	mouse = "x pos = "+xpos+"   y pos = "+ypos;    	
    	
    	
    	Input input = gc.getInput();	//keyboard and mouse input
    	if((xpos>86 && xpos<411) && (ypos>341 && ypos<401) ){
    		if(input.isMousePressed(0) || type1 == true){	//0: left-clicked
    			//firstNameField.setText("");
    			firstNameField.setAcceptingInput(true);
    			firstNameField.setFocus(true);
    			type1 = true;
    		}
    	}    
    	else if((xpos>86 && xpos<411) && (ypos>271 && ypos<329) ){
    		if(input.isMousePressed(0) || type2 == true){	//0: left-clicked
    			//kamo na bahala ani
    			lastNameField.setAcceptingInput(true);
    			lastNameField.setFocus(true);
    			type2 = true;
    		}
    	}
    	else if((xpos>86 && xpos<411) && (ypos>191 && ypos<254) ){
    		if(input.isMousePressed(0) || type3 == true){	//0: left-clicked
    			//kamo na bahala ani
    			usernameField.setAcceptingInput(true);
    			usernameField.setFocus(true);
    			type3 = true;
    		}
    	}
    	else if((xpos>86 && xpos<411) && (ypos>113 && ypos<179) ){
    		if(input.isMousePressed(0) || type4 == true){	//0: left-clicked
    			//kamo na bahala ani
    			passwordField.setAcceptingInput(true);
    			passwordField.setFocus(true);
    			type4 = true;
    		}
    	}
    	else if((xpos>86 && xpos<411) && (ypos>36 && ypos<103) ){
    		if(input.isMousePressed(1)){	//0: left-clicked
    			//kamo na bahala ani
    			
    			//System.out.println("hihihihi");
    			
    			String fName = firstNameField.getText();
    			String lName = lastNameField.getText();
    			String uName = usernameField.getText();
    			String pWord = passwordField.getText();
    			
    			if (registerPerson(fName,lName,uName,pWord)){
    				//do nothing
    			}
    			else{
    				Person newPerson = new Person();
    				PersonDaoImpl personDaoImpl = new PersonDaoImpl();
    				newPerson = personDaoImpl.selectByUserName(uName);
    				player.changeId(newPerson.getId());
    				firstNameField.setText("");
    				lastNameField.setText("");
    				usernameField.setText("");
    				passwordField.setText("");
    				sbg.enterState(3);
    			}
    		}
    	}
    	else if((xpos>381 && xpos<493) && (ypos>513 && ypos<645) ){
    		if(input.isMousePressed(0) || type2 == true){	//0: left-clicked
    			//kamo na bahala ani
    			sbg.enterState(0);
    			
    		}
    	}
    	else{
    		type1 = type2 = type3 = type4 = false;
    	}
    	
	}

	@Override
	public int getID() {

		return Register.id;
	}


	public boolean registerPerson(String first, String last, String user, String pass){
	
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		boolean invalidUserName = false;
		
		//do{

			try {
				connection = ConnectionConfiguration.getConnection();
				//preparedStatement = connection.prepareStatement("SELECT user_name FROM Person");
				Statement myStmt = connection.createStatement();
				
				resultSet = myStmt.executeQuery("SELECT user_name FROM Person");
				
				while(resultSet.next()){
					if(resultSet.getString("user_name").equals(user)){
						//System.out.println("parehas!");
						invalidUserName = true;
						notification = "Username already taken! Try another one.";
						//System.out.println("Username already taken! Try another one:");
						break;
					}
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
			
			finally{
				if(resultSet!=null){
					try {
						resultSet.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				if(preparedStatement!=null){
					try {
						preparedStatement.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				if(connection!=null){
					try {
						connection.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				
			}
			
			//}while(invalidUserName);
			if(invalidUserName){
				return true;
			}
			Person newPerson = new Person(first, last, user, pass,0,"0",0);
			//System.out.println("Register Successful!");
			PersonDaoImpl personT = new PersonDaoImpl();
			BudgetDaoImpl budgT = new BudgetDaoImpl();
			
			int newID = personT.insert(newPerson);
			int oldID = newPerson.getId();
			newPerson.setId(newID);
			personT.update(newPerson, oldID);
			return false;
	}
	
	public static String getDate(){
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    Date date = new Date();
	    return dateFormat.format(date);
	    
	    
	}

}
/*
public List<String> pollInput() {
List<String> keys = new ArrayList<>();
while (Keyboard.next()) {
    keys.add((char) Keyboard.getEventKey() + "");
}
return keys;
}*/