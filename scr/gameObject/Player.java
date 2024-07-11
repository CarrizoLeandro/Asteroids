package gameObject;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import gameObejcts.Chronometer;
import graphics.Assets;
import input.KeyBoard;
import math.Vector2D;
import states.GameState;

public class Player extends MovingObject {

	private Vector2D heading;
	private Vector2D acceleration;
	private boolean aceleracion=false;
	private Chronometer fireRate;
	

	public Player(Vector2D position, Vector2D velocity, double maxVel , BufferedImage texture,GameState gameState) {
		super(position, velocity, maxVel, texture, gameState);
		heading=new Vector2D(0,1);
		acceleration=new Vector2D();
		fireRate= new Chronometer();

	}

	@Override
	public void update() {
		
		
		if(KeyBoard.SHOOT && !fireRate.isRunning()) {
			gameState.getMovingObjects().add(0, new Laser(
					getCenter().add(heading.scale(anchotx)),
					heading,
					10,
					angle,
					Assets.redLaser,
					gameState
					));
			fireRate.run(Constants.FIRERATE);
		}
		
		if(KeyBoard.RIGHT) {
			angle+=Constants.DELTAANGLE;
		}
		if(KeyBoard.LEFT) {
			angle-=Constants.DELTAANGLE;
		}
		if (KeyBoard.UP) {
			acceleration = heading.scale(Constants.ACC);
			aceleracion = true;
		}else {
			if (velocity.getMagnitude()!=0){
				acceleration=(velocity.scale(-1).normalize()).scale(Constants.ACC/2);
				aceleracion = false;
			}
		}
		
		velocity=velocity.add(acceleration);
		
		velocity = velocity.limit(maxVel);
		
		heading=heading.setDirection(angle-Math.PI/2);
		
		position= position.add(velocity);
		
		if(position.getX() > Constants.ANCHO) {
		    position.setX(0);
		}
		if(position.getY() > Constants.ALTO) {
		    position.setY(0);
		}
		if(position.getX() < 0) {
		    position.setX(Constants.ANCHO);
		}
		if(position.getY() < 0) {
		    position.setY(Constants.ALTO);
		}
		
		fireRate.update();
		
	}

	@Override
	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		
		AffineTransform at1= AffineTransform.getTranslateInstance(position.getX()+ anchotx/2 + 20, position.getY() + alturatx/2+22);
		AffineTransform at2= AffineTransform.getTranslateInstance(position.getX()+ anchotx/2 - 35, position.getY()+alturatx/2 + 22);
		
		at1.rotate(angle,-20,-22);
		at2.rotate(angle,+35,-22);
		
		if(aceleracion) {
			g2d.drawImage(Assets.speed, at1,null);
			g2d.drawImage(Assets.speed, at2,null);
		}
		
		
		
		at= AffineTransform.getTranslateInstance(position.getX(), position.getY());
		at.rotate(angle,anchotx/2,alturatx/2);
		g2d.drawImage(Assets.player,  at, null);
	}
	
	public Vector2D getCenter() {
		return new Vector2D(position.getX()+anchotx/2,position.getY()+alturatx/2);
	}
}
