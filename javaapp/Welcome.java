package javaapp;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import org.lwjgl.input.Mouse;

public class Welcome extends BasicGameState{
	public static int id = 0;
	public String mouse = "";

	public Welcome(int welcome) {

	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		Image bg = new Image("res/welcome.png");
    	g.drawImage(bg, 0, 0);
    	g.setColor(Color.white);    	
    	
    	//g.drawString(mouse, 50, 50);
    	//g.drawString("naa ka sa welcome", 25, 25);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		int xpos = Mouse.getX();
    	int ypos = Mouse.getY();
    	mouse = "x pos = "+xpos+"   y pos = "+ypos;
    	
    	
    	Input input = gc.getInput();	//keyboard and mouse input
    	if((xpos>136 && xpos<372) && (ypos>238 && ypos<299) ){
    		if(input.isMouseButtonDown(0)){	//0: left-clicked
    			sbg.enterState(1);	//1: login state
    		}
    	}
    	
    	if((xpos>84 && xpos<414) && (ypos>128 && ypos<189) ){
    		if(input.isMouseButtonDown(0)){	//0: left-clicked
    			
    			sbg.enterState(2);	//2: register state
    		}
    	}
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return Welcome.id;
	}
}