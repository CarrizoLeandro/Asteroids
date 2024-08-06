package gameObject;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import graphics.Assets;
import graphics.Sounds;
import input.KeyBoard;
import math.Vector2D;
import states.GameOverState;
import states.GameState;
import states.State;

public class Player extends MovingObject {

	private Vector2D heading;
	private Vector2D acceleration;
	private boolean aceleracion=false;
	private Chronometer fireRate;
	
	private boolean spawning,visible;
	private Chronometer spawnTime,flickerTime;
	
	private Sounds shoot;
	private Sounds explosionNave;
	private int extraLaserCount = 0;
	private final int MAX_EXTRA_LASERS = 2;
	private int extraShieldCount = 0;
	private final int maxExtraShield = 2;
	boolean shieldActive=false;


	public Player(Vector2D position, Vector2D velocity, double maxVel , BufferedImage texture,GameState gameState,double scale) {
		super(position, velocity, maxVel, texture, gameState,scale);
		heading=new Vector2D(0,1);
		acceleration=new Vector2D();
		fireRate= new Chronometer();
		spawnTime = new Chronometer();
	    flickerTime = new Chronometer();
	    spawning = false;
	    visible = true;
	    shoot = new Sounds(Assets.playerShoot);
	    explosionNave = new Sounds(Assets.explosionNave);
	}
	
	@Override
    public double getCollisionRadius() {
	    return collisionRadius * Constants.PlAYER_SCALE;

    }
	
	public boolean collidesWith(MovingObject other) {
	    double distance = getCenter().subtract(other.getCenter()).getMagnitude();
	    double combinedRadius = getCollisionRadius() + other.getCollisionRadius();

	    if (distance < combinedRadius) {
	        if (other instanceof PowerUp) {
	            PowerUp powerUp = (PowerUp) other;
	            if (!powerUp.isCollected()) {
	                powerUp.applyEffect(this);
	            }
	            return false; 
	        }
	        return true;
	    }
	    return false;
	}



	@Override
	public void update() {
		
		if (!spawnTime.isRunning()) {
			spawning = false;
			visible = true;
		}
		if (spawning) {
			if (!flickerTime.isRunning()) {
				flickerTime.run(Constants.FLICKER_TIME);
				visible = !visible;
			}
		}

		if (KeyBoard.SHOOT && !fireRate.isRunning() && !spawning) {
            if (extraLaserCount == 0) {
                shootSingleLaser();
            } else if (extraLaserCount == 1) {
                shootDoubleLaser();
            } else if (extraLaserCount == 2) {
                shootTripleLaser();
            }
            fireRate.run(Constants.FIRERATE);
            shoot.changeVolumen(Constants.VOULMEN_LASER);
            shoot.play();
        }
		

		
		if (shieldActive==false) {
			if (extraShieldCount == 1) {
				System.out.println("Shield: "+extraShieldCount+"a");
				drawShield();
				shieldActive=true;
	        } else if(extraShieldCount == 2){
	        	System.out.println("Shield: "+extraShieldCount+"b");
				drawShield();
				shieldActive=true;
	        }else if(extraShieldCount == 3) {
	        	System.out.println("Shield: "+extraShieldCount+"c");
				drawShield();
				shieldActive=true;
	        }
		}
		
	 
		if (shoot.getFramePosition() > 10000) {
			shoot.stop();
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
		spawnTime.update();
		flickerTime.update();
		collidesWith();
		
	}
	
	@Override
	public void Destroy() {
		if(gameState.getLives() >= 2) {
			spawning = true;
			explosionNave.playFromPosition(15000);
			explosionNave.changeVolumen(Constants.VOLUMEN_NAVE_EXPLOSION);
			spawnTime.run(Constants.SPAWNING_TIME);
			resetValues();
			gameState.subtractLife();
		} else {
			gameState.subtractLife();
			
			State.changeState(new GameOverState());
		}
	}
	
	private void resetValues(){
		angle=0;
		velocity=new Vector2D();
		position =new Vector2D(Constants.INICIAL_PLAYER_POSX,Constants.INICIAL_PLAYER_POSY);
		extraLaserCount=0;
	}
	
	@Override
	public void draw(Graphics g) {
		
		if (!visible) {
			return;
		}
		
		Graphics2D g2d = (Graphics2D)g;
		
		AffineTransform at1= AffineTransform.getTranslateInstance(position.getX()+ anchotx*Constants.PlAYER_SCALE/2 + 20*Constants.PlAYER_SCALE, position.getY() + alturatx*Constants.PlAYER_SCALE/2+22*Constants.PlAYER_SCALE);
		at1.scale(Constants.PlAYER_SCALE, Constants.PlAYER_SCALE);
		AffineTransform at2= AffineTransform.getTranslateInstance(position.getX()+ anchotx*Constants.PlAYER_SCALE/2 - 35*Constants.PlAYER_SCALE, position.getY()+alturatx*Constants.PlAYER_SCALE/2 + 22*Constants.PlAYER_SCALE);
		at2.scale(Constants.PlAYER_SCALE, Constants.PlAYER_SCALE);
		
		at1.rotate(angle,-20,-22);
		at2.rotate(angle,+35,-22);
		
		if(aceleracion) {
			g2d.drawImage(Assets.speed, at1,null);
			g2d.drawImage(Assets.speed, at2,null);
		}
		
		
		
		at= AffineTransform.getTranslateInstance(position.getX(), position.getY());
		at.scale(Constants.PlAYER_SCALE, Constants.PlAYER_SCALE);
		at.rotate(angle,anchotx/2,alturatx/2);
		g2d.drawImage(texture,  at, null);
	}
	
	public boolean isSpawning() {
		return spawning;
	}

	public void addLife() {
	    gameState.addLife();
	}
	
	private void drawShield() {
		gameState.getMovingObjects().add(0, new ExtraShield(
				new Vector2D(Constants.INICIAL_PLAYER_POSX,Constants.INICIAL_PLAYER_POSY),
				new Vector2D(0, 0),
	            0,
	            Assets.shield3,
	            gameState,
	            1.0
	        ));
	}
	
	private void shootSingleLaser() {
        gameState.getMovingObjects().add(0, new Laser(
            getCenter().add(heading.scale(anchotx)),
            heading,
            10,
            angle,
            Assets.redLaser,
            gameState,
            Constants.LASER_SCALE
        ));
    }

    private void shootDoubleLaser() {
        double offsetDistance = 40 * Constants.PlAYER_SCALE;
        Vector2D offset1 = new Vector2D(offsetDistance, -30).rotate(angle);
        Vector2D offset2 = new Vector2D(-offsetDistance, -30).rotate(angle);
        gameState.getMovingObjects().add(0, new Laser(
            getCenter().add(offset1),
            heading,
            10,
            angle,
            Assets.redLaser,
            gameState,
            Constants.LASER_SCALE
        ));
        gameState.getMovingObjects().add(0, new Laser(
            getCenter().add(offset2),
            heading,
            10,
            angle,
            Assets.redLaser,
            gameState,
            Constants.LASER_SCALE
        ));
    }

    private void shootTripleLaser() {
        shootSingleLaser();
        shootDoubleLaser();
    }
	
	public void incrementExtraLaser() {
	    if (extraLaserCount < MAX_EXTRA_LASERS) {
	    	if(extraLaserCount >=2) {
	    		extraLaserCount=2;
	    	}else {
	    		extraLaserCount++;
	    	}
	    	
	        
	    }
	}
	
	public void incrementExtraShield() {
	    if (extraShieldCount < maxExtraShield) {
	        extraShieldCount++;
	        shieldActive = false;

	    } else {
	        extraShieldCount = maxExtraShield;
	    }
	}


}
