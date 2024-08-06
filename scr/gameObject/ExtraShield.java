package gameObject;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import math.Vector2D;
import states.GameState;

public class ExtraShield extends PowerUp{

	public ExtraShield(Vector2D position, Vector2D velocity, double maxVel, BufferedImage texture, GameState gameState,
			double scale) {
		super(position, velocity, maxVel, texture, gameState, scale);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void applyEffect(GameState gameState) {
		this.Destroy();
		
	}

	@Override
	public void applyEffect(Player player) {
		player.incrementExtraShield();
		this.Destroy();
		
	}
	
	@Override
    public double getCollisionRadius() {
	    return collisionRadius * 3.0;

    }
	@Override
	public void draw(Graphics g) {
	    Graphics2D g2d = (Graphics2D) g;
	    AffineTransform at = AffineTransform.getTranslateInstance(position.getX(), position.getY());
	    at.scale(scale, scale);
	    g2d.drawImage(texture, at, null);
	}


}
