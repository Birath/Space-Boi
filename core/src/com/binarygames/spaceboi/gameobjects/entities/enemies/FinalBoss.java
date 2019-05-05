package com.binarygames.spaceboi.gameobjects.entities.enemies;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.binarygames.spaceboi.gameobjects.GameWorld;
import com.binarygames.spaceboi.gameobjects.entities.weapons.Cannon;
import com.binarygames.spaceboi.gameobjects.entities.weapons.Weapon;
import com.binarygames.spaceboi.screens.VictoryScreen;

public class FinalBoss extends Enemy implements MeleeEnemy {
    // Cannon values
    public static final int MINIMUM_CANNON_DISTANCE = 500;
    private static final float MINIMUM_TIME_SINCE_CHARGE = 0.5f;

    // Charge values
    private static final float MINIMUM_CHARGE_DISTANCE = 700;

    private static final float CHARGE_ATTACK_COOLDOWN = 10.0f;
    private static final float MAXIMUM_CHARGE_TIME = 5.0f;
    private static final float CHARGE_CHANNEL_TIME = 2.0f;
    private static final float MISSED_CHARGE_STUN = 1.5f;

    private static final float CHARGE_SPEED = 40;
    private static final int CHARGE_DAMANGE = 100;


    // Clocks
    private float timeSinceLastCharge = 0;
    private float timeSinceChannelStarted = 0;
    private float timeSinceChargeStart = 0;
    private float stunTime = 0;
    private float angle;
    private Cannon cannon;
    private boolean stunned;

    private enum ChargeState {
        CHANNELING, CHARGING, COOLDOWN
    }
    private ChargeState chargeState = ChargeState.COOLDOWN;


    public FinalBoss(GameWorld gameWorld, float x, float y, String path) {
        super(gameWorld, x, y, path, EnemyType.FINAL_BOSS, EnemyType.FINAL_BOSS.getWidth(), EnemyType.FINAL_BOSS.getHeight());
        this.cannon = new Cannon(gameWorld, this);
        sprite.rotate90(false);
    }

    @Override
    protected void getSounds() {

    }

    @Override
    public void update(float delta) {
        super.update(delta);
    }

    @Override
    public void render(SpriteBatch batch, OrthographicCamera camera) {
        super.render(batch, camera);
        sprite.rotate(-90);
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
        updateChargeTimers(delta);

        if (stunned) {
            // Play stun animation...
            stunTime += delta;
            standStill();
            stunned = stunTime < MISSED_CHARGE_STUN;
        }
        else if (shouldStartChanneling()) {
            chargeState = ChargeState.CHANNELING;
            standStill();
        }
        else if (shouldCharge()) {
            charge();
        }
        else if (chargeState == ChargeState.CHANNELING) {
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

    @Override
    public void onRemove() {
        super.onRemove();
        gameWorld.getGame().setScreen(new VictoryScreen(gameWorld.getGame(), gameWorld.getGame().getScreen()));
    }

    private void updateChargeTimers(float delta) {
        switch (chargeState) {
            case CHANNELING:
                timeSinceChannelStarted += delta;
                break;
            case COOLDOWN:
                timeSinceLastCharge += delta;
                break;
            case CHARGING:
                timeSinceChargeStart += delta;
                break;
        }
    }

    private void charge() {
        if (chargeState == ChargeState.CHANNELING) {
            chargeState = ChargeState.CHARGING;
        }
        if (timeSinceChargeStart > MAXIMUM_CHARGE_TIME) {
            stopCharge(false);
            return;
        }
        Vector2 chargeVector = perpen.cpy();
        chargeVector.scl(CHARGE_SPEED);
        if (moveRight) {
            body.applyForceToCenter(chargeVector, true);
        } else if (moveLeft) {
            body.applyForceToCenter(chargeVector.scl(-1), true);
        }
    }

    private void stopCharge(boolean hitPlayer) {
        if (!hitPlayer) {
            stunned = true;
        }
        timeSinceLastCharge = 0;
        timeSinceChannelStarted = 0;
        timeSinceChargeStart = 0;
        stunTime = 0;
        chargeState = ChargeState.COOLDOWN;
    }

    private boolean shouldCharge() {
        return timeSinceChannelStarted > CHARGE_CHANNEL_TIME;
    }

    private boolean shouldStartChanneling() {
        return chargeState == ChargeState.COOLDOWN && timeSinceLastCharge > CHARGE_ATTACK_COOLDOWN && MINIMUM_CHARGE_DISTANCE > body.getPosition().dst2(player.getBody().getPosition());
    }

    private boolean shouldShootCannon() {
        return MINIMUM_CANNON_DISTANCE > body.getPosition().dst2(player.getBody().getPosition()) && timeSinceLastCharge > MINIMUM_TIME_SINCE_CHARGE;
    }

    @Override
    protected void updateJumping(float delta) {
        //moveAlongPlanet();
    }

    @Override
    public void touchedPlayer() {
        if (chargeState == ChargeState.CHARGING) {
            stopCharge(true);
            gameWorld.getPlayer().reduceHealth(CHARGE_DAMANGE);
            // Play melee animation
        }
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

        shootWeapon.shoot(shootFrom, shootDirection);
    }


    public void setAngle(float angle) {
        this.angle = angle;
    }

    public float getAngle() {
        return angle;
    }


}
