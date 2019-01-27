package com.binarygames.spaceboi.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.binarygames.spaceboi.gameobjects.entities.Player;

public class CompassArrow extends Image {

    private Player player;

    public CompassArrow(Texture texture, Player player, float x, float y) {
        super(texture);
        this.setPosition(x, y);
        this.setOrigin(Align.center);
        this.player = player;
    }

    @Override
    public void act(float delta) {
        this.setRotation(player.getBody().getPosition().nor().angle() - player.getPlayerAngle());
    }
}
