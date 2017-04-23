package javaapp;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import daoimpl.BudgetDaoImpl;
import daoimpl.HootDaoImpl;
import daoimpl.ItemDaoImpl;
import daoimpl.PersonDaoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import com.entities.Budget;
import com.entities.Person;
import com.util.ConnectionConfiguration;



public class main extends StateBasedGame{

	public static final int welcome = 0;
	public static final int login = 1;
	public static final int register = 2;
	public static final int profile = 3;
	public static final int worldhoot = 4;
	public static final int budgetprofile = 5;
	public static final int startday = 6;
	
	public static PersonDaoImpl personT = new PersonDaoImpl();
	public static BudgetDaoImpl budgT = new BudgetDaoImpl();
	public static HootDaoImpl hootTable = new HootDaoImpl();
	public static ItemDaoImpl itemTable = new ItemDaoImpl();
	
	/*
	public PersonDaoImpl getPersonT(){
		return personT;
	}
	
	public BudgetDaoImpl getbudgT(){
		return budgT;
	}
	*/
	
	public main(String name){
		super(name);
		this.addState(new Welcome(welcome));
		this.addState(new Login(login));
		this.addState(new Register(register));
		this.addState(new Profile(profile));
		this.addState(new Worldhoot(worldhoot));
		this.addState(new BudgetProfile(budgetprofile));
		this.addState(new Startday(startday));		
	}
	
	public static void main(String[] args) throws SlickException{
		AppGameContainer appgc;
		try{
		appgc = new AppGameContainer(new main("Budget Manager"));
		appgc.setDisplayMode(500, 650, false);
		appgc.setAlwaysRender(true);
		
		personT.createPersonTable();
		budgT.createBudgetTable();
		hootTable.createHootTable();
		itemTable.createItemTable();
		
		appgc.start();
		}
		catch(SlickException ex){
			Logger.getLogger(main.class.getName()).log(Level.SEVERE,null,ex);
		}	
	}
	
	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		// TODO Auto-generated method stub
		//addState(new BasicGame());
		//addState(new Welcome(welcome));
		this.getState(welcome).init(gc,this);
		this.getState(login).init(gc,this);
		this.getState(register).init(gc,this);
		this.getState(profile).init(gc,this);
		this.getState(worldhoot).init(gc,this);
		this.getState(budgetprofile).init(gc,this);
		this.getState(startday).init(gc,this);
		
		this.enterState(welcome);
	}
}