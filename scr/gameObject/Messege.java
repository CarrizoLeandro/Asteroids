package gameObject;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import graphics.Text;
import math.Vector2D;
import states.GameState;

public class Messege {
	private GameState gameState;
	private float alpha;
	private String text;
	private Vector2D position;
	private Color color;
	private boolean center;

	private String effectType;
	private Font font;
	private final float deltaAlpha= 0.01f;
	
	public Messege(Vector2D position, String text, Color color, boolean center, Font font, GameState gameState,String effectType) {
		this.font = font;
		this.gameState = gameState;
		this.text = text;
		this.position = position;
		this.color = color;
		this.center = center;
		this.effectType = effectType;
		
		alpha=1;
	}
	public void draw(Graphics2D g2d) {
		
		 switch (effectType) {
         case "fade":
             fadeEffect(g2d);
             break;
         case "noFade":
             noFadeEffect(g2d);
             break;
         default:
             Text.drawText(g2d, text, position, center, color, font);
             break;
		 	}
	}
	
	private void fadeEffect(Graphics2D g2d) {
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        Text.drawText(g2d, text, position, center, color, font);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));

        position.setY(position.getY() - 1);
        alpha -= deltaAlpha;

        if (alpha < 0) {
            gameState.getMesseges().remove(this);
        }
    }
	
	
	 private void noFadeEffect(Graphics2D g2d) {
	        Text.drawText(g2d, text, position, center, color, font);
	    }
	 

}
