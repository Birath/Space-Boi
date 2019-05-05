package com.binarygames.spaceboi.gameobjects.entities;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.binarygames.spaceboi.Assets;
import com.binarygames.spaceboi.gameobjects.GameWorld;

import java.util.ArrayList;
import java.util.List;


public class Planet extends EntityStatic {

    private float radius;
    private List<LaunchPad> launchPads = new ArrayList<>();
    private boolean launchPadActive = true;

    private Texture atmosphereTexture;
    int atmosphereRadius = (int) (GRAVITY_RADIUS * rad * PPM);

    public static final int GRAVITY_RADIUS = 2;

    private GameWorld gameWorld;

    public Planet(GameWorld gameWorld, float x, float y, String path, float mass, float radius) {
        super(gameWorld, x, y, path, mass, radius);
        this.radius = radius;
        this.gameWorld = gameWorld;

        atmosphereTexture = gameWorld.getGame().getAssetManager().get(Assets.ATMOSPHERE, Texture.class);
    }

    @Override
    public void render(SpriteBatch batch, OrthographicCamera camera) {
        if (isVisible(camera)) {
            batch.draw(atmosphereTexture, body.getPosition().x * PPM - atmosphereRadius, body.getPosition().y * PPM - atmosphereRadius, 2 * GRAVITY_RADIUS * rad * PPM, 2 * GRAVITY_RADIUS * rad * PPM);
        }

        getSprite().setPosition(body.getPosition().x * PPM - getSprite().getWidth() / 2, body.getPosition().y * PPM - getSprite().getHeight() / 2);
        getSprite().draw(batch);
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
