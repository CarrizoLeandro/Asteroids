package gameObject;

import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import math.Vector2D;
import states.GameState;

public abstract class MovingObject extends GameObject{
	
	protected Vector2D velocity;
	protected AffineTransform at;
	protected double angle;
	protected double maxVel;
	protected int anchotx;
	protected int alturatx;
	
	protected GameState gameState;
	protected double scale;
	protected double collisionRadius;
	
	public MovingObject(Vector2D position,Vector2D velocity, double maxVel ,BufferedImage texture,GameState gameState, double scale) 
	{
		super(position, texture);
		this.velocity=velocity;
		this.maxVel=maxVel;
		this.gameState = gameState;
		anchotx = texture.getWidth();
		alturatx = texture.getHeight();
		angle=0;
		this.scale = scale;

        // Calcula el radio de colisión escalado
        double scaledWidth = anchotx * scale;
        double scaledHeight = alturatx * scale;
        this.collisionRadius = Math.max(scaledWidth, scaledHeight) / 2.0;
	}
	
	 public abstract double getCollisionRadius();
	
	protected void collidesWith() {
		ArrayList <MovingObject>movingObjects = gameState.getMovingObjects();
		
		for (int i = 0; i < movingObjects.size();i++) {
			MovingObject m = movingObjects.get(i);
			if (m.equals(this)) {
				continue;
			}
			double distance = m.getCenter().subtract(getCenter()).getMagnitude();
			
			if(distance < m.getCollisionRadius() && movingObjects.contains(this)) {
				objectCollision(m,this);
			}
		
		}
	}
	
	private void objectCollision(MovingObject a, MovingObject b) {
		if (a instanceof Player && ((Player)a).isSpawning() || b instanceof Player && ((Player)b).isSpawning()) {
			return;
		}
		
		if (!(a instanceof Meteor && b instanceof Meteor)) {
			gameState.playExplosion(getCenter());
			a.Destroy();
			b.Destroy();
		}
	}
	
	protected void Destroy() {
		gameState.getMovingObjects().remove(this);
	}
	
	public Vector2D getCenter() {
		return new Vector2D(position.getX()+anchotx*scale/2,position.getY()+alturatx*scale/2);
	}

}
