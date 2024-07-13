package graphics;
import java.awt.image.BufferedImage;

public class Assets {
	
	//nave,enemigo y laser
	public static BufferedImage player,speed,blueLaser,greenLaser,redLaser,ufo;
	
	//naves
	public static BufferedImage[]bigs = new BufferedImage[4];
	public static BufferedImage[]meds = new BufferedImage[2];
	public static BufferedImage[]smalls=new BufferedImage[2];
	public static BufferedImage[]tinies=new BufferedImage[2];
	
	
	//explosion
	public static BufferedImage[]exp=new BufferedImage[9];
	
	public static void init()
	{
		player = Loader.ImageLoader("/ships/playerShip1_blue.png");
		speed = Loader.ImageLoader("/efectos/fire08.png");
		blueLaser = Loader.ImageLoader("/Lasers/laserBlue01.png");
		greenLaser = Loader.ImageLoader("/Lasers/laserGreen11.png");
		redLaser = Loader.ImageLoader("/Lasers/laserRed01.png");
		
		ufo = Loader.ImageLoader("/ships/enemy/ufoRed.png");
		
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
		
		if (player == null || speed == null) {
            System.out.println("Error al cargar la imagen del jugador");
        } else {
            System.out.println("Imagen del jugador cargada exitosamente");
        }
	}
	
}
