package com.binarygames.spaceboi.gameobjects.entities.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.binarygames.spaceboi.Assets;
import com.binarygames.spaceboi.animation.AnimationHandler;
import com.binarygames.spaceboi.gameobjects.GameWorld;
import com.binarygames.spaceboi.gameobjects.entities.Planet;
import com.binarygames.spaceboi.gameobjects.entities.weapons.Machinegun;
import com.binarygames.spaceboi.gameobjects.entities.weapons.Weapon;

public class Shooter extends Enemy {

    private int weapon_damage = 3;

    private static final float WEAPON_WIDTH = 3;
    private static final float WEAPON_HEIGHT = 7;

    private Machinegun machinegun;
    private Sprite machinegunSprite;
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

        machinegunSprite = new Sprite(gameWorld.getGame().getAssetManager().get(Assets.SHOOTER_WEAPON, Texture.class));
        machinegunSprite.setSize(machinegunSprite.getWidth() * 0.15f, machinegunSprite.getHeight() * 0.15f);

        machinegunSprite.setOrigin(machinegunSprite.getWidth() / 10, machinegunSprite.getHeight() / 2);
        machinegunSprite.flip(false, true);

        this.machinegun.setDamage(weapon_damage);
        this.machinegun.setRecoil(0);
        this.machinegun.setBulletSpeed(50);
    }

    private void aim(SpriteBatch batch) {
        Vector2 armOffset = body.getLocalCenter()
                .cpy()
                .add(0, 1);

        machinegunSprite.setPosition(
                body.getWorldPoint(armOffset).x * PPM - machinegunSprite.getOriginX(),
                body.getWorldPoint(armOffset).y * PPM - machinegunSprite.getOriginY()
        );

        float angle = getAngleToPlayer(body.getPosition());
        machinegunSprite.setRotation(angle * MathUtils.radiansToDegrees + 180);
        if (moveLeft && !machinegunSprite.isFlipY()) {
            machinegunSprite.flip(false, true);
        } else if (moveRight && machinegunSprite.isFlipY()) {
            machinegunSprite.flip(false, true);
        }
        machinegunSprite.draw(batch);
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
        //super.render(batch, camera);
        if (moveLeft && !animationHandler.isFlipped() || moveRight && animationHandler.isFlipped()) {
            animationHandler.setFlipped(!animationHandler.isFlipped());
        }
        batch.draw(animationHandler.getCurrentFrame(), body.getPosition().x * PPM - SHOOTER_WIDTH / 2, body.getPosition().y * PPM - SHOOTER_HEIGHT / 2, SHOOTER_WIDTH / 2, SHOOTER_HEIGHT / 2, SHOOTER_WIDTH, SHOOTER_HEIGHT, 0.15f, 0.15f, targetAngle + 90);
        aim(batch);
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
            body.setLinearVelocity(perpen.x/2, perpen.y/2); //Science cannot explain why there isnt a minus sign before perpen vectors
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
        perpen.setLength2(1).scl(rad/3 * PPM); //divide rad by three to make bullets spawn closer to gun - hacky
        if (Math.abs(perpen.angle(toPlayer)) > 90) {
            perpen.rotate(180);
        }

        recoil.setLength2(1);
        Vector2 muzzle = toPlayer.cpy().nor().scl(15);
        muzzle.add(toPlanet.cpy().nor().scl(-10));

        Vector2 shootFrom = body.getPosition().cpy().scl(PPM).add(muzzle);

        Vector2 shootDirection = new Vector2(toPlayer).nor();
        shootWeapon.shoot(shootFrom, shootDirection);
    }

    private static class ShotCallback implements RayCastCallback {

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
