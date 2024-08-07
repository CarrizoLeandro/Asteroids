package gameObject;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import math.Vector2D;
import states.GameState;

public class Shield extends MovingObject {
	
	private Player player;
	
	public Shield(Vector2D position, Vector2D velocity, double maxVel, BufferedImage texture, GameState gameState,
			double scale, Player player) {
		super(position, velocity, maxVel, texture, gameState, scale);
		this.collisionRadius = Math.max(anchotx, alturatx) * scale / 2.0;
		this.player = player;
	}

	@Override
	public double getCollisionRadius() {
		return collisionRadius*0.9;
	}

	@Override
	public void update() {
		if (gameState.getPlayer() != null) {
        this.position = gameState.getPlayer().getPosition().subtract(new Vector2D(+5,+5));
        this.angle = gameState.getPlayer().getAngle();
        collidesWith();
    	}
		
	}

	@Override
	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
        AffineTransform at = AffineTransform.getTranslateInstance(position.getX()-anchotx/4+30, position.getY()-alturatx/4+15);
        at.scale(scale, scale);
        at.rotate(angle,anchotx/4+35,alturatx/4+35);
        g2d.drawImage(texture, at, null);
        
        g2d.setColor(Color.GREEN);
        int diameter = (int) (2 * getCollisionRadius());
        int x = (int) (getCenter().getX() - getCollisionRadius());
        int y = (int) (getCenter().getY() - getCollisionRadius());
        g.drawOval(x, y, diameter, diameter);
        
	}
	
	 @Override
	    protected void Destroy() {
	        gameState.getMovingObjects().remove(this);
	        Player.shieldActive=false;
	        player.decrementExtraShield();
	        
	        //test
	        System.out.println("Estado del escudo:"+Player.shieldActive);
	        
	    }

}
