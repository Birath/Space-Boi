package com.binarygames.spaceboi.gameobjects.entities.weapons;

import com.badlogic.gdx.math.Vector2;
import com.binarygames.spaceboi.Assets;
import com.binarygames.spaceboi.gameobjects.GameWorld;
import com.binarygames.spaceboi.gameobjects.entities.EntityDynamic;

public class GravityFreeMachineGun extends Machinegun {
    public GravityFreeMachineGun(GameWorld aGameWorld, EntityDynamic shooter) {
        super(aGameWorld, shooter);
    }

    @Override
    public void Shoot(float x, float y, Vector2 shootDirection) {
        if (canShoot()) {
            shootDirection.scl(bulletSpeed);
            new GravityFreeBullet(gameWorld, x, y, path, shootDirection, bulletMass, bulletRadius, removeBulletDelay, damage, shooter, this);
            weaponMaths();
        }
        gameWorld.getGame().getSoundManager().play(Assets.WEAPON_MACHINEGUN_SHOT);
    }
}
