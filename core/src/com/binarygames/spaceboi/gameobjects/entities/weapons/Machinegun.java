package com.binarygames.spaceboi.gameobjects.entities.weapons;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.binarygames.spaceboi.gameobjects.GameWorld;
import com.binarygames.spaceboi.gameobjects.entities.Bullet;

public class Machinegun extends Weapon {

    public Machinegun(World aWorld, GameWorld aGameWorld) {
        super(aWorld, aGameWorld);

        this.bulletMass = 12;
        this.bulletRadius = 2f;
        this.bulletSpeed = 6;
        this.path = "playerShip.png";
        this.recoil = 20;
        this.removeBulletDelay = 10;
        this.timeBetweenShots = 0.1f;
        this.magSize = this.currentMag = 30;
        this.reloadTime = 3;
    }

    @Override
    public void Shoot(float x, float y, Vector2 shootDirection) {
        if (canShoot()) {
            shootDirection.scl(bulletSpeed);
            new Bullet(world, x, y, path, shootDirection, gameWorld, bulletMass, bulletRadius, removeBulletDelay);
            weaponMaths();
        }
    }
}
