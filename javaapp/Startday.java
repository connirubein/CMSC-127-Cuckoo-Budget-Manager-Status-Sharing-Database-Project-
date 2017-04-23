package javaapp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.newdawn.slick.*;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.*;
import org.lwjgl.input.Mouse;

import com.entities.Budget;
import com.entities.Item;

import daoimpl.BudgetDaoImpl;


public class Startday extends BasicGameState{
	public static int id = 6;
	public String mouse = "";
	
	private boolean type1 = false;
	private boolean type2 = false;
	
	private TextField budgetField;
	private TextField targetField;
	private TextField errorField;
	
	UserLogInId player;
	
	private String date = getDate();
	private int userId;
	
	public Startday(int sd) {
	
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		budgetField = new TextField(gc, gc.getDefaultFont(), 125, 280, 250, 50);
		budgetField.setBorderColor(new Color(219, 255, 241));
		budgetField.setBackgroundColor(new Color(219, 255, 241));   
		budgetField.setTextColor(Color.black);
		
        targetField = new TextField(gc, gc.getDefaultFont(), 125, 350, 250, 50);
        targetField.setBorderColor(new Color(211, 245, 231));
        targetField.setBackgroundColor(new Color(211, 245, 231));
        targetField.setTextColor(Color.black);
        
        errorField = new TextField(gc, gc.getDefaultFont(), 11, 523, 400, 50);
        errorField.setBorderColor(new Color(211, 245, 231));
        errorField.setBackgroundColor(new Color(211, 245, 231));
        errorField.setTextColor(Color.black);
        
        player = UserLogInId.getInstance();
		userId = player.getCurrentUserId();
		
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		// TODO Auto-generated method stub
		Image bg = new Image("res/bt.png");
    	g.drawImage(bg, 0, 0);
    	g.setColor(Color.white);    	
    	
    	g.drawString(mouse, 50, 50);
    	g.drawString("naa ka sa starttheday", 25, 25);
    	
    	budgetField.render(gc, g);
    	targetField.render(gc, g);
    	errorField.render(gc,g);
    	
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		int xpos = Mouse.getX();
    	int ypos = Mouse.getY();
    	mouse = "x pos = "+xpos+"   y pos = "+ypos;
    	
    	Input input = gc.getInput();	//keyboard and mouse input    	
    	
    	if((xpos>126 && xpos<366) && (ypos>320 && ypos<370) ){
    		if(input.isMousePressed(0) || type1 == true){	//add item name
    			budgetField.setAcceptingInput(true);
    			budgetField.setFocus(true);
    			type1 = true;
    		}
    	}
    	
    	else if((xpos>126 && xpos<366) && (ypos>245 && ypos<294)){
    		if(input.isMousePressed(0) || type2 == true){	//add item price
    			targetField.setAcceptingInput(true);
    			targetField.setFocus(true);
    			type2 = true;
    		}
    	}
    	
    	else if((xpos>37 && xpos<468) && (ypos>540 && ypos<627)){
    		if(input.isMousePressed(0)){
    			Budget budget1 = new Budget();
    			try{
    				budget1 = new Budget(player.getCurrentUserId(),Float.valueOf(budgetField.getText()),date,Float.valueOf(targetField.getText()));
    				System.out.println(Float.valueOf(budgetField.getText())+ " "+date+" "+Float.valueOf(targetField.getText()));
    				BudgetDaoImpl bdi = new BudgetDaoImpl();
        			bdi.insertBudget(budget1);
        			sbg.enterState(5);
    			}catch(NumberFormatException e){
    				e.printStackTrace();
    			}
    			errorField.setText("Enter numbers only. Target Percentage<=100");
    		}
    	}
    	
	}
	
	public static String getDate(){
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    Date date = new Date();
	    return dateFormat.format(date);
	    
	    
	}
	
	@Override
	public int getID() {
		return Startday.id;
	}
}