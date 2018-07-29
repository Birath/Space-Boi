package com.binarygames.spaceboi.gameobjects.entities.enemies;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.binarygames.spaceboi.gameobjects.GameWorld;

public class FinalBoss extends Enemy {
    private float angle;
    public FinalBoss(GameWorld gameWorld, float x, float y, String path) {
        super(gameWorld, x, y, path, EnemyType.FINAL_BOSS, EnemyType.FINAL_BOSS.getWidth(), EnemyType.FINAL_BOSS.getHeight());
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        body.setTransform(body.getPosition(), angle * MathUtils.degreesToRadians + MathUtils.PI / 2);
    }

    @Override
    public void render(SpriteBatch batch, OrthographicCamera camera) {
        super.render(batch, camera);
        getSprite().setRotation(angle + 90);
    }


    @Override
    protected void updateIdle(float delta) {
        standStill();
    }

    @Override
    protected void updateHunting(float delta) {
        standStill();
    }

    @Override
    protected void updateAttacking(float delta) {

    }

    @Override
    protected void updateJumping(float delta) {

    }


    public void setAngle(float angle) {
        this.angle = angle;
    }

    public float getAngle() {
        return angle;
    }
}
