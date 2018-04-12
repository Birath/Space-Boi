package com.binarygames.spaceboi.gameobjects.bodies;

import com.badlogic.gdx.physics.box2d.World;

public abstract class BaseStaticBody extends BaseBody {
    public BaseStaticBody(World aWorld, float x, float y, float mass, float radius) {
        super(aWorld, x, y, mass, radius);
    }

    public float getVolume() {
        return (float) ((4 * Math.PI * Math.pow(rad, 3)) / 3);
    }

    protected abstract int getDensity();

}
