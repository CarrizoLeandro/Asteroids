package ui;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import gameObject.Chronometer;
import graphics.Assets;
import graphics.Text;
import input.MouseInput;
import math.Vector2D;

public class Button {
	private BufferedImage mouseOutImg; //Si el mouuse no esta sobre la img muestra esto
	private BufferedImage mouseInImg; //Si el mouse esta sobre la img muestra esto
	private boolean mouseIn; //Si el mouse esta en la imagen o no
	private Rectangle boundingBox; //Detecta collision mouse
	private String text; //texto del boton
	private Action action;
	private Chronometer clickTimer;
	private static final long CLICK_DELAY = 250; // 500 milliseconds delay
	
	public Button (
			BufferedImage mouseOutImg,
			BufferedImage mouseInImg,
			int x, int y,
			String text,
			Action action
			) {
		this.mouseInImg = mouseInImg;
		this.mouseOutImg = mouseOutImg;
		this.text = text;
		boundingBox = new Rectangle(x,y,mouseInImg.getWidth(),mouseInImg.getHeight());
		this.action =action;
		this.clickTimer = new Chronometer();
	}
	
	public void update() {
		clickTimer.update();
		//Pregunta si el mouse esta pasando por un boton
		if(boundingBox.contains(MouseInput.x, MouseInput.y)){
			mouseIn = true;
		}else {
			mouseIn=false;
		}
		//pregunta si ademas esta presionado el boton izquierdo y pone diley
		if(mouseIn && MouseInput.MLB && !clickTimer.isRunning()) {
            action.doAction();
            clickTimer.run(CLICK_DELAY); // Inicia el cronómetro
        }
	}
	
	public void draw(Graphics g) {
		
		
		if(mouseIn) {
			g.drawImage(mouseInImg, boundingBox.x, boundingBox.y, null);
		}else {
			g.drawImage(mouseOutImg, boundingBox.x, boundingBox.y, null);
		}
		
		FontMetrics fm = g.getFontMetrics(Assets.fontMed);
		
		Text.drawText(
				g,
				text,
				new Vector2D(
						boundingBox.getX()+boundingBox.getWidth()/2,
						boundingBox.getY()+boundingBox.getHeight()/2+fm.getAscent()),
				true,
				Color.BLACK,
				Assets.fontMed);
		
		
	}
}
