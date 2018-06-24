package com.binarygames.spaceboi.gameobjects.entities.weapons;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.binarygames.spaceboi.gameobjects.GameWorld;
import com.binarygames.spaceboi.gameobjects.entities.EntityDynamic;

public class GrenadeLauncher extends Weapon {

    public GrenadeLauncher(World aWorld, GameWorld aGameWorld, EntityDynamic shooter) {
        super(aWorld, aGameWorld, shooter);

        this.bulletMass = 20;
        this.bulletRadius = 4f;
        this.bulletSpeed = 5;
        this.path = "playerShip.png";
        this.recoil = 35;
        this.removeBulletDelay = 2000;
        this.timeBetweenShots = 1;
        this.magSize = this.currentMag = 4;
        this.reloadTime = 4;
        this.damage = 20;
    }

    @Override
    public void Shoot(float x, float y, Vector2 shootDirection) {
        if (canShoot()) {
            shootDirection.scl(bulletSpeed);
            new Grenade(world, x, y, path, shootDirection, gameWorld, bulletMass, bulletRadius, removeBulletDelay, damage, shooter);
            weaponMaths();
        }
    }
}
