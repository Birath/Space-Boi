package com.binarygames.spaceboi.gameobjects.entities.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.binarygames.spaceboi.gameobjects.GameWorld;
import com.binarygames.spaceboi.gameobjects.entities.weapons.Cannon;
import com.binarygames.spaceboi.gameobjects.entities.weapons.Weapon;

public class FinalBoss extends Enemy implements MeleeEnemy {
    public static final int MINIMUM_CANNON_DISTANCE = 500;

    private static final float CHARGE_ATTACK_COOLDOWN = 10.0f;
    private static final float MINIMUM_CHARGE_DISTANCE = 700;
    private static final float CHARGE_CHANNEL_TIME = 2.0f;
    private static final float CHARGE_SPEED = 30;

    private float timeSinceLastCharge = 0;
    private float timeSinceChannelStarted = 0;
    private float angle;
    private Cannon cannon;

    private enum ChargeState {
        CHANNELING, CHARGING, COOLDOWN
    }
    private ChargeState chargeState = ChargeState.COOLDOWN;
    private boolean channelingCharge = false;
    private boolean charging = false;


    public FinalBoss(GameWorld gameWorld, float x, float y, String path) {
        super(gameWorld, x, y, path, EnemyType.FINAL_BOSS, EnemyType.FINAL_BOSS.getWidth(), EnemyType.FINAL_BOSS.getHeight());
        this.cannon = new Cannon(gameWorld, this);

    }

    @Override
    public void update(float delta) {
        super.update(delta);
        if (chargeState == ChargeState.CHANNELING) {
            timeSinceChannelStarted += delta;
        } else if (chargeState == ChargeState.COOLDOWN) {
            timeSinceLastCharge += delta;
        }
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
        if (shouldStartCharging()) {
            chargeState = ChargeState.CHANNELING;
            Gdx.app.log("FinalBoss", "Starting channel");
            standStill();
        }
        else if (shouldCharge()) {
            charge();
        }
        else if (chargeState == ChargeState.CHANNELING) {
           Gdx.app.log("FinalBoss", "Channeling charge");
            standStill();
        }

        else if (shouldShootCannon()) {
            shoot(cannon);
            standStill();
        } else {
            moveAlongPlanet();
            //standStill();
        }
    }

    private void charge() {
        if (chargeState == ChargeState.CHANNELING) {
            chargeState = ChargeState.CHARGING;
        }
        Vector2 chargeVector = perpen.cpy();
        chargeVector.scl(CHARGE_SPEED);
        if (moveRight) {
            body.applyForceToCenter(chargeVector, true);
        } else if (moveLeft) {
            body.applyForceToCenter(chargeVector.scl(-1), true);
        }
    }

    private boolean shouldCharge() {
        return timeSinceChannelStarted > CHARGE_CHANNEL_TIME;
    }

    private boolean shouldStartCharging() {
        return chargeState == ChargeState.COOLDOWN && timeSinceLastCharge > CHARGE_ATTACK_COOLDOWN && MINIMUM_CHARGE_DISTANCE > body.getPosition().dst2(player.getBody().getPosition());
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

    @Override
    public void touchedPlayer() {
        if (chargeState == ChargeState.CHARGING) {
            timeSinceLastCharge = 0;
            timeSinceChannelStarted = 0;
            chargeState = ChargeState.COOLDOWN;
            Gdx.app.log("FinalBoss", "Should stop charge");
            // Play melee animation
        }
    }
}
