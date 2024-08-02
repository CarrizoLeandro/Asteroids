package gameObject;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import graphics.Assets;
import graphics.Sounds;
import math.Vector2D;
import states.GameState;

public class Meteor extends MovingObject{
	
	private Size size;
	private Sounds explosionMeteor;

	public Meteor(Vector2D position, Vector2D velocity, double maxVel, BufferedImage texture, GameState gameState, Size size,double scale) {
		super(position, velocity, maxVel, texture, gameState, scale);
		this.size = size;
		this.velocity = velocity.scale(maxVel);
		explosionMeteor = new Sounds(Assets.explosionMeteor);
	}
	
	@Override
    public double getCollisionRadius() {
        switch (size) {
            case BIG:
                return collisionRadius;
            case MED:
                return collisionRadius;
            case SMALL:
                return collisionRadius;
            default:
                return collisionRadius;
        }
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
		
		explosionMeteor.play();
		explosionMeteor.changeVolumen(Constants.VOLUMEN_METEOR);
		gameState.divideMeteor(this);
		gameState.addScore(Constants.METEOR_SCORE,position);
		super.Destroy();
		
		if(this.size==Size.MED) {
			double probabilidad=Math.random();
			Vector2D position=this.getPosition();
			Vector2D velocity=this.velocity;
			double maxVel = this.maxVel;
			BufferedImage texture= Assets.extraLife;
			GameState gameState=this.gameState;
			double scale=1.2;
			
			if(probabilidad <= 0.2) {
				gameState.generatePowerUp(position, velocity, maxVel, texture, gameState, scale);
			}
		}
		
		
		
		
	}
	
	
	@Override
	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		
		
		at= AffineTransform.getTranslateInstance(position.getX(), position.getY());
		at.scale(Constants.METEOR_SCALE, Constants.METEOR_SCALE);
		at.rotate(angle,anchotx/2,alturatx/2);
		
		g2d.drawImage(texture,  at, null);
		
	}
	
	public Size getSize() {
		return size;
	}
	
	

}
