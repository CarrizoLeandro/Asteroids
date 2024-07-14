package gameObject;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import graphics.Assets;
import graphics.Sounds;
import math.Vector2D;
import states.GameState;

public class Ufo extends MovingObject{

	private ArrayList<Vector2D> path;
	private Vector2D currentNode;
	private int index;
	private boolean following;
	private Chronometer fireRate;
	private Sounds shoot;
	private Sounds explosionNave;
	
	public Ufo(Vector2D position, Vector2D velocity, double maxVel, BufferedImage texture, ArrayList<Vector2D> path, GameState gameState, double scale) {
		super(position, velocity, maxVel, texture, gameState,scale);
		this.path = path;
		index=0;
		following = true;
		fireRate = new Chronometer();
		fireRate.run(Constants.UFO_FIRE_RATE);
		shoot =new Sounds(Assets.ufoShoot);
		explosionNave = new Sounds(Assets.explosionNave); 
	}
	
	private Vector2D pathFollowing() {
		currentNode = path.get(index);
		double distanceToNode = currentNode.subtract(getCenter()).getMagnitude();
		
		if (distanceToNode < Constants.NODE_RADIUS) {
			index ++;
			if(index >= path.size()) {
				following = false;
			}
		}
		
		return seekForce(currentNode);
	}
	
	@Override
    public double getCollisionRadius() {
	    return collisionRadius;

    }
	private Vector2D seekForce(Vector2D target) {
		Vector2D desiredVelocity = target.subtract(getCenter());
		desiredVelocity = desiredVelocity.normalize().scale(maxVel);
		return desiredVelocity.subtract(velocity);
	}
	

	@Override
	public void update() {
		Vector2D pathFollowing;
		
		if(following) {
			pathFollowing = pathFollowing();
			
		}else {
			pathFollowing = new Vector2D();
		}
		
		pathFollowing = pathFollowing.scale(1/Constants.UFO_MASS);
		
		velocity = velocity.add(pathFollowing);
		
		velocity = velocity.limit(maxVel);
		
		position = position.add(velocity);
		
		if (position.getX() > Constants.ANCHO || position.getY() > Constants.ALTO ||
				position.getX() < 0 || position.getY() < 0) {
			Destroy();
		}
		
		if (!fireRate.isRunning()) {
		    Vector2D toPlayer = gameState.getPlayer().getCenter().subtract(getCenter());
		    toPlayer = toPlayer.normalize();
		    
		    // Obtener el ángulo directo hacia el jugador
		    double currentAngle = toPlayer.getAngle();
		    
		    currentAngle += Math.random()*Constants.UFO_ANGLE_RANGE - Constants.UFO_ANGLE_RANGE/2;
		    
		    if(toPlayer.getX()<0) {
		    	currentAngle = -currentAngle + Math.PI;
		    }
		    
		    toPlayer = toPlayer.setDirection(currentAngle);
		    
		    Laser laser = new Laser(
		        getCenter().add(toPlayer.scale(alturatx)),
		        toPlayer,
		        Constants.LASER_VEL,
		        currentAngle + Math.PI/2,
		        Assets.redLaser,
		        gameState,
		        Constants.UFO_SCALE
		    );
		    gameState.getMovingObjects().add(0, laser);
		    
		    fireRate.run(Constants.UFO_FIRE_RATE);
		    shoot.changeVolumen(Constants.VOULMEN_LASER);
		    shoot.play();
		}

		angle += 0.05;
		
		collidesWith();
		fireRate.update();		
	}
	

	@Override
	public void Destroy() {
		explosionNave.changeVolumen(Constants.VOLUMEN_NAVE_EXPLOSION);
		explosionNave.playFromPosition(15000);
		gameState.addScore(Constants.UFO_SCORE,position);
		super.Destroy();
	}
	
	
	
	@Override
	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		at = AffineTransform.getTranslateInstance(position.getX(), position.getY());
		at.rotate(angle, anchotx/2, alturatx/2);
		g2d.drawImage(texture, at, null);
		
		
	}

}
