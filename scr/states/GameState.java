package states;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import gameObject.Constants;
import gameObject.MovingObject;
import gameObject.Meteor;
import gameObject.Player;
import gameObject.Size;
import graphics.Assets;
import math.Vector2D;

public class GameState {
	
	private Player player;
	private ArrayList <MovingObject> movingObjects = new ArrayList<MovingObject>();
	
	private int meteors;

	public GameState() {
		player = new Player(new Vector2D(100,500),new Vector2D(),5,Assets.player, this);
		movingObjects.add(player);
		
		meteors=1;
		
		startWave();
	
	}
	
	
	public void divideMeteor(Meteor meteor) {
		Size size = meteor.getSize();
		
		BufferedImage[] textures = size.textures;
		
		Size newSize = null;
		
		switch(size) {
		case BIG:
			newSize=Size.MED;
			break;
		case MED:
			newSize=Size.SMALL;
			break;
		case SMALL:
			newSize=Size.TINY;
			break;
		default:
			return;
		}
		
		for(int i =0 ; i < size.quantity;i++) {
			movingObjects.add(new Meteor(
					meteor.getPosition(),
					new Vector2D (0,1).setDirection(Math.random()*Math.PI*2),
					Constants.METEOR_VEL*(Math.random() * 2 + 1),
					textures[(int)(Math.random()*textures.length)],
					this,
					newSize
					));
		}
		
		
	}
	
	private void startWave() {
		double x, y ;
		
		for(int i =0; i < meteors ; i ++) {
			x= i % 2 == 0 ? Math.random()*Constants.ANCHO:0;
			y=i % 2 == 0 ? 0 : Math.random()*Constants.ALTO;
			
			BufferedImage texture = Assets.bigs[(int)(Math.random()*Assets.bigs.length)];
			
			movingObjects.add(new Meteor(
					new Vector2D (x,y),
					new Vector2D (0,1).setDirection(Math.random()*Math.PI*2),
					Constants.METEOR_VEL*Math.random()+1,
					texture,
					this,
					Size.BIG
					));
			
		}
		meteors++;
	}
	
	
	public void update() {
		
		for(int i =0; i < movingObjects.size();i++) {
			movingObjects.get(i).update();
		}
		for(int i =0; i < movingObjects.size();i++) {
			if (movingObjects.get(i)instanceof Meteor) {
				return;
			}
		}
		startWave();
	}
	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

		for(int i =0; i < movingObjects.size();i++) {
			movingObjects.get(i).draw(g);
		}
	}
	public ArrayList<MovingObject> getMovingObjects() {
		return movingObjects;
	}
	
	
}
