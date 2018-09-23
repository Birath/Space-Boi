package com.binarygames.spaceboi.gameobjects.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.binarygames.spaceboi.Assets;
import com.binarygames.spaceboi.SpaceBoi;

import static com.binarygames.spaceboi.gameobjects.bodies.BaseBody.PPM;

public class Background {

    private Sprite background;

    private SpaceBoi game;
    private Player player;

    /*
        TODO:
        -Parallax
        -More Background Images
        -Larger resolution
     */

    public Background(SpaceBoi game, Player player) {
        this.game = game;
        this.player = player;

        background = new Sprite(game.getAssetManager().get(Assets.WORLD_BACKGROUND, Texture.class));
        background.setPosition(player.getBody().getPosition().x * PPM - background.getWidth() / 2, player.getBody().getPosition().y * PPM - background.getHeight() / 2);
    }

    public void render(SpriteBatch batch) {
        background.setOrigin(background.getWidth() / 2, background.getHeight() / 2);
        background.setRotation((float) Math.toDegrees(player.getRad()));
        background.draw(batch);
    }

}
