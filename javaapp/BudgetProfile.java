package javaapp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFrame;

import org.newdawn.slick.*;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.*;
import org.lwjgl.input.Mouse;

import com.entities.Budget;
import com.entities.Item;
import com.entities.Person;

import daoimpl.BudgetDaoImpl;
import daoimpl.ItemDaoImpl;
import daoimpl.PersonDaoImpl;


public class BudgetProfile extends BasicGameState{
	public static int id = 5;
	public String mouse = "";
	private TextField searchField;
	private TextField budgetField;
	private TextField targetField;
	private TextField nameField;
	private TextField priceField;
	private TextField expensesField;
	private TextField metField;
	private TextField dateField;
	private TextField errorField;
	private boolean type = false;
	private boolean type1 = false;
	private boolean type2 = false;
	private boolean type3 = false;
	private boolean type4 = false;
	private boolean type5 = false;
	private boolean isSearching = false;
	PersonDaoImpl pdi= new PersonDaoImpl();
	
	UserLogInId player;
	
	private String date = getDate();
	private int userId;
	
	ItemDaoImpl idi = new ItemDaoImpl();
	BudgetDaoImpl bdi = new BudgetDaoImpl();
	Budget budget = new Budget();//Current stats
	
	public BudgetProfile(int bp) {

	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		searchField = new TextField(gc, gc.getDefaultFont(), 221, 17, 290, 40);
		searchField.setBorderColor(new Color(179, 185, 185));
		searchField.setBackgroundColor(new Color(179, 185, 185));
		
		nameField = new TextField(gc, gc.getDefaultFont(), 264, 480, 206, 38);
		nameField.setBorderColor(new Color(122, 153, 138));
		nameField.setBackgroundColor(new Color(122, 153, 138));
		
		priceField = new TextField(gc, gc.getDefaultFont(), 264, 530, 206, 38);
		priceField.setBorderColor(new Color(122, 153, 138));
		priceField.setBackgroundColor(new Color(122, 153, 138));
		
		expensesField = new TextField(gc, gc.getDefaultFont(), 43, 403, 185, 51);
		expensesField.setBorderColor(new Color(122, 153, 138));
		expensesField.setBackgroundColor(new Color(122, 153, 138));
		
		metField = new TextField(gc, gc.getDefaultFont(), 264, 403, 206, 51);
		metField.setBorderColor(new Color(122, 153, 138));
		metField.setBackgroundColor(new Color(122, 153, 138));
		
		dateField = new TextField(gc, gc.getDefaultFont(), 7, 40, 186, 31);
		dateField.setBorderColor(new Color(179, 185, 185));
		dateField.setBackgroundColor(new Color(179, 185, 185));
		
		budgetField = new TextField(gc, gc.getDefaultFont(), 75, 104, 97, 93);
		budgetField.setBorderColor(new Color(212, 200, 188));
		budgetField.setBackgroundColor(new Color(212, 200, 188));
		
		targetField = new TextField(gc, gc.getDefaultFont(), 315, 104, 97, 93);
		targetField.setBorderColor(new Color(212, 200, 188));
		targetField.setBackgroundColor(new Color(212, 200, 188));
		
		errorField = new TextField(gc, gc.getDefaultFont(), 266, 586, 84, 50);
		errorField.setBorderColor(new Color(212, 200, 188));
		errorField.setBackgroundColor(new Color(212, 200, 188));
		
		player = UserLogInId.getInstance();
		userId = player.getCurrentUserId();
		
		budget = bdi.selectByIdDate(player.getCurrentUserId(), getDate());
		
		//fill textboxes
		expensesField.setText(Float.toString(budget.getExpense()));
    	if(budget.isMeet()==true){metField.setText("Yes!");}
    	else{metField.setText("No!");}
    	dateField.setText(date);
    	targetField.setText(Float.toString(budget.getTarget()));
    	budgetField.setText(Float.toString(budget.getDaily_budget()));
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		Image bg = new Image("res/addprofile.png");
    	g.drawImage(bg, 0, 0);
    	g.setColor(Color.white);    	
    	
    	searchField.render(gc, g);
    	expensesField.render(gc, g);
    	metField.render(gc, g);
    	dateField.render(gc, g);
    	targetField.render(gc, g);
    	budgetField.render(gc, g);
    	errorField.render(gc,g);
    	if(!isSearching){
	    	nameField.render(gc, g);
	    	priceField.render(gc, g);
	    }
    	
    	//g.drawString(mouse, 50, 50);
    	//g.drawString("naa ka sa profile", 25, 25);
    	
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		int xpos = Mouse.getX();
    	int ypos = Mouse.getY();
    	mouse = "x pos = "+xpos+"   y pos = "+ypos;
    	
    	
    	Input input = gc.getInput();	//keyboard and mouse input
    	
    	if((xpos>422 && xpos<494) && (ypos>6 && ypos<35) ){
    		if(input.isMousePressed(0)){	//back
    			if(isSearching){
    				budget=bdi.selectByIdDate(player.getCurrentUserId(), date);
	    			expensesField.setText(Float.toString(budget.getExpense()));
	    	    	if(budget.isMeet()==true){metField.setText("Yes!");}
	    	    	else{metField.setText("No!");}
	    	    	dateField.setText(getDate());
	    	    	targetField.setText(Float.toString(budget.getTarget()));
	    	    	budgetField.setText(Float.toString(budget.getDaily_budget()));
	    	    	searchField.setText("");
	    	    	date = getDate();
	    	    	isSearching=false;
    			}
    			else{
    				sbg.enterState(3);
    			}
    		}
    	}
    	
    	else if((xpos>227 && xpos<493) && (ypos>603 && ypos<632) ){
    		if(input.isMousePressed(0) || type1 == true){	
    			searchField.setAcceptingInput(true);
    			searchField.setFocus(true);
    			type1 = true;
    		}
    	}
    	
    	else if((xpos>300 && xpos<430) && (ypos>556 && ypos<600) ){
    		if(input.isMousePressed(0)){
    			isSearching = true;
    			String searchDate = searchField.getText();
    			Budget budgetS = bdi.selectByIdDate(player.getCurrentUserId(), searchDate);
    			System.out.println(budgetS.getExpense()+" "+budgetS.getTarget()+" "+budgetS.getDaily_budget()+" ");
    			System.out.println(player.getCurrentUserId()+ " "+searchDate);
    			expensesField.setText(Float.toString(budgetS.getExpense()));
    	    	if(budgetS.isMeet()==true){metField.setText("Yes!");}
    	    	else{metField.setText("No!");}
    	    	dateField.setText(searchDate);
    	    	targetField.setText(Float.toString(budgetS.getTarget()));
    	    	budgetField.setText(Float.toString(budgetS.getDaily_budget()));
    	    	date = searchDate;
    		}
    	}
    	
    	else if((xpos>268 && xpos<472) && (ypos>132 && ypos<168) ){
    		if(input.isMousePressed(0) || type2 == true){	//add item name
    			nameField.setAcceptingInput(true);
    			nameField.setFocus(true);
    			type2 = true;
    		}
    	}
    	
    	else if((xpos>261 && xpos<466) && (ypos>86 && ypos<121) ){
    		if(input.isMousePressed(0) || type3 == true){	//add item price
    			priceField.setAcceptingInput(true);
    			priceField.setFocus(true);
    			type3 = true;
    		}
    	}
    	
    	else if((xpos>21 && xpos<481) && (ypos>310 && ypos<353) ){
    		if(input.isMousePressed(0)){	//display item table
    			Object[][] data = idi.getItemList(player.getCurrentUserId(),date);
    			JFrame frame = new JFrame("Item List");
    	        ItemList newContentPane = new ItemList(data);
    	        newContentPane.setOpaque(true); //content panes must be opaque
    	        frame.setContentPane(newContentPane);
    	        frame.pack();
    	        frame.setVisible(true);
    		}
    	}

    	else{
    		type1 = type2 = type3 = type4 = type5 =  false; 
    	}
    	
    	
    	if((xpos>40 && xpos<240) && (ypos>22 && ypos<62) ){
    		if(input.isMousePressed(0)){	//add item
    			if(!isSearching){
	    			Item item = new Item();
	    			try{
	    				item = new Item(player.getCurrentUserId(),date,nameField.getText(),Float.valueOf(priceField.getText()));
	    			}catch(NumberFormatException e){
	    				e.printStackTrace();
	    				errorField.setText("ERR");
	    			}
	    			
	    			if(idi.addItem(item)==false){
	    				errorField.setText("ERR");
	    			}
	    			else{
	    				
	    				errorField.setText("");
	    				Person newPerson = pdi.selectById(player.getCurrentUserId());
	    				newPerson.setDateOfLastAdd(getDate());
	    			//	int oldID = newPerson.getId();
	    				pdi.update(newPerson, newPerson.getId());
	    			
	    			}
	    			
	    			//update stats
	    			budget=bdi.selectByIdDate(player.getCurrentUserId(), getDate());
	    			System.out.println(player.getCurrentUserId()+" "+ getDate());
	    			expensesField.setText(Float.toString(budget.getExpense()));
	    	    	if(budget.isMeet()==true){metField.setText("Yes!");}
	    	    	else{metField.setText("No!");}
	    	    	dateField.setText(date);
	    	    	targetField.setText(Float.toString(budget.getTarget()));
	    	    	budgetField.setText(Float.toString(budget.getDaily_budget()));
	    	    	System.out.println("JSDLKJASKLD"+Float.toString(budget.getExpense())+" "+Float.toString(budget.getTarget())+" "+Float.toString(budget.getDaily_budget()));
    			}
    			else{errorField.setText("ERR");}
    		}
    	}
	}

	@Override
	public int getID() {
		return BudgetProfile.id;
	}
	
	public static String getDate(){
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    Date date = new Date();
	    return dateFormat.format(date);
	}
}