package com.binarygames.spaceboi.gameobjects.entities;

import com.badlogic.gdx.math.MathUtils;
import com.binarygames.spaceboi.gameobjects.GameWorld;

public class LaunchPad extends EntityStatic {

    public static final int LAUNCH_PAD_SPEED = 60;

    private boolean active = true;

    public LaunchPad(GameWorld gameWorld, float x, float y, String path, float mass, float width, float height, float angle) {
        super(gameWorld, x, y, path, mass, width, height);
        body.setTransform(body.getPosition(), angle + MathUtils.PI / 2);
        getSprite().setRotation(body.getAngle() * MathUtils.radiansToDegrees);
    }

    public void launchEntity(EntityDynamic entity) {

    }

    private void getOppositePlanet() {

    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
