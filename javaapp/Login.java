package javaapp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.newdawn.slick.*;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.*;
import org.lwjgl.input.Mouse;

import com.entities.Person;
import com.util.ConnectionConfiguration;

import daoimpl.BudgetDaoImpl;
import daoimpl.HootDaoImpl;
import daoimpl.PersonDaoImpl;


public class Login extends BasicGameState{
	public static int id = 1;
	public String mouse = "";
	public String displayName = "";
	public String displayUserName = "";
	public String displayLevel = "";
	public String notification = "";
	public String enterUsername = "Username:";
	public String enterPassword = "Password:";
	
	private boolean type1 = false;
	private boolean type2 = false;
	private TextField nameField;
	private TextField passwordField;
	UserLogInId player;
	PersonDaoImpl pdi=new PersonDaoImpl();
	HootDaoImpl hdi=new HootDaoImpl();
	
	public Login(int login) {
	
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		player = UserLogInId.getInstance();
		
		nameField = new TextField(gc, gc.getDefaultFont(), 125, 277, 250, 50);
		nameField.setBorderColor(new Color(219, 255, 241));
		nameField.setBackgroundColor(new Color(219, 255, 241));    
        passwordField = new TextField(gc, gc.getDefaultFont(), 125, 356, 250, 50);
        passwordField.setBorderColor(new Color(211, 245, 231));
        passwordField.setBackgroundColor(new Color(211, 245, 231));
        //System.out.println("player user id = "+player.getCurrentUserId());
        
        nameField.setTextColor(Color.black);
    	passwordField.setTextColor(Color.black);
       
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		// TODO Auto-generated method stub
		Image bg = new Image("res/login.png");
    	g.drawImage(bg, 0, 0);
    	g.setColor(Color.white);    	
    	
    	//g.drawString(mouse, 50, 50);
    	//g.drawString("naa ka sa login", 25, 25);
    	
    	//g.drawString(str, x, y);
    	//g.drawString(enterPassword, x, y);
    	
    	nameField.render(gc, g);
    	passwordField.render(gc, g);
    	
    	g.setColor(Color.red);
    	g.drawString(notification, 25, 550);
    	
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		int xpos = Mouse.getX();
    	int ypos = Mouse.getY();
    	mouse = "x pos = "+xpos+"   y pos = "+ypos;
    	
    	
    	
    	
    	Input input = gc.getInput();	//keyboard and mouse input
    	if((xpos>85 && xpos<347) && (ypos>313 && ypos<381) ){
    		if(input.isMousePressed(0) || type1 == true){	//0: left-clicked
    			//System.out.println("naclick 1");
    			nameField.setAcceptingInput(true);
    			nameField.setFocus(true);
    			type1 = true;
    		}
    	}
    	else if((xpos>85 && xpos<411) && (ypos>230 && ypos<301) ){
    		if(input.isMousePressed(0) || type2 == true){	//0: left-clicked
    			//System.out.println("naclick 2");
    			passwordField.setFocus(true);
    			type2 = true;
    			
    		}
    	}
    	else if((xpos>85 && xpos<411) && (ypos>151 && ypos<220)){
    		if(input.isMousePressed(0)){	//0: left-clicked
    			String uName = nameField.getText();
    			String pWord = passwordField.getText();
    			
    			if (loginPerson(uName,pWord)){
    				//do nothing
    				notification = "Either you entered a username that doesn't match \n\tany account or you entered a wrong password. \n"
    						+ "Try again!";
					
    			}
    			else{
    				Person newPerson = new Person();
    				PersonDaoImpl personDaoImpl = new PersonDaoImpl();
    				newPerson = personDaoImpl.selectByUserName(uName);
    				player.changeId(newPerson.getId());
    				
    				nameField.setText("");
    				passwordField.setText("");
    				
    				//newPerson.setDateOfLastAdd(getDate());
    				//i/nt oldID = newPerson.getId();
    				//personDaoImpl.update(newPerson, oldID);
    				sbg.enterState(3);
    			}
    		}
    	}
    	else{
    		if(input.isMousePressed(0)){
    		type1 = type2 = false;
    		}
    	}
    	
}
	
public boolean loginPerson(String user, String pass){
	
		
	Connection connection = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	boolean userNameNotFound;
	boolean tryagain;
	//do{
		userNameNotFound = true;
		tryagain = false;
		
		try {
			connection = ConnectionConfiguration.getConnection();
			//preparedStatement = connection.prepareStatement("SELECT user_name FROM Person");
			Statement myStmt = connection.createStatement();
		
			//preparedStatement.setString(1, person.getUserName());
			resultSet = myStmt.executeQuery("SELECT user_name, pass_word FROM Person");
		
			while(resultSet.next()){
				if(resultSet.getString("user_name").equals(user)){
					//System.out.println("username exists!");
					
					userNameNotFound = false;
					if(resultSet.getString("pass_word").equals(pass)){
						tryagain = false;
					}
					else{
						//System.out.println("Wrong password!");
						tryagain = true;
						
					}
					break;
				}
			}
			if(userNameNotFound){
				//System.out.println("Username not found!");
				tryagain = true;
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
	//}while(tryagain);
		
		if(tryagain){
			return true;
		}
		
		//PersonDaoImpl personT = new PersonDaoImpl();
		//BudgetDaoImpl budgT = new BudgetDaoImpl();
		return false;
//		loggedIn(personT.selectByUserName(user));
	}
	
	@Override
	public int getID() {
		return Login.id;
	}
	
	public static String getDate(){
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    Date date = new Date();
	    return dateFormat.format(date);
	    
	    
	}
}