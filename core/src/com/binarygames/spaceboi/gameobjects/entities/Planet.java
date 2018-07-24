package com.binarygames.spaceboi.gameobjects.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.binarygames.spaceboi.gameobjects.GameWorld;


public class Planet extends EntityStatic {

    private float radius;

    private ShapeRenderer shapeRenderer;
    public static final int GRAVITY_RADIUS = 2;

    public Planet(GameWorld gameWorld, float x, float y, String path, float mass, float radius) {
        super(gameWorld, x, y, path, mass, radius);
        this.radius = radius;

        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void render(SpriteBatch batch, OrthographicCamera camera) {
        getSprite().setPosition(body.getPosition().x * PPM - getSprite().getWidth() / 2, body.getPosition().y * PPM - getSprite().getHeight() / 2);
        getSprite().draw(batch);

        shapeRenderer.setAutoShapeType(true);
        shapeRenderer.setColor(Color.LIGHT_GRAY);
        shapeRenderer.setProjectionMatrix(camera.combined);
        batch.end();
        shapeRenderer.begin();
        shapeRenderer.circle(body.getPosition().x * PPM, body.getPosition().y * PPM, GRAVITY_RADIUS * rad * PPM);
        shapeRenderer.end();
        batch.begin();
    }

    public float getRadius() {
        return radius;
    }

}
