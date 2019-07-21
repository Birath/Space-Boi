package com.binarygames.spaceboi.gameobjects.entities.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.binarygames.spaceboi.Assets;
import com.binarygames.spaceboi.animation.AnimationHandler;
import com.binarygames.spaceboi.gameobjects.GameWorld;
import com.binarygames.spaceboi.gameobjects.entities.weapons.Cannon;
import com.binarygames.spaceboi.gameobjects.entities.weapons.Weapon;

import java.util.ArrayList;
import java.util.List;

public class FinalBoss extends Enemy implements MeleeEnemy {
    // Cannon values
    public static final int MINIMUM_CANNON_DISTANCE = 500;
    private static final float MINIMUM_TIME_SINCE_LAST_SHOT = 5f;

    // Charge values
    private static final float MINIMUM_CHARGE_DISTANCE = 5000;

    private static final float CHARGE_ATTACK_COOLDOWN = 10.0f;
    private static final float MAXIMUM_CHARGE_TIME = 5.0f;
    private static final float CHARGE_CHANNEL_TIME = 2.0f;
    private static final float MISSED_CHARGE_STUN = 1.5f;

    private static final float CHARGE_SPEED = 40;
    private static final int CHARGE_DAMANGE = 100;

    //Animation
    private static final int BOSS_WIDTH = 500; //From the size of the spritesheet
    private static final int BOSS_HEIGHT = 700;

    private AnimationHandler walkAnimationHandler;
    private static final int WALK_FRAME_COLUMNS = 1;
    private static final int WALK_FRAME_ROWS = 17;
    private static final float runFrameDuration = 0.2f;

    private AnimationHandler chargeAnimationHandler;
    private static final int CHARGE_FRAME_COLUMNS = 1;
    private static final int CHARGE_FRAME_ROWS = 12;
    private static final float chargeFrameDuration = 0.04f;

    private AnimationHandler prepchargeAnimationHandler;
    private static final int PREPCHARGE_FRAME_COLUMNS = 1;
    private static final int PREPCHARGE_FRAME_ROWS = 17;
    private static final float prepchargeFrameDuration = 0.3f;
    private float channelAnimationTime = PREPCHARGE_FRAME_COLUMNS * PREPCHARGE_FRAME_ROWS * prepchargeFrameDuration;  // Time to play channel/chargeprep animation

    private AnimationHandler shootAnimationHandler;
    private static final int SHOOT_FRAME_COLUMNS = 1;
    private static final int SHOOT_FRAME_ROWS = 10;
    private static final float shootFrameDuration = 0.25f;
    private float shootAnimationTime = SHOOT_FRAME_COLUMNS * SHOOT_FRAME_ROWS * shootFrameDuration;  // Time to play shoot animation
    private float bulletFiredAnimationTime = 8 * shootFrameDuration; // actual shot goes off on the 8th frame

    //Sounds
    private boolean isWalking;
    private long walkingSoundID;
    private Sound walkingSound;

    // Clocks
    private float timeSinceLastCharge = 0;
    private float timeSinceChannelStarted = 0;
    private float timeSinceChargeStart = 0;
    private float timeSinceShootStarted = 0;
    private float timeSinceLastShot = 0;

    //
    private float stunTime = 0;
    private float angle;
    private Cannon cannon;
    private boolean stunned;
    private int deAggroDist = 20000; //Basically never de-aggros

    private enum BossState {
        CHANNELING, CHARGING, SHOOTING, WALKING
    }
    private BossState bossState = BossState.WALKING;


    public FinalBoss(GameWorld gameWorld, float x, float y, String path) {
        super(gameWorld, x, y, path, EnemyType.FINAL_BOSS, EnemyType.FINAL_BOSS.getWidth(), EnemyType.FINAL_BOSS.getHeight());
        this.cannon = new Cannon(gameWorld, this);
        loadAnimations();
        this.setDeAggroDistance(deAggroDist);
        walkingSound = gameWorld.getGame().getAssetManager().get(Assets.END_BOSS_CLANK, Sound.class);


    }

    @Override
    protected void getSounds() {
        attackSounds.add(Assets.END_BOSS1);

        damagedSounds.add(Assets.RICOCHET1);
        damagedSounds.add(Assets.RICOCHET2);
        damagedSounds.add(Assets.RICOCHET3);
        damagedSounds.add(Assets.RICOCHET4);
    }

    private void loadAnimations(){
        walkAnimationHandler = new AnimationHandler(gameWorld, WALK_FRAME_COLUMNS, WALK_FRAME_ROWS,
                runFrameDuration, Assets.END_BOSS_WALK_ANIMATION);

        chargeAnimationHandler = new AnimationHandler(gameWorld, CHARGE_FRAME_COLUMNS, CHARGE_FRAME_ROWS,
                chargeFrameDuration, Assets.END_BOSS_CHARGE_ANIMATION);

        prepchargeAnimationHandler = new AnimationHandler(gameWorld, PREPCHARGE_FRAME_COLUMNS,
                PREPCHARGE_FRAME_ROWS, prepchargeFrameDuration, Assets.END_BOSS_PREPCHARGE_ANIMATION);

        shootAnimationHandler = new AnimationHandler(gameWorld, SHOOT_FRAME_COLUMNS, SHOOT_FRAME_ROWS,
                shootFrameDuration, Assets.END_BOSS_SHOOT_ANIMATION);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
    }

    @Override
    public void render(SpriteBatch batch, OrthographicCamera camera) {
        switch (bossState) {
            case CHANNELING:
                animationHandler = prepchargeAnimationHandler;
                break;
            case CHARGING:
                animationHandler = chargeAnimationHandler;
                break;
            case WALKING:
                animationHandler = walkAnimationHandler;
                break;
            case SHOOTING:
                animationHandler = shootAnimationHandler;
                break;
        }
        if (moveLeft && !animationHandler.isFlipped() || moveRight && animationHandler.isFlipped()) {
            animationHandler.setFlipped(!animationHandler.isFlipped());
        }
        batch.draw(animationHandler.getCurrentFrame(), body.getPosition().x * PPM - BOSS_WIDTH / 2,
                body.getPosition().y * PPM - BOSS_HEIGHT / 2, BOSS_WIDTH / 2, BOSS_HEIGHT / 2,
                BOSS_WIDTH, BOSS_HEIGHT, 0.4f, 0.4f, targetAngle + 90);
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
        updateTimers(delta);
        if(!isWalking){
            walkingSoundID = walkingSound.loop(gameWorld.getGame().getPreferences().getSoundVolume());
            isWalking = true;
        }

        if (stunned) {
            // Play stun animation...
            stunTime += delta;
            standStill();
            stunned = stunTime < MISSED_CHARGE_STUN;
        }
        else if(bossState == BossState.SHOOTING){
            updateShooting();
            standStill();
        }
        else if (bossState == BossState.CHANNELING) {
            updateChannel();  //Sets stage to charge after the animation has been played
            standStill();
        }
        else if (shouldStartChanneling()) {
            bossState = BossState.CHANNELING;
            standStill();
        }
        else if (bossState == BossState.CHARGING) {
            charge();
        }
        else if (shouldShootCannon()) {
            bossState = BossState.SHOOTING;
            standStill();
        } else {
            moveAlongPlanet();
            bossState = BossState.WALKING;
        }
    }

    @Override
    public void onRemove() {
        isWalking = false;
        walkingSound.stop(walkingSoundID);
        super.onRemove();
        gameWorld.winGame();
    }

    private void updateTimers(float delta) {
        switch (bossState) {
            case CHANNELING:
                timeSinceChannelStarted += delta;
                prepchargeAnimationHandler.updateAnimation(delta);
                break;
            case CHARGING:
                timeSinceChargeStart += delta;
                chargeAnimationHandler.updateAnimation(delta);
                break;
            case WALKING:
                timeSinceLastShot += delta;  //we can only shoot if we have walked for a while
                timeSinceLastCharge += delta;
                animationHandler.updateAnimation(delta);
                break;
            case SHOOTING:
                timeSinceShootStarted += delta;
                shootAnimationHandler.updateAnimation(delta);
                break;
        }
    }

    private void charge() {
        if (bossState == BossState.CHANNELING) {
            bossState = BossState.CHARGING;
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
        timeSinceChargeStart = 0;
        stunTime = 0;
        bossState = BossState.WALKING;
    }

    private boolean shouldStartChanneling() {
        return bossState == BossState.WALKING && timeSinceLastCharge > CHARGE_ATTACK_COOLDOWN && MINIMUM_CHARGE_DISTANCE > body.getPosition().dst2(player.getBody().getPosition());
    }

    private boolean shouldShootCannon() {
        return MINIMUM_CANNON_DISTANCE > body.getPosition().dst2(player.getBody().getPosition()) && timeSinceLastShot > MINIMUM_TIME_SINCE_LAST_SHOT;
    }

    @Override
    protected void updateJumping(float delta) {
        //moveAlongPlanet();
    }

    @Override
    public void touchedPlayer() {
        if (bossState == BossState.CHARGING) {
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

    private void updateShooting(){
        if (timeSinceShootStarted > shootAnimationTime){
            bossState = BossState.WALKING;
            timeSinceShootStarted = 0;
            timeSinceLastShot = 0;
        }
        else if (timeSinceShootStarted > bulletFiredAnimationTime){
            shoot(cannon);
        }
    }

    private void updateChannel(){
        if (timeSinceChannelStarted > channelAnimationTime){
            bossState = BossState.CHARGING;
            timeSinceChannelStarted = 0;

        }

    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public float getAngle() {
        return angle;
    }


}
