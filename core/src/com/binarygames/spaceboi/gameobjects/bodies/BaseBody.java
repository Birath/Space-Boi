package com.binarygames.spaceboi.gameobjects.bodies;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public abstract class BaseBody {

    protected float mass;
    protected Vector2 pos;
    protected World world;
    protected float rad;

    public static final float WORLD_TO_BOX = 0.05f;
    public static final float BOX_TO_WORLD = 32f;

    public BaseBody(World aWorld, float x, float y, float mass, float radius) { //Just nu tar vi in mass som argument. Tror dock att vi vill ha konstanter eller skala med volym och densitet.
        this.world = aWorld;
        this.rad = radius;
        this.mass = mass;
        this.pos = new Vector2(x , y);
    }

    public abstract void render(SpriteBatch batch, OrthographicCamera camera);

    public float getMass() {
        return mass;
    }

    public float getRad() {
        return rad;
    }

    protected abstract Body getBody();
}
