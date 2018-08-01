package com.binarygames.spaceboi.gameobjects.entities.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.binarygames.spaceboi.gameobjects.GameWorld;
import com.binarygames.spaceboi.gameobjects.entities.weapons.Cannon;
import com.binarygames.spaceboi.gameobjects.entities.weapons.Weapon;

public class FinalBoss extends Enemy {
    public static final int MINIMUM_CANNON_DISTANCE = 500;
    private float angle;
    private Cannon cannon;

    public FinalBoss(GameWorld gameWorld, float x, float y, String path) {
        super(gameWorld, x, y, path, EnemyType.FINAL_BOSS, EnemyType.FINAL_BOSS.getWidth(), EnemyType.FINAL_BOSS.getHeight());
        this.cannon = new Cannon(gameWorld, this);

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
        if (shouldShootCannon()) {
            shoot(cannon);
            standStill();
        } else {
            moveAlongPlanet();
            //standStill();
        }

    }

    private boolean shouldShootCannon() {
        return MINIMUM_CANNON_DISTANCE > body.getPosition().dst2(player.getBody().getPosition());
    }

    @Override
    protected void updateJumping(float delta) {
        //moveAlongPlanet();
    }

    private void shoot(Weapon shootWeapon) {
        if (!shootWeapon.canShoot()) {
            return;
        }
        Vector2 localTopCenter = new Vector2(body.getLocalCenter().x, body.getLocalCenter().y + getHeight() / 2);
        Vector2 worldTopCenter = body.getWorldPoint(localTopCenter);
        Vector2 tempToPlayer = player.getBody().getPosition().sub(worldTopCenter);
        Vector2 shootDirection = new Vector2(tempToPlayer).setLength2(1).scl(width  * PPM);
        Vector2 shootFrom = new Vector2(worldTopCenter.x * PPM + shootDirection.x, worldTopCenter.y * PPM + shootDirection.y);

        shootWeapon.Shoot(shootFrom.x, shootFrom.y, shootDirection);
    }


    public void setAngle(float angle) {
        this.angle = angle;
    }

    public float getAngle() {
        return angle;
    }
}
