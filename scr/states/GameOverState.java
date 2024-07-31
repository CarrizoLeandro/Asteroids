package states;

import java.awt.Graphics;
import java.util.ArrayList;

import gameObject.Constants;
import graphics.Assets;
import graphics.Sounds;
import ui.Action;
import ui.Button;

public class GameOverState extends State{
	private ArrayList<Button> buttons;
    private Sounds gameOverMusic;

    public GameOverState() {
        
        buttons = new ArrayList<Button>();

        // Botón de reiniciar
        buttons.add(new Button(
            Assets.grayBotton,
            Assets.blueBotton,
            Constants.ANCHO / 2 - Assets.grayBotton.getWidth() / 2,
            Constants.ALTO / 2 - Assets.blueBotton.getHeight() / 2,
            "Reiniciar",
            new Action() {
                @Override
                public void doAction() {
    
                    State.changeState(new GameState());
                }
            }
        ));

        // Botón de salir
        buttons.add(new Button(
            Assets.grayBotton,
            Assets.blueBotton,
            Constants.ANCHO / 2 - Assets.grayBotton.getWidth() / 2,
            Constants.ALTO / 2 + Assets.blueBotton.getHeight(),
            Constants.EXIT,
            new Action() {
                @Override
                public void doAction() {
                    System.exit(0);
                }
            }
        ));
    }

    @Override
    public void update() {
        for (Button b : buttons) {
            b.update();
        }
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(Assets.background, 0, 0, Constants.ANCHO, Constants.ALTO, null);
        for (Button b : buttons) {
            b.draw(g);
        }
    }
}
