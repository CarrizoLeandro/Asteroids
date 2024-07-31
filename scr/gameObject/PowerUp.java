package gameObject;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import math.Vector2D;
import states.GameState;

public abstract class PowerUp extends MovingObject {
    
    public static final double SPEED = 1.0;
    private static final Random random = new Random();
    
    public PowerUp(Vector2D position, Vector2D velocity, double maxVel, BufferedImage texture, GameState gameState, double scale) {
        super(position, velocity, maxVel, texture, gameState, scale);
    }

    @Override
    public double getCollisionRadius() {
        return collisionRadius;
    }

    @Override
    public void update() {
        position = position.add(velocity);
        if (position.getX() < 0 || position.getX() > Constants.ANCHO - anchotx * scale) {
            velocity = new Vector2D(-velocity.getX(), velocity.getY());
        }
        if (position.getY() < 0 || position.getY() > Constants.ALTO - alturatx * scale) {
            velocity = new Vector2D(velocity.getX(), -velocity.getY());
        }
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(texture, (int) position.getX(), (int) position.getY(), null);
    }
    
    @Override
    protected void Destroy() {
        gameState.getMovingObjects().remove(this);
    }

    public abstract void applyEffect(GameState gameState);
    public abstract void applyEffect(Player player);
}

