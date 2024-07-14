package gameObject;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import math.Vector2D;
import states.GameState;

public class Laser extends MovingObject {

	public Laser(Vector2D position, Vector2D velocity, double maxVel,double angle, BufferedImage texture, GameState gameState,double scale) 
	{
		super(position, velocity, maxVel, texture, gameState,scale);
		this.angle = angle;
		this.velocity = velocity.scale(maxVel);
	}
	
	@Override
    public double getCollisionRadius() {
		double maxRadius = Math.max(anchotx, alturatx) / 2.0;
	    return maxRadius;

    }
	@Override
	public void update() {
		position = position.add(velocity);
		if (position.getX() < 0 || position.getX() > Constants.ANCHO ||
				position.getY() < 0 || position.getY() > Constants.ALTO){
			Destroy();
		}
		collidesWith();
	}

	@Override
	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		
		at= AffineTransform.getTranslateInstance(position.getX()-anchotx/2,position.getY());
		
		at.rotate(angle,anchotx/2,0);
		
		g2d.drawImage(texture, at, null);
		
	}
	

	public Vector2D getCenter() {
		return new Vector2D(position.getX()+anchotx/2,position.getY()+anchotx/2);
	}

	public double getAnchotx() {
		// TODO Auto-generated method stub
		return anchotx;
	}

}
