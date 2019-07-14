package com.binarygames.spaceboi.gameobjects.entities.enemies;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.binarygames.spaceboi.Assets;
import com.binarygames.spaceboi.gameobjects.GameWorld;
import com.binarygames.spaceboi.gameobjects.entities.ENTITY_STATE;
import com.binarygames.spaceboi.gameobjects.entities.weapons.Shotgun;
import com.binarygames.spaceboi.gameobjects.pickups.WeaponAttachments.WeaponAttachment;

import java.lang.reflect.InvocationTargetException;

public class FlyingShip extends Enemy {

    private static final float SHOTGUN_OFFSET = 35;
    private static final float MIN_ANGLE = 0.5f;
    private Shotgun shotgun;
    private int bulletSpeed = 1;
    private int shotgunDamage = 15;
    private int shotgunMagsize = 1;

    private Sprite shotgunSprite;

    public FlyingShip(GameWorld gameWorld, float x, float y, String path) {
        super(gameWorld, x, y, path, EnemyType.FLYING_SHIP);
        this.entityState = ENTITY_STATE.JUMPING;
        shotgun = new Shotgun(gameWorld, this);
        shotgun.setBulletSpeed(bulletSpeed);
        shotgun.setDamage(shotgunDamage);
        shotgun.setMagSize(shotgunMagsize);

        shotgunSprite = new Sprite(gameWorld.getGame().getAssetManager().get(Assets.FLYINGSHIP_CANNON, Texture.class));
        shotgunSprite.setSize(10, 20);
        shotgunSprite.setOriginCenter();
        //shotgunSprite.rotate90(true);

        damagedSounds.add(Assets.RICOCHET1);
        damagedSounds.add(Assets.RICOCHET2);
        damagedSounds.add(Assets.RICOCHET3);
        damagedSounds.add(Assets.RICOCHET4);
    }

    @Override
    public void render(SpriteBatch batch, OrthographicCamera camera) {
        aimShotgun(batch);
        super.render(batch, camera);
    }

    @Override
    protected void getSounds() {

    }

    @Override
    protected void updateIdle(float delta) {
        //Do nothing
    }

    @Override
    protected void updateHunting(float delta) {
        //Do nothing
    }

    @Override
    protected void updateAttacking(float delta) {
        //Do nothing
    }

    @Override
    public boolean isAffectedByGravity() {
        return false;
    }

    private void updateIdleJumping() {
        moveAlongPlanet();
    }

    private void updateHuntingJumping() {
        standStill();
    }

    private void updateAttackingJumping() {
        if (toShootRockets() && weapon.canShoot()) {
            ShootRockets();
        } else if (toShootShotgun() && shotgun.canShoot()) {
            ShootShotgun();
        } else {
            moveAlongPlanet();
        }
    }

    @Override
    protected void updateJumping(float delta) {
        if (this.planetBody != null) {
            if (enemyState == ENEMY_STATE.IDLE) {
                updateIdleJumping();
            } else if (enemyState == ENEMY_STATE.HUNTING) {
                updateHuntingJumping();
            } else if (enemyState == ENEMY_STATE.ATTACKING) {
                updateAttackingJumping();
            }
        } else {
            this.getBody().setLinearVelocity(0, 0);
        }
    }

    @Override
    public void updateWalkingDirection() {
        if (Math.abs(Math.abs(perpen.angle(toPlayer)) - 90) < MIN_ANGLE) {
            moveLeft = false;
            moveRight = false;
        } else {
            super.updateWalkingDirection();
        }

    }

    @Override
    public void onRemove() {
        super.onRemove();
        getClosestPlanet().setLaunchPadActive(true);
        try {
            gameWorld.addDynamicEntity(WeaponAttachment.getRandomAttachment(gameWorld, getBody().getPosition().x * PPM, getBody().getPosition().y * PPM));
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    private boolean toShootShotgun() {
        float angle = Math.abs(toPlanet.angle(toPlayer));
        return angle < 45;
    }

    private boolean toShootRockets() {
        float angle = Math.abs(toPlanet.angle(toPlayer));
        return 45 < angle && angle < 75;
    }

    private void ShootShotgun() {
        Vector2 shootDirection = new Vector2(toPlayer.x, toPlayer.y).setLength2(1).scl(rad * PPM);
        Vector2 shootFrom = new Vector2(body.getPosition().x * PPM,body.getPosition().y * PPM);
        shootFrom.add(toPlanet.cpy().nor().scl(SHOTGUN_OFFSET));

        shotgun.shoot(shootFrom, shootDirection);
    }

    private void ShootRockets() {
        perpen = new Vector2(-toPlanet.y, toPlanet.x);
        perpen.setLength2(1).scl(rad * PPM);
        if (Math.abs(perpen.angle(toPlayer)) > 90) {
            perpen.rotate(180);
        }

        Vector2 shootFrom = new Vector2(body.getPosition().x * PPM + perpen.x,
            body.getPosition().y * PPM + perpen.y);

        weapon.shoot(shootFrom, perpen);
    }

    private void aimShotgun(SpriteBatch batch) {
        Vector2 spritePosition = new Vector2(body.getPosition().x * PPM - shotgunSprite.getWidth() / 2, (body.getPosition().y)  * PPM - shotgunSprite.getHeight() / 2);
        spritePosition.add(toPlanet.cpy().nor().scl(SHOTGUN_OFFSET));
        // Gets the sprites position in physics units and gets the angle from it to the player
        float angle = getAngleToPlayer(body.getPosition().cpy().add(toPlanet.cpy().nor().scl(SHOTGUN_OFFSET / PPM)));
        shotgunSprite.setPosition(spritePosition.x, spritePosition.y);
        shotgunSprite.setRotation(angle * MathUtils.radiansToDegrees - 90);
        shotgunSprite.draw(batch);
    }

}
