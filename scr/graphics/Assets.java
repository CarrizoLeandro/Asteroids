package graphics;
import java.awt.image.BufferedImage;

public class Assets {
	
	public static BufferedImage player,speed,blueLaser,greenLaser,redLaser;
		
	public static void init()
	{
		player = Loader.ImageLoader("/ships/playerShip1_blue.png");
		speed = Loader.ImageLoader("/efectos/fire08.png");
		blueLaser = Loader.ImageLoader("/Lasers/laserBlue01.png");
		greenLaser = Loader.ImageLoader("/Lasers/laserGreen11.png");
		redLaser = Loader.ImageLoader("/Lasers/laserRed01.png");
		
		if (player == null || speed == null) {
            System.out.println("Error al cargar la imagen del jugador");
        } else {
            System.out.println("Imagen del jugador cargada exitosamente");
        }
	}
	
}
