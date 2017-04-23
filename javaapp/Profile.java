package javaapp;

import java.awt.BorderLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import daoimpl.BudgetDaoImpl;
import daoimpl.HootDaoImpl;
import daoimpl.ItemDaoImpl;
import daoimpl.PersonDaoImpl;

public class Profile extends BasicGameState{
	
	public static int id = 3;
	public String mouse = "";
	private String name = "";
	private String password = "";
	public String displayName = "";
	public String displayUserName = "";
	public String displayLevel = "";
	//private TextArea myHoots;
	
	UserLogInId player;
	Boolean isRefreshClicked ;
	PersonDaoImpl pdi=new PersonDaoImpl();
	HootDaoImpl hdi=new HootDaoImpl();
	
	public Profile(int p) {

	}

	public void Profile(int p, String name, String pw) {
		this.name = name;
		this.password = pw;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		
		player = UserLogInId.getInstance();

		isRefreshClicked = false;
		
		
		
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		Image bg = new Image("res/personviewbg.png");
    	g.drawImage(bg, 0, 0);
    	g.setColor(Color.white);    	
    	
    	//g.drawString(mouse, 50, 350);
    	//g.drawString("naa ka sa profile", 25, 25);
    	
    	g.drawString(displayName, 90, 20);
    	
    	
    	//g.drawString(displayLevel, 227, 220);
    	
    	g.drawString(name, 100, 100);
    	
    	if (checkIfHooter(player.getCurrentUserId())){
    		
    		g.drawString("You have not hooted yet :(", 50, 350);
    	}
    	else{
    		g.drawString("My latest hoot:", 50, 350);
    		Hoot hoot = getLatestHoot(player.currentUserId);
    		g.drawString(hoot.getDate(),50,370);
    		g.drawString(hoot.getMessage(),50,390);
    	}
    	g.setColor(Color.black);   
    	g.drawString(displayUserName, 220, 220);
		
    	
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		int xpos = Mouse.getX();
    	int ypos = Mouse.getY();
    	mouse = "x pos = "+xpos+"   y pos = "+ypos;
    	
    	
    	Person person = pdi.selectById(player.getCurrentUserId());
    	person.setFirstName(pdi.selectById(player.getCurrentUserId()).getFirstName());
    	person.setLastName(pdi.selectById(player.getCurrentUserId()).getLastName());
    	person.setUserName(pdi.selectById(player.getCurrentUserId()).getUserName());
    	 displayName = person.getFirstName() +" "+ person.getLastName();
        // System.out.println("displayname = " + displayName);
         displayUserName = "@"+person.getUserName();
        // displayLevel = "LEVEL "+person.getLevel();
    	
    	
    	Input input = gc.getInput();	//keyboard and mouse input
    	if((xpos>16 && xpos<71) && (ypos>583 && ypos<635) ){
    		if(input.isMousePressed(0)){	//WORLDHOOT
				player.changeId(player.getCurrentUserId());
				
				isRefreshClicked = worldHootFrame(gc,sbg,delta);
				
				if(isRefreshClicked){
					worldHootFrame(gc, sbg, delta);
				}
				
				
				
    		}
    	}
    	
    	if((xpos>35 && xpos<465) && (ypos>36 && ypos<94) ){
    		if(input.isMousePressed(0)){	//BUDGETPROFILE
    			PersonDaoImpl pdi = new PersonDaoImpl();
    			Person checkPerson = pdi.selectById(player.getCurrentUserId());
				if(checkPerson.getDateOfLastAdd().equals(getDate())){
    				sbg.enterState(5);
    			}
    			else if(!(checkPerson.getDateOfLastAdd().equals(getDate())) || checkPerson.getDateOfLastAdd().equals("0") ){
    				sbg.enterState(6);
    			}
    				
    		}
    	}
    	
    	if((xpos>35 && xpos<177) && (ypos>343 && ypos<378) ){
    		if(input.isMousePressed(0)){	//LOGOUT
    			//TODO
    			sbg.enterState(0);
    		}
    	}
    	
    	if((xpos>320 && xpos<460) && (ypos>343 && ypos<378) ){
    		if(input.isMousePressed(0)){	//DEACIVATE
    			//TODO
    			PersonDaoImpl pdi = new PersonDaoImpl();
    			pdi.delete(player.getCurrentUserId());
    			HootDaoImpl hdi= new HootDaoImpl();
    			hdi.deleteHoot(player.getCurrentUserId());
    			BudgetDaoImpl bdi = new BudgetDaoImpl();
    			ItemDaoImpl idi = new ItemDaoImpl();
    			bdi.deleteById(player.getCurrentUserId());
    			idi.deleteById(player.getCurrentUserId());
    			sbg.enterState(0);
    		}
    	}
    	
    	
    	
	}

	private boolean worldHootFrame(GameContainer gc, StateBasedGame sbg, int delta) {
		
		isRefreshClicked = false;
		
		JTextArea hootHere = new JTextArea(10,40);
		//hootHere.append("Results:");
		hootHere.setLineWrap(true);
		hootHere.setWrapStyleWord(true);
		hootHere.setVisible(true);
		
		
		JButton btnHoot = new JButton("HOOT!");
		btnHoot.setSize(400,100);
		btnHoot.setLocation(10, 50);
		btnHoot.setVisible(true);
		
		
		JTextArea showAllHoots = new JTextArea(10,70);
		showAllHoots.setLineWrap(true);
		showAllHoots.setWrapStyleWord(true);
		showAllHoots = loadHoots(showAllHoots);
		showAllHoots.setEditable(false);
		showAllHoots.setVisible(true);
		JScrollPane jsp = new JScrollPane(showAllHoots);
		//jsp.setVisible(true);
		
		JPanel jp= new JPanel();
		jp.setLayout(new BorderLayout());
		jp.add(hootHere);
		JPanel jp2= new JPanel();
		
		
		jp2.add(btnHoot);
		
		JFrame frame = new JFrame();
        //frame.add(hootHere);
        //frame.add(btnHoot);
        //frame.add(jsp, BorderLayout.NORTH);
		frame.add(jp, BorderLayout.NORTH);
		frame.add(jp2, BorderLayout.CENTER);
		frame.add(jsp,BorderLayout.SOUTH);
        //frame.add(new JScrollPane(table), BorderLayout.SOUTH);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setSize(500, 650);
        frame.setVisible(true);
        
        btnHoot.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e){
	        	Input input = gc.getInput();
		        	HootDaoImpl hdi= new HootDaoImpl();
		        	String hootText = hootHere.getText();
		        	if(hootText.equals("")){
		        		//do nothing
		        	}
		        	else{
		        		Hoot hoot = new Hoot(player.getCurrentUserId(),hootText,getDate());
		        		
		        		hdi.insertHoot(hoot);
		        		
		        	}
	        	
	        }
		});
		
		

		return isRefreshClicked;
	}

	@Override
	public int getID() {
		return Profile.id;
	}
	
public JTextArea loadHoots(JTextArea jta){

		
		HootDaoImpl hdi = new HootDaoImpl();
		PersonDaoImpl pdi = new PersonDaoImpl();
		Person person = new Person();
		//person = pdi.selectById(player.getCurrentUserId());
		
		List <Hoot> hoots = new ArrayList<Hoot>();
		hoots = hdi.selectAll();
		
		for(Hoot hoot : hoots){
			person = pdi.selectById(hoot.getHootID());
			jta.append((person.getUserName() + "\t" + hoot.getDate()+"\n"+hoot.getMessage() + "\n\n"));
			System.out.println((person.getUserName() + "\t" + hoot.getDate()+"\n"+hoot.getMessage() + "\n"));
		}
		
		return jta;
	}

public static String getDate(){
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date date = new Date();
    return dateFormat.format(date);
    
    
}

public static boolean checkIfHooter(int id){
	
	HootDaoImpl hdi = new HootDaoImpl();
	
	//System.out.println("countHoot = "+hdi.countHoot(id));
	
	return hdi.isListEmpty(id);
	
}


public static Hoot getLatestHoot(int id){
	
	HootDaoImpl hdi = new HootDaoImpl();
	
	Hoot hoot = new Hoot();
	List <Hoot> hoots = new ArrayList<Hoot>();
	hoots = hdi.selectById(id);
	
	for(int i=0; i<hoots.size();i++){
		hoot = hoots.get(i);
	}
	
	return hoot;
	
	
}
	

}