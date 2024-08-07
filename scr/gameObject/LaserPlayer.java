package gameObject;

import java.awt.image.BufferedImage;

import math.Vector2D;
import states.GameState;

public class LaserPlayer extends Laser{

	public LaserPlayer(Vector2D position, Vector2D velocity, double maxVel, double angle, BufferedImage texture,
			GameState gameState, double scale) {
		super(position, velocity, maxVel, angle, texture, gameState, scale);
		// TODO Auto-generated constructor stub
	}

}
