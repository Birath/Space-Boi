package com.binarygames.spaceboi.gameobjects.entities;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.binarygames.spaceboi.Assets;
import com.binarygames.spaceboi.SpaceBoi;

public class Background {

    private Sprite background;

    private SpaceBoi game;
    private Player player;

    private float movePercentage = 1f;

    private float startX;
    private float startY;

    private float currentX;
    private float currentY;

    /*
        TODO:
        -Parallax
        -More Background Images
        -Larger resolution
     */

    public Background(SpaceBoi game, Player player, OrthographicCamera camera) {
        this.game = game;
        this.player = player;

        startX = currentX = camera.position.x;
        startY = currentY = camera.position.y;

        background = new Sprite(game.getAssetManager().get(Assets.WORLD_BACKGROUND, Texture.class));
        background.setPosition(startX - background.getWidth() / 2, startY - background.getHeight() / 2);
        background.setOrigin(background.getWidth() / 2, background.getHeight() / 2);
    }

    public void render(SpriteBatch batch, OrthographicCamera camera) {
        float deltaX = camera.position.x - currentX;
        float deltaY = camera.position.y - currentY;

        currentX = currentX + deltaX * movePercentage;
        currentY = currentY + deltaY * movePercentage;

        background.setPosition(currentX - background.getWidth() / 2, currentY - background.getHeight() / 2);
        background.draw(batch);
    }

}
