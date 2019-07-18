package com.binarygames.spaceboi.gameobjects.entities.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.binarygames.spaceboi.Assets;
import com.binarygames.spaceboi.animation.AnimationHandler;
import com.binarygames.spaceboi.gameobjects.GameWorld;
import com.binarygames.spaceboi.gameobjects.entities.Planet;
import com.binarygames.spaceboi.gameobjects.entities.weapons.Machinegun;
import com.binarygames.spaceboi.gameobjects.entities.weapons.Weapon;
import com.binarygames.spaceboi.gameobjects.utils.DebugDrawer;

public class Shooter extends Enemy {

    private static final float WEAPON_WIDTH = 3;
    private static final float WEAPON_HEIGHT = 7;

    private Machinegun machinegun;
    private Sprite machinGunSprite;
    private static final int RUN_FRAME_COLUMNS = 1;
    private static final int RUN_FRAME_ROWS = 12;
    private static final float runFrameDuration = 0.04f;
    private static final int SHOOTER_WIDTH = 213; //From the size of the spritesheet
    private static final int SHOOTER_HEIGHT = 393;

    private static final float TARGET_SHOOTING_DISTANCE = 10;

    public Shooter(GameWorld gameWorld, float x, float y, String path) {
        super(gameWorld, x, y, path, EnemyType.SHOOTER);

        this.machinegun = new Machinegun(gameWorld, this);
        animationHandler = new AnimationHandler(gameWorld, RUN_FRAME_COLUMNS, RUN_FRAME_ROWS, runFrameDuration, Assets.PIRATE_WALK_ANIMATION);

        machinGunSprite = new Sprite(gameWorld.getGame().getAssetManager().get(Assets.WEAPON_MACHINEGUN, Texture.class));
        machinGunSprite.setSize(machinGunSprite.getWidth() * 0.1f, machinGunSprite.getHeight() * 0.1f);
        machinGunSprite.setOriginCenter();
        machinGunSprite.flip(true, false);

    }

    private void aim(SpriteBatch batch) {
        Vector2 spritePosition = new Vector2(body.getPosition().x * PPM - machinGunSprite.getWidth() / 2, (body.getPosition().y) * PPM - machinGunSprite.getHeight() / 2);
        // Gets the sprites position in physics units and gets the angle from it to the player
        float angle = getAngleToPlayer(body.getPosition());
        machinGunSprite.setPosition(spritePosition.x, spritePosition.y);
        machinGunSprite.setRotation(angle * MathUtils.radiansToDegrees);
        if (moveRight && !machinGunSprite.isFlipY()) {
            machinGunSprite.flip(false, true);
        } else if (moveLeft && machinGunSprite.isFlipY()) {
            machinGunSprite.flip(false, true);
        }
        machinGunSprite.draw(batch);
    }

    @Override
    protected void getSounds() {
        attackSounds.add(Assets.PIRATE_ATTACK1);
        attackSounds.add(Assets.PIRATE_ATTACK2);
        attackSounds.add(Assets.PIRATE_ATTACK3);
        attackSounds.add(Assets.PIRATE_ATTACK4);

        deathSounds.add(Assets.PIRATE_DEATH);

        damagedSounds.add(Assets.PIRATE_OUCH1);
        damagedSounds.add(Assets.PIRATE_OUCH2);
        damagedSounds.add(Assets.PIRATE_OUCH3);
        damagedSounds.add(Assets.PIRATE_OUCH4);
    }

    @Override
    protected void updateIdle(float delta) {
        standStill();
    }

    @Override
    protected void updateHunting(float delta) {
        if (shouldShoot() && distanceToPlayer() < 450) {
            shoot(machinegun);
        }
        animationHandler.updateAnimation(delta);
    }

    @Override
    protected void updateAttacking(float delta) {
        if (shouldShoot()) {
            shoot(machinegun);
            if (distanceToPlayer() > TARGET_SHOOTING_DISTANCE) {
                moveAlongPlanetSlowly();
                animationHandler.updateAnimation(delta / 2);
            } else {
                body.setLinearVelocity(0, 0);
            }
        } else {
            moveAlongPlanet();
            animationHandler.updateAnimation(delta);
        }
    }

    @Override
    protected void updateJumping(float delta) {
        updateHunting(delta);
    }

    @Override
    public void render(SpriteBatch batch, OrthographicCamera camera) {
        aim(batch);
        //super.render(batch, camera);
        if (moveLeft && !animationHandler.isFlipped() || moveRight && animationHandler.isFlipped()) {
            animationHandler.setFlipped(!animationHandler.isFlipped());
        }
        batch.draw(animationHandler.getCurrentFrame(), body.getPosition().x * PPM - SHOOTER_WIDTH / 2, body.getPosition().y * PPM - SHOOTER_HEIGHT / 2, SHOOTER_WIDTH / 2, SHOOTER_HEIGHT / 2, SHOOTER_WIDTH, SHOOTER_HEIGHT, 0.15f, 0.15f, targetAngle + 90);
        //Scale is set based on in-game look
    }

    private boolean shouldShoot() {
        ShotCallback scb = new ShotCallback();
        gameWorld.getWorld().rayCast(scb, getBody().getPosition(), player.getBody().getPosition());

        return !scb.hasHit;
    }

    private void moveAlongPlanetSlowly() {
        if (moveRight) {
            body.setLinearVelocity(perpen.cpy().scl(0.5f));
        } else if (moveLeft) {
            body.setLinearVelocity(perpen.cpy().scl(-0.5f));
        } else {
            standStill();
        }
    }

    private void shoot(Weapon shootWeapon) {
        Vector2 recoil = new Vector2(-toPlayer.x, -toPlayer.y);
        recoil.setLength2(1);

        //Setting recoil
        recoil.scl(shootWeapon.getRecoil());
        body.setLinearVelocity(recoil);

        perpen = new Vector2(-toPlanet.y, toPlanet.x);
        perpen.setLength2(1).scl(rad * PPM);
        if (Math.abs(perpen.angle(toPlayer)) > 90) {
            perpen.rotate(180);
        }

        Vector2 shootFrom = new Vector2(body.getPosition().x * PPM + perpen.x,
                body.getPosition().y * PPM + perpen.y);
        Vector2 muzzle = new Vector2(WEAPON_WIDTH, WEAPON_HEIGHT).scl(1, 1);
        if  (machinGunSprite.isFlipY()) {
            muzzle.scl(-1, 1);
        }
        muzzle.rotate(getAngleToPlayer(body.getPosition()) * MathUtils.radiansToDegrees + 90);
        shootFrom.add(muzzle);

        Vector2 shootDirection = new Vector2(toPlayer).setLength2(1).scl(rad * PPM);
        shootWeapon.shoot(shootFrom, shootDirection);
    }

    private class ShotCallback implements RayCastCallback {

        private boolean hasHit;

        @Override
        public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
            if (fixture.getBody().getUserData() instanceof Planet) {
                hasHit = true;
                return 0;
            }
            return 1;
        }
    }
}
