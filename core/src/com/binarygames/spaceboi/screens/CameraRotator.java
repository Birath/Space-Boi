package com.binarygames.spaceboi.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;

public class CameraRotator {
    private OrthographicCamera camera;
    private Vector3 axis;
    private Interpolation interpolation = Interpolation.circle;

    private float startRot;
    private float rotAmount;
    private float totRot;

    private float rotTime;
    private float timeCounter;

    public boolean rotationFinished = true;

    public CameraRotator(OrthographicCamera camera) {
        this.camera = camera;
    }

    public void update(final float delta) {
        if (rotationFinished)
            return;

        /* Increment time spent */
        timeCounter += delta;
        /* Calculate progress */
        float alpha = timeCounter / rotTime;
        alpha = MathUtils.clamp(alpha, 0, 1);

        /* Interpolate rotation using alpha (progress) */
        Gdx.app.log("CameraRotator", String.valueOf(startRot));
        Gdx.app.log("CameraRotator", String.valueOf(rotAmount));
        Gdx.app.log("CameraRotator", String.valueOf(alpha));
        rotateTo(interpolation.apply(startRot, rotAmount, alpha));

        /* If (alpha == 1) we're finished. */
        if (alpha == 1) {
            timeCounter = 0;
            totRot = 0;
            rotationFinished = true;
        }
    }

    /** Rotate camera by the specified degrees in the specified
     * time.
     *
     * @param degrees
     * @param time */
    public void startRotation(final float startRot, final float degrees, final float time) {
        if (!rotationFinished) {
            return;
        }
        Gdx.app.log("CameraRotator", "Starting rotation");
        this.startRot = startRot;
        this.totRot = startRot;
        rotAmount = degrees;
        rotTime = time;
        rotationFinished = false;
    }

    /** Rotate camera by the specified degrees.
     *
     * @param degrees */
    private void rotate(final float degrees) {
        Gdx.app.log("CameraRotator", "Rotating with: " + degrees);
        camera.rotate(degrees);
        totRot += degrees; // Track amount rotated
    }

    /** Rotates the camera to the specified degrees.
     *
     * @param degrees */
    private void rotateTo(final float degrees) {
        rotate(degrees - totRot);
    }
}