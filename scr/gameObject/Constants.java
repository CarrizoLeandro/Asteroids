package gameObject;

import graphics.Assets;

public class Constants {
	
	
	public static final double METEOR_SCALE = 1.5, PlAYER_SCALE=0.8;  
    public static final double UFO_SCALE = 1.0, LASER_SCALE=1.0;     
	
	
	// frame dimensions
	public static final int ANCHO=1200,ALTO=700;	
	//player properties
	public static final int INICIAL_PLAYER_POSX=ANCHO/2-Assets.player.getWidth()/2;
	public static final int INICIAL_PLAYER_POSY=ALTO/2-Assets.player.getHeight()/2;
	public static final int FIRERATE=300;
	public static final double DELTAANGLE=0.1;
	public static final double ACC=0.2;
	public static final double PLAYER_MAX_VEL =7.0;
	public static final long SPAWNING_TIME=3000;
	public static final long FLICKER_TIME=200;
	
	//Laser properties
	public static final double LASER_VEL=15.0;
	
	//Meteors Properties
	public static final double METEOR_VEL=0.5;
	public static final int METEOR_SCORE=20;
	
	//Ufo properties
	public static final int NODE_RADIUS = 160;
	public static final double UFO_MASS = 60;
	public static final int UFO_MAX_VEL=3;
	public static final long UFO_FIRE_RATE=1000;
	public static final double UFO_ANGLE_RANGE=Math.PI/2;
	public static final int UFO_SCORE = 40;
}
