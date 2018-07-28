package com.binarygames.spaceboi.gameobjects.entities;

import com.binarygames.spaceboi.gameobjects.GameWorld;
import com.badlogic.gdx.Gdx;

public class LaunchPad extends EntityStatic {

    public static final int LAUNCH_PAD_SPEED = 60;

    public LaunchPad(GameWorld gameWorld, float x, float y, String path, float mass, float width, float height, float angle) {
        super(gameWorld, x, y, path, mass, width, height);
        getSprite().rotate(angle + 30);
        body.setTransform(body.getPosition(), angle + 30);
    }

    public void launchEntity(EntityDynamic entity) {

    }

    private void getOppositePlanet() {

    }
}
