package com.binarygames.spaceboi.gameobjects.entities.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.binarygames.spaceboi.Assets;
import com.binarygames.spaceboi.gameobjects.GameWorld;
import com.binarygames.spaceboi.gameobjects.entities.weapons.Machinegun;
import com.binarygames.spaceboi.gameobjects.entities.weapons.Weapon;

public class Shooter extends Enemy {

    private static final float WEAPON_WIDTH = 3;
    private static final float WEAPON_HEIGHT = 14;

    private Machinegun machinegun;
    private Sprite machinGunSprite;

    public Shooter(GameWorld gameWorld, float x, float y, String path) {
        super(gameWorld, x, y, path, EnemyType.SHOOTER);

        this.machinegun = new Machinegun(gameWorld, this);
        machinGunSprite = new Sprite(gameWorld.getGame().getAssetManager().get(Assets.WEAPON_MACHINEGUN, Texture.class));
        machinGunSprite.setSize(machinGunSprite.getWidth() * 0.1f, machinGunSprite.getHeight() * 0.1f);
        machinGunSprite.setOriginCenter();
        machinGunSprite.flip(true, false);

    }

    @Override
    public void render(SpriteBatch batch, OrthographicCamera camera) {
        aim(batch);
        super.render(batch, camera);
    }

    private void aim(SpriteBatch batch) {
        Vector2 spritePosition = new Vector2(body.getPosition().x * PPM - machinGunSprite.getWidth() / 2, (body.getPosition().y)  * PPM - machinGunSprite.getHeight() / 2);
        // Gets the sprites position in physics units and gets the angle from it to the player
        float angle = getAngleToPlayer(body.getPosition());
        machinGunSprite.setPosition(spritePosition.x, spritePosition.y);
        machinGunSprite.setRotation(angle * MathUtils.radiansToDegrees);
        if (!sprite.isFlipX() && !machinGunSprite.isFlipY()) {
            machinGunSprite.flip(false, true);
        } else if (sprite.isFlipX() && machinGunSprite.isFlipY()) {
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
        if (toJump()) {
            jump();
        } else {
            moveAlongPlanet();
        }
    }

    @Override
    protected void updateAttacking(float delta) {
        if (shouldShootWithNormalGun()) {
            shoot(machinegun);
        } else if (shouldShootWithNonGravityGun()) {
            shoot(weapon);
        } else {
            moveAlongPlanet();
        }
    }

    @Override
    protected void updateJumping(float delta) {
        //Do nothing
    }

    private boolean shouldShootWithNormalGun() {
        float angle = Math.abs(toPlanet.angle(toPlayer));
        return 80 < angle;
    }

    private boolean shouldShootWithNonGravityGun() {
        return 60 < Math.abs(toPlanet.angle(toPlayer));
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
            muzzle.scl(1, -1);
        }
        //Gdx.app.log("Shooter", "Angle: " + getAngleToPlayer(body.getPosition()) * MathUtils.radiansToDegrees);
        //Gdx.app.log("Shooter", "Angle: " + getAngleToPlayer(new Vector2(0, 0)) * MathUtils.radiansToDegrees);
        muzzle.rotate(getAngleToPlayer(body.getPosition()) * MathUtils.radiansToDegrees + 90);
        //getAngleToPlayer(new Vector2(0, 0));
        //player.getBody().getPosition();
        shootFrom.add(muzzle);

        if (shootWeapon.getClass().getName().equals(Machinegun.class.getName())) {
            Vector2 shootDirection = new Vector2(toPlayer.x, toPlayer.y).setLength2(1).scl(rad * PPM);
            shootWeapon.shoot(shootFrom, shootDirection);
        } else {
            shootWeapon.shoot(shootFrom, perpen);
        }
    }
}
