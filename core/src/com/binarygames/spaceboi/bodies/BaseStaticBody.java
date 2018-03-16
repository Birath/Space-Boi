package com.binarygames.spaceboi.bodies;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public abstract class BaseStaticBody {
    protected float mass;
    protected Vector2 pos;
    protected World world;
    protected float rad;

    public static final float WORLD_TO_BOX = 0.03125f;
    public static final float BOX_TO_WORLD = 32f;
    public static final double CONSTANT = 6.674 * Math.pow(10, -11);

    public BaseStaticBody(World aWorld, float x, float y, float radius) {
        this.world = aWorld;
        this.rad = radius * WORLD_TO_BOX;
        System.out.println("BaseRad: " + rad);
        this.mass = getVolume() * getDensity();
        this.pos = new Vector2(x * WORLD_TO_BOX, y * WORLD_TO_BOX);
    }

    public float getMass() {
        return mass;
    }

    public float getVolume() {
        return (float) ((4 * Math.PI * Math.pow(rad, 3)) / 3);
    }

    protected abstract Body getBody();
    protected abstract int getDensity();
}
