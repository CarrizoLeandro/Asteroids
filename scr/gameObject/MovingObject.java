package gameObject;

import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

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
	
	public MovingObject(Vector2D position,Vector2D velocity, double maxVel ,BufferedImage texture,GameState gameState) 
	{
		super(position, texture);
		this.velocity=velocity;
		this.maxVel=maxVel;
		this.gameState = gameState;
		anchotx = texture.getWidth();
		alturatx = texture.getHeight();
		angle=0;
	}

}
