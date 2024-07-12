package gameObject;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import graphics.Assets;
import math.Vector2D;
import states.GameState;

public class Meteor extends MovingObject{
	
	private Size size;

	public Meteor(Vector2D position, Vector2D velocity, double maxVel, BufferedImage texture, GameState gameState, Size size) {
		super(position, velocity, maxVel, texture, gameState);
		this.size = size;
		this.velocity = velocity.scale(maxVel);
	}

	@Override
	public void update() {
		position = position.add(velocity);
		
		if(position.getX() > Constants.ANCHO) {
		    position.setX(-anchotx);
		}
		if(position.getY() > Constants.ALTO) {
		    position.setY(-alturatx);
		}
		if(position.getX() < -anchotx) {
		    position.setX(Constants.ANCHO);
		}
		if(position.getY() < -alturatx) {
		    position.setY(Constants.ALTO);
		}
		
		angle+=Constants.DELTAANGLE/2;
	}

	@Override
	public void Destroy() {
		gameState.divideMeteor(this);
		super.Destroy();
	}
	
	
	@Override
	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		
		double scaleFactor = 1.5;
		
		at= AffineTransform.getTranslateInstance(position.getX(), position.getY());
		at.scale(scaleFactor, scaleFactor);
		at.rotate(angle,anchotx/2,alturatx/2);
		
		g2d.drawImage(texture,  at, null);
		
	}
	
	public Size getSize() {
		return size;
	}

}
