package com.binarygames.spaceboi.gameobjects.entities.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.binarygames.spaceboi.Assets;
import com.binarygames.spaceboi.gameobjects.GameWorld;
import com.binarygames.spaceboi.gameobjects.entities.weapons.Machinegun;
import com.binarygames.spaceboi.gameobjects.entities.weapons.Weapon;

public class Shooter extends Enemy {

    private Machinegun machinegun;

    public Shooter(GameWorld gameWorld, float x, float y, String path) {
        super(gameWorld, x, y, path, EnemyType.SHOOTER);

        this.machinegun = new Machinegun(gameWorld, this);
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
        if (shootWeapon.getClass().getName().equals(Machinegun.class.getName())) {
            Vector2 shootDirection = new Vector2(toPlayer.x, toPlayer.y).setLength2(1).scl(rad * PPM);
            shootWeapon.Shoot(shootFrom.x, shootFrom.y, shootDirection);
        } else {
            shootWeapon.Shoot(shootFrom.x, shootFrom.y, perpen);
        }
    }
}
