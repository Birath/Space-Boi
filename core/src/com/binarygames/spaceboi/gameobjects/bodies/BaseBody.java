package com.binarygames.spaceboi.gameobjects.bodies;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Shape.Type;
import com.binarygames.spaceboi.gameobjects.GameWorld;

public abstract class BaseBody {

    protected Body body;

    protected float mass;
    protected Vector2 pos;
    protected GameWorld gameWorld;
    protected float rad;

    protected float width;
    protected float height;

    public Type bodyShape;

    public static final int PPM = 10;

    public BaseBody(GameWorld gameWorld, float x, float y, float mass, float radius, BodyType bodyType) { //Just nu tar vi in mass som argument. Tror dock att vi vill ha konstanter eller skala med volym och densitet.
        this.rad = radius / PPM;
        bodyShape = Type.Circle;
        createBody(gameWorld, mass, x, y, bodyType);
    }

    public BaseBody(GameWorld gameWorld, float x, float y, float mass, float width, float height, BodyType bodyType) {
        this.width = width / PPM;
        this.height = height / PPM;
        bodyShape = Type.Polygon;
        createBody(gameWorld, mass, x, y, bodyType);
    }

    private void createBody(GameWorld gameWorld, float mass, float x, float y, BodyType bodyType) {
        this.gameWorld = gameWorld;
        this.mass = mass;
        this.pos = new Vector2(x / PPM, y / PPM);

        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.type = bodyType;
        groundBodyDef.position.set(pos);
        body = gameWorld.getWorld().createBody(groundBodyDef);
        switch (bodyShape) {
            case Circle:
                CircleShape circleShape = new CircleShape();
                circleShape.setRadius(rad);
                addFixture(circleShape);
                break;
            case Edge:
                break;
            case Polygon:
                PolygonShape polygonShape = new PolygonShape();
                polygonShape.setAsBox(width / 2, height / 2);
                addFixture(polygonShape);
                break;
            case Chain:
                break;
        }

    }

    private void addFixture(Shape shape) {
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.2f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0f;
        Fixture fixture = body.createFixture(fixtureDef);
        shape.dispose();
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

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
}
