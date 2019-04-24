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

        if (isVisible(camera)) {
            shapeRenderer.setAutoShapeType(true);
            shapeRenderer.setProjectionMatrix(camera.combined);
            batch.end();
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

            float startAlpha = 0.4f;
            float endAlpha = 0.05f;
            Color fadeColor = new Color(36 / 255f, 124 / 255f, 182 / 255f, startAlpha);
            //Color fadeColor = new Color(Color.TEAL);
            int lineWidth = 4;
            Gdx.gl.glLineWidth(lineWidth);
            int planetBarrierTransitions = 100;
            float currentRad = GRAVITY_RADIUS * rad * PPM;
            shapeRenderer.setColor(fadeColor);

            float alphaDecrease = (startAlpha - endAlpha) / (planetBarrierTransitions); // TODO check black clircle line

            Gdx.gl.glEnable(GL20.GL_BLEND);
            for (int i = 0; i < planetBarrierTransitions; i++) {
                shapeRenderer.circle(body.getPosition().x * PPM, body.getPosition().y * PPM, currentRad, (int) (20 * (float) Math.cbrt(radius)));
                currentRad -= lineWidth / 2;
                fadeColor.a -= alphaDecrease; // TODO FINISH
                shapeRenderer.setColor(fadeColor);
            }

            shapeRenderer.end();

            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(fadeColor);
            shapeRenderer.circle(body.getPosition().x * PPM, body.getPosition().y * PPM, currentRad);
            shapeRenderer.end();

            Gdx.gl.glDisable(GL20.GL_BLEND);
            batch.begin();
        }
    }

    /**
     * Returns whether planet is visible or not. kinda
     *
     * @param camera
     * @return Whether planet is visible to camera
     */
    private boolean isVisible(OrthographicCamera camera) {
        //return gameWorld.getPlayer().getBody().getPosition().dst(body.getPosition()) < GRAVITY_RADIUS * rad * 2 * (camera.zoom / 2);
        return gameWorld.getPlayer().getBody().getPosition().dst(body.getPosition()) < GRAVITY_RADIUS * rad * 2;
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
