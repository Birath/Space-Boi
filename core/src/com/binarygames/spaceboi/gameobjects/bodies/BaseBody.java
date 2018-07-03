package com.binarygames.spaceboi.gameobjects.bodies;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.binarygames.spaceboi.gameobjects.GameWorld;

public abstract class BaseBody {

    protected Body body;

    protected float mass;
    protected Vector2 pos;
    protected GameWorld gameWorld;
    protected float rad;

    public static final int PPM = 10;

    public BaseBody(GameWorld gameWorld, float x, float y, float mass, float radius) { //Just nu tar vi in mass som argument. Tror dock att vi vill ha konstanter eller skala med volym och densitet.
        this.gameWorld = gameWorld;
        this.rad = radius / PPM;
        this.mass = mass;
        this.pos = new Vector2(x / PPM, y / PPM);
    }

    public abstract void render(SpriteBatch batch, OrthographicCamera camera);

    public float getMass() {
        return mass;
    }

    public float getRad() {
        return rad;
    }

    public Body getBody() {
        return body;
    }
}
