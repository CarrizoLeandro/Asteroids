package graphics;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.sound.sampled.Clip;

public class Assets {
	
	//Fondo de pantalla
	public static BufferedImage background;
	
	//nave,enemigo y laser
	public static BufferedImage player,speed,blueLaser,greenLaser,redLaser,ufo;
	public static ArrayList<BufferedImage> naveColorIndex = new ArrayList<>();
	
	//powerUps
	public static BufferedImage extraLife;
	public static BufferedImage extraLaser;
	
	
	//naves
	public static BufferedImage[]bigs = new BufferedImage[4];
	public static BufferedImage[]meds = new BufferedImage[2];
	public static BufferedImage[]smalls=new BufferedImage[2];
	public static BufferedImage[]tinies=new BufferedImage[2];
	
	//Interface
	public static BufferedImage[]numbers=new BufferedImage[11];
	public static BufferedImage life;
	public static BufferedImage grayBotton,blueBotton;
	
	
	
	//explosion
	public static BufferedImage[]exp=new BufferedImage[9];
	
	//fonts
	public static Font fontBig;
	public static Font fontMed;
	
	//sounds
	public static Clip backgroundMusic, explosionMeteor,explosionNave, playerLose, playerShoot, ufoShoot,menuMusic;
	public static void init()
	{
		player = Loader.ImageLoader("/ships/playerShip1.png");
		speed = Loader.ImageLoader("/efectos/fire08.png");
		blueLaser = Loader.ImageLoader("/Lasers/laserBlue01.png");
		greenLaser = Loader.ImageLoader("/Lasers/laserGreen11.png");
		redLaser = Loader.ImageLoader("/Lasers/laserRed01.png");
		
		ufo = Loader.ImageLoader("/ships/enemy/ufoRed.png");
		life = Loader.ImageLoader("/interface/playerLife1_blue.png");
		
		fontBig = Loader.loadFont("/fonts/kenvector_future.ttf", 42);
		fontMed = Loader.loadFont("/fonts/kenvector_future.ttf", 20);
		
		grayBotton = Loader.ImageLoader("/UI/button_gray_rectangle_gradient.png");
		blueBotton = Loader.ImageLoader("/UI/button-blue_rectangle_gradient.png");
		
		background = Loader.ImageLoader("/background.jpg");
		
		
		extraLife =Loader.ImageLoader("/powerUp/pill_green.png");
		extraLaser =Loader.ImageLoader("/powerUp/bolt_bronze.png");
		for (int i = 1 ; i<= 12 ; i++) {
			naveColorIndex.add(Loader.ImageLoader("/ships/playerShip" + i + ".png"));
		}
		for (int i = 0;i<bigs.length;i++) {
			bigs[i] = Loader.ImageLoader("/meteors/meteorBrown_big"+(i+1)+".png");
		}
		for (int i = 0;i<meds.length;i++) {
			meds[i] = Loader.ImageLoader("/meteors/meteorBrown_med"+(i+1)+".png");
		}
		for (int i = 0;i<smalls.length;i++) {
			smalls[i] = Loader.ImageLoader("/meteors/meteorBrown_small"+(i+1)+".png");
		}
		for (int i = 0;i<tinies.length;i++) {
			tinies[i] = Loader.ImageLoader("/meteors/meteorBrown_tiny"+(i+1)+".png");
		}
		
		for (int i = 0 ; i < exp.length ; i++ ) {
			exp[i] = Loader.ImageLoader("/explosion/"+i+".png");
		}
		for(int i = 0; i < numbers.length; i++)
			numbers[i] = Loader.ImageLoader("/interface/numeral"+i+".png");
		
		backgroundMusic = Loader.loadSound("/sounds/2018-09-07-52675.wav");
		playerShoot = Loader.loadSound("/sounds/laser-gun-shot-sound-future-sci-fi-lazer-wobble-chakongaudio-174883.wav");
		ufoShoot = Loader.loadSound("/sounds/laser-gun-81720.wav");
		explosionNave=Loader.loadSound("/sounds/explosion-luna-102514.wav");
		explosionMeteor=Loader.loadSound("/sounds/small-rock-break-194553.wav");
		menuMusic= Loader.loadSound("/sounds/gamemusic-6082.wav");
		
		if (player == null || speed == null) {
            System.out.println("Error al cargar la imagen del jugador");
        } else {
            System.out.println("Imagen del jugador cargada exitosamente");
        }
	}
	
	public static ArrayList<BufferedImage> getNaveColorIndex() {
		return naveColorIndex;
	}

	
}
