package com.binarygames.spaceboi.gameobjects.entities;

import com.binarygames.spaceboi.gameobjects.GameWorld;
import com.badlogic.gdx.Gdx;

public class LaunchPad extends EntityStatic {

    public static final int LAUNCH_PAD_SPEED = 60;

    public LaunchPad(GameWorld gameWorld, float x, float y, String path, float mass, float width, float height) {
        super(gameWorld, x, y, path, mass, width, height);
        getSprite().rotate(90);
        body.setTransform(body.getPosition(), 90);
    }

    public void launchEntity(EntityDynamic entity) {

    }

    private void getOppositePlanet() {

    }

}
