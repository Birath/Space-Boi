package com.binarygames.spaceboi.gameobjects.entities.weapons;

import com.badlogic.gdx.math.Vector2;
import com.binarygames.spaceboi.Assets;
import com.binarygames.spaceboi.gameobjects.GameWorld;
import com.binarygames.spaceboi.gameobjects.entities.EntityDynamic;

public class GravityFreeMachineGun extends Machinegun {
    public GravityFreeMachineGun(GameWorld aGameWorld, EntityDynamic shooter) {
        super(aGameWorld, shooter);

        setBulletSpeed(4); // Default: 5
        setTimeBetweenShots(0.2f); // Default: 0.12f
    }

    @Override
    public void shoot(Vector2 pos, Vector2 shootDirection) {
        if (canShoot()) {
            shootDirection.scl(bulletSpeed);
            new GravityFreeBullet(gameWorld, pos.x, pos.y, bulletPath, shootDirection, bulletMass, bulletRadius, removeBulletDelay, damage, shooter, this);
            weaponMaths();

            gameWorld.getGame().getSoundManager().play(Assets.WEAPON_MACHINEGUN_SHOT);
        }
    }
}
