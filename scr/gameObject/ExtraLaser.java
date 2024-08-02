package gameObject;

import java.awt.image.BufferedImage;

import math.Vector2D;
import states.GameState;

public class ExtraLaser extends PowerUp {

	public ExtraLaser(Vector2D position, Vector2D velocity, double maxVel, BufferedImage texture, GameState gameState,
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
		    player.incrementExtraLaser();
		    this.Destroy();
		}
	
	@Override
    public double getCollisionRadius() {
	    return collisionRadius * 3.0;

    }

}
