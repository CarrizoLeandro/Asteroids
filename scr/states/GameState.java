package states;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import gameObject.Constants;
import gameObject.Messege;
import gameObject.Meteor;
import gameObject.MovingObject;
import gameObject.Player;
import gameObject.Size;
import gameObject.Ufo;
import graphics.Animation;
import graphics.Assets;
import main.Windows;
import math.Vector2D;

public class GameState {
	
	private Player player;
	private ArrayList <MovingObject> movingObjects = new ArrayList<MovingObject>();
	private ArrayList <Animation> explosion = new ArrayList<Animation>();
	private ArrayList <Messege> messeges = new ArrayList<Messege>();
	
	private int score = 0;
	private int lives = 3;
	
	private int meteors;
	private int waves = 1;	
	
	public GameState() {
		player = new Player(new Vector2D(Constants.INICIAL_PLAYER_POSX,Constants.INICIAL_PLAYER_POSY),new Vector2D(),5,Assets.player, this,Constants.PlAYER_SCALE);
		movingObjects.add(player);
		
		meteors=1;
		
		startWave();
	
	}
	
	
	public void addScore(int value, Vector2D position) {
		score+= value;
		messeges.add(new Messege(position, true, "+"+value+" score", Color.WHITE, false, Assets.fontMed, this));
	}
	
	
	public void divideMeteor(Meteor meteor) {
		Size size = meteor.getSize();
		
		BufferedImage[] textures = size.textures;
		
		Size newSize = null;
		
		switch(size) {
		case BIG:
			newSize=Size.MED;
			break;
		case MED:
			newSize=Size.SMALL;
			break;
		case SMALL:
			newSize=Size.TINY;
			break;
		default:
			return;
		}
		
		for(int i =0 ; i < size.quantity;i++) {
			movingObjects.add(new Meteor(
					meteor.getPosition(),
					new Vector2D (0,1).setDirection(Math.random()*Math.PI*2),
					Constants.METEOR_VEL*(Math.random() * 2 + 1),
					textures[(int)(Math.random()*textures.length)],
					this,
					newSize,
					Constants.METEOR_SCALE
					));
		}
		
		
	}
	
	private void startWave() {
		messeges.add(new Messege(new Vector2D(Constants.ANCHO/2,Constants.ALTO/2),true, "Wave " + waves, Color.WHITE, true, Assets.fontBig, this));
		
		for(int i =0; i < meteors ; i ++) {
			Vector2D position = generateRandomPosition();
			
			BufferedImage texture = Assets.bigs[(int)(Math.random()*Assets.bigs.length)];
			
			movingObjects.add(new Meteor(
					position,
					new Vector2D (0,1).setDirection(Math.random()*Math.PI*2),
					Constants.METEOR_VEL*Math.random()+1,
					texture,
					this,
					Size.BIG,
					Constants.METEOR_SCALE
					));
			
		}
		meteors++;
		waves++;
		spawnUfo();
	}
	
	public void playExplosion(Vector2D position) {
		explosion.add(new Animation(
				Assets.exp,
				50,
				position.subtract(new Vector2D(Assets.exp[0].getWidth()/2,Assets.exp[0].getHeight()/2))
				));
	}
	
	private boolean isPositionValid(Vector2D position, double minDistance) {
	    for (MovingObject obj : movingObjects) {
	        if (obj != null && obj.getPosition().subtract(position).getMagnitude() < minDistance) {
	            return false;
	        }
	    }
	    return true;
	}
	
	private Vector2D generateRandomPosition() {
	    boolean validPosition = false;
	    int attempts = 0;
	    Vector2D position = new Vector2D(0, 0);

	    while (!validPosition && attempts < 100) {
	        int rand = (int) (Math.random() * 2);

	        double x = rand == 0 ? (Math.random() * Constants.ANCHO) : 0;
	        double y = rand == 0 ? 0 : (Math.random() * Constants.ALTO);

	        position = new Vector2D(x, y);
	        validPosition = isPositionValid(position, 200); // 100 es la distancia mínima entre objetos
	        attempts++;
	    }

	    return position;
	}
	
	private void spawnUfo() {
		Vector2D position = generateRandomPosition();
		
		ArrayList<Vector2D> path = new ArrayList<Vector2D>();
		
		double posX,posY;
		
		posX= Math.random()*Constants.ANCHO/2;
		posY= Math.random()*Constants.ALTO/2;
		path.add(new Vector2D(posX,posY));
		
		posX=Math.random()*(Constants.ANCHO/2) + Constants.ANCHO/2;
		posY=Math.random()*Constants.ALTO/2;
		path.add(new Vector2D(posX,posY));
		
		posX=Math.random()*Constants.ANCHO/2;
		posY=Math.random()*(Constants.ALTO/2) + Constants.ALTO/2;
		path.add(new Vector2D(posX,posY));
		
		posX=Math.random()*(Constants.ANCHO/2) + Constants.ANCHO/2;
		posY=Math.random()*(Constants.ALTO/2) + Constants.ALTO/2;
		path.add(new Vector2D(posX,posY));
		
		movingObjects.add(new Ufo(
				position,
				new Vector2D(),
				Constants.UFO_MAX_VEL,
				Assets.ufo,
				path,
				this,
				Constants.UFO_SCALE
				));
		
	}
	
	
	
	public void update() {
		for(int i =0; i < movingObjects.size();i++) {
			movingObjects.get(i).update();
		}
		
		for(int i =0; i < explosion.size();i++) {
			Animation anim = explosion.get(i);
			anim.update();
			if (!anim.isRunning()) {
				explosion.remove(i);
			}
		}
		
		for(int i =0; i < movingObjects.size();i++) {
			if (movingObjects.get(i)instanceof Meteor) {
				return;
			}
		}
		
		
		startWave();
	}
	public void drawFPS(Graphics g) {
	    g.setColor(Color.WHITE);
	    g.drawString("FPS actuales: " + Windows.AVARAGEFPS, 20, 650);
	}
	public void draw(Graphics g) {
		drawFPS(g);
		Graphics2D g2d = (Graphics2D)g;
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		
		for(int i = 0; i < messeges.size();i++) {
			messeges.get(i).draw(g2d);
		}
		for(int i =0; i < movingObjects.size();i++) {
			movingObjects.get(i).draw(g);
		}
		
		for(int i =0; i < explosion.size();i++) {
			Animation anim = explosion.get(i);
			g2d.drawImage(anim.getCurrentFrame(),(int)anim.getPosition().getX(),(int) anim.getPosition().getY(),null);
			
		}
		drawScore(g);
		drawLives(g);
		 // Dibuja el contador de FPS
	}
	
	private void drawScore(Graphics g) {
		Vector2D pos = new Vector2D(1100, 25);
		
		String scoreToString = Integer.toString(score);
		
		for(int i = 0; i < scoreToString.length(); i++) {
			
			g.drawImage(Assets.numbers[Integer.parseInt(scoreToString.substring(i, i + 1))],
					(int)pos.getX(), (int)pos.getY(), null);
			pos.setX(pos.getX() + 20);
			
		}
	}
	
	private void drawLives(Graphics g) {
		Vector2D livePosition = new Vector2D(25,25);
		g.drawImage(Assets.life,(int)livePosition.getX(),(int)livePosition.getY(), null);
		g.drawImage(Assets.numbers[10],(int)livePosition.getX()+40,(int)livePosition.getY()+5,null);
		String livesToString = Integer.toString(lives);
		Vector2D pos = new Vector2D(livePosition.getX(), livePosition.getY());
		
		for(int i = 0; i < livesToString.length(); i ++)
		{
			int number = Integer.parseInt(livesToString.substring(i, i+1));
			
			if(number <= 0)
				break;
			g.drawImage(Assets.numbers[number],
					(int)pos.getX() + 60, (int)pos.getY() + 5, null);
			pos.setX(pos.getX() + 20);
		}
		
	}
	
	
	public ArrayList<MovingObject> getMovingObjects() {
		return movingObjects;
	}
	
	public ArrayList<Messege> getMesseges(){
		return messeges;
	}
	
	public Player getPlayer() {
		return player;
	}
	public void subtractLife() {
		lives --;
	}
}
