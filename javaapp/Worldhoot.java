package javaapp;

import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

import org.newdawn.slick.*;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.*;
import org.lwjgl.input.Mouse;

import com.entities.Hoot;
import com.entities.Person;
import com.util.ConnectionConfiguration;

import daoimpl.HootDaoImpl;
import daoimpl.PersonDaoImpl;

public class Worldhoot extends BasicGameState{
	public static int id = 4;
	public String mouse = "";
	private boolean type = false;
	private TextField hootField;
	UserLogInId player;
	
	public Worldhoot(int wh) {

	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		player = UserLogInId.getInstance();
		hootField = new TextField(gc, gc.getDefaultFont(), 75, 25, 377, 200);
		hootField.setBorderColor(new Color(179, 190, 186));
		hootField.setBackgroundColor(new Color(179, 190, 186));  
		/*
		if(getID() == 4){
		JFrame f= new JFrame();  
	    String data[][]={ {"101","Amit","670000"},    
	                          {"102","Jai","780000"},    
	                          {"101","Sachin","700000"}};    
	    String column[]={"ID","NAME","SALARY"};         
	    JTable jt=new JTable(data,column);    
	    jt.setBounds(30,40,200,300);          
	    JScrollPane sp=new JScrollPane(jt);    
	    f.add(sp);          
	    f.setSize(300,400);    
	    f.setVisible(true); 
		}
		*/
		/*
		JTextArea hootHere = new JTextArea();
		JButton btnHoot = new JButton();
		hootHere.setSize(400, 300);
		//
		
		JTextArea jta = new JTextArea();
		jta = loadHoots(jta);
		
		
	//	JTable table = new JTable(data, columnNames);
      //  table.setEnabled(false);
        
		JScrollPane jsp = new JScrollPane(jta);
		
		
     //   JPanel panel = new JPanel();
       // panel.add(new JLabel(label));
        
        JFrame frame = new JFrame();
        frame.add(hootHere);
        frame.add(btnHoot);
        frame.add(jsp, BorderLayout.NORTH);
        //frame.add(new JScrollPane(table), BorderLayout.SOUTH);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setSize(500, 500);
        frame.setVisible(true);
		*/
		
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		Image bg = new Image("res/worldhoots.png");
    	g.drawImage(bg, 0, 0);
    	g.setColor(Color.white);    	
    	
    	hootField.render(gc, g);
    	g.drawString(mouse, 50, 50);
    	g.drawString("naa ka sa wh", 25, 25);    	
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		int xpos = Mouse.getX();
    	int ypos = Mouse.getY();
    	mouse = "x pos = "+xpos+"   y pos = "+ypos;
    	
    	
    	Input input = gc.getInput();	//keyboard and mouse input
    	if((xpos>0 && xpos<206) && (ypos>0 && ypos<40) ){
    		if(input.isMousePressed(0)){	//0: left-clicked
    			sbg.enterState(3);
    		}
    	}
    	
    	if((xpos>76 && xpos<453) && (ypos>421 && ypos<629) ){
    		if(input.isMousePressed(0) || type == true){	//0: left-clicked
    			hootField.setAcceptingInput(true);
    			hootField.setFocus(true);
    			type = true;
    		}
    	}
    	
    	else if((xpos>34 && xpos<462) && (ypos>372 && ypos<417) ){
    		if(input.isMousePressed(0)){	//0: left-clicked
    			//hoot	
       		}
    	}
    	
    	else{
    		type = false;
    	}
    	
	}

	@Override
	public int getID() {

		return Worldhoot.id;
	}

	public int getRowsOfTableHoot(){

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		int counter = 0;
		try{
			connection = ConnectionConfiguration.getConnection();
			preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM hoot");
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				counter++;
			}
		}
		catch(Exception e){
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
		System.out.println("hoot row count = "+counter);
		return counter;
	}
	
	public JTextArea loadHoots(JTextArea jta){
		
		HootDaoImpl hdi = new HootDaoImpl();
		PersonDaoImpl pdi = new PersonDaoImpl();
		Person person = new Person();
		person = pdi.selectById(player.getCurrentUserId());
		
		List <Hoot> hoots = new ArrayList<Hoot>();
		hoots = hdi.selectAll();
		
		for(Hoot hoot : hoots){
			jta.append((person.getUserName() + "\t" + hoot.getDate()+"\n"+hoot.getMessage()));
		}
		
		return jta;
	}
	
}