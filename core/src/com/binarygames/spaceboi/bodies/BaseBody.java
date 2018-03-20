package com.binarygames.spaceboi.bodies;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public abstract class BaseBody {
    protected float mass;
    protected Vector2 pos;
    protected World world;
    protected float rad;

    public static final float WORLD_TO_BOX = 0.03125f;
    public static final float BOX_TO_WORLD = 32f;
    public static final double CONSTANT = 6.674 * Math.pow(10, -11);

    public BaseBody(World aWorld, float x, float y, float mass) {
        this.world = aWorld;
        System.out.println("BaseRad: " + rad);
        this.rad = 100 * WORLD_TO_BOX;
        this.mass = mass;
        this.pos = new Vector2(x * WORLD_TO_BOX, y * WORLD_TO_BOX);
    }

    public float getMass() {
        return mass;
    }

    protected abstract Body getBody();
}
