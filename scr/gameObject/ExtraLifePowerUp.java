package gameObject;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import math.Vector2D;
import states.GameState;

public class ExtraLifePowerUp extends PowerUp {

    public ExtraLifePowerUp(Vector2D position, Vector2D velocity, double maxVel, BufferedImage texture, GameState gameState, double scale) {
        super(position, velocity, maxVel, texture, gameState, scale);
    }

    @Override
    public void applyEffect(GameState gameState) {
        gameState.addLife();
        this.Destroy();
    }

    @Override
    public void applyEffect(Player player) {
        player.addLife();
    }


}
