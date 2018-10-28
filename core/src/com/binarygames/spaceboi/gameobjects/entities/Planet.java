package com.binarygames.spaceboi.gameobjects.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.binarygames.spaceboi.gameobjects.GameWorld;

import java.util.ArrayList;
import java.util.List;


public class Planet extends EntityStatic {

    private float radius;
    private List<LaunchPad> launchPads = new ArrayList<>();
    private boolean launchPadActive = true;

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
        shapeRenderer.setProjectionMatrix(camera.combined);
        batch.end();
        shapeRenderer.begin();

        Color fadeColor = new Color(0, 102, 204, 0);
        int lineWidth = 2;
        Gdx.gl.glLineWidth(lineWidth);
        int planetBarrierTransitions = 8;
        float currentRad = GRAVITY_RADIUS * rad * PPM;
        shapeRenderer.setColor(fadeColor);

        Gdx.gl.glEnable(GL20.GL_BLEND);
        for (int i = 0; i < planetBarrierTransitions; i++) {
            shapeRenderer.circle(body.getPosition().x * PPM, body.getPosition().y * PPM, currentRad, (int) (32 * (float) Math.cbrt(radius)));
            currentRad -= lineWidth / 2;
            fadeColor.a += 255 / planetBarrierTransitions;
            shapeRenderer.setColor(fadeColor);
        }

        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
        batch.begin();
    }

    public float getRadius() {
        return radius;
    }

    public void addLaunchPad(LaunchPad launchPad) {
        launchPads.add(launchPad);
    }

    public void setLaunchPadActive(boolean launchPadsActive) {
        if (this.launchPadActive != launchPadsActive) {
            for (LaunchPad launchPad : launchPads) {
                launchPad.setActive(launchPadsActive);
            }
        }
        this.launchPadActive = launchPadsActive;
    }
}
