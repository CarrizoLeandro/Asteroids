package states;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import gameObject.Constants;
import graphics.Assets;
import graphics.Sounds;
import ui.Action;
import ui.Button;

public class MenuState extends State{

	private ArrayList <Button> buttons;
	private Sounds menuMusic;
	public MenuState() {
		menuMusic= new Sounds(Assets.menuMusic);
		menuMusic.changeVolumen(Constants.VOLUMEN_FONDO);
		menuMusic.loop();
		buttons = new ArrayList<Button>();
		
		buttons.add(new Button (
				Assets.grayBotton,
				Assets.blueBotton,
				Constants.ANCHO/2 - Assets.grayBotton.getWidth()/2 + Constants.ANCHO/4,
				Constants.ALTO/2 - Assets.blueBotton.getHeight()/2 - Constants.ALTO/4,
				Constants.PLAY,
				new Action() {
					@Override
					public void doAction() {
						menuMusic.stop();
						State.changeState(new GameState());
					}}
				));
		buttons.add(new Button (
				Assets.grayBotton,
				Assets.blueBotton,
				Constants.ANCHO/2 - Assets.grayBotton.getWidth()/2 + Constants.ANCHO/4,
				Constants.ALTO/4 - Assets.blueBotton.getHeight()/2 + Constants.ALTO/2,
				Constants.EXIT,
				new Action() {
					@Override
					public void doAction() {
						System.exit(0);
					}}
				));
		buttons.add(new Button (
				Assets.grayBotton,
				Assets.blueBotton,
				Constants.ANCHO/2 - Assets.grayBotton.getWidth()/2 + Constants.ANCHO/4,
				Constants.ALTO/2 - Assets.blueBotton.getHeight()/2,
				"Cambiar color",
				new Action() {
					@Override
					public void doAction() {
						GameState.changeNaveColor();
					}}
				));
	}
	
	@Override
	public void update() {
		for (Button b: buttons) {
			b.update();
		}
		
	}

	@Override
	public void draw(Graphics g) {
	    g.drawImage(Assets.background, 0, 0, Constants.ANCHO,Constants.ALTO, null);
		for (Button b: buttons) {
			b.draw(g);
		}
		ArrayList<BufferedImage> naveImage =  Assets.getNaveColorIndex();
		int naveIndex= GameState.getNaveColor();
		if (naveIndex >= 0 && naveIndex <= naveImage.size()) {
			
            g.drawImage(naveImage.get(naveIndex), (Constants.ANCHO / 4)-100,( Constants.ALTO / 2)-100,200,200, null);
        }
		
	}

}
