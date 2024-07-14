package graphics;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;


public class Loader {
    
    public static BufferedImage ImageLoader(String path) {
        try {
            System.out.println("Intentando cargar el recurso: " + path);
            InputStream is = Loader.class.getResourceAsStream(path);
            if (is == null) {
                System.out.println("El recurso no fue encontrado: " + path);
                throw new IOException("Recurso no encontrado: " + path);
            }
            return ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static Font loadFont(String path,int size) {
    	try {
			return Font.createFont(Font.TRUETYPE_FONT, Loader.class.getResourceAsStream(path)).deriveFont(Font.PLAIN,size);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
			return null;
		}
    }
    
}
