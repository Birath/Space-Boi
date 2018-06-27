package com.binarygames.spaceboi.gameobjects.entities.weapons;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.binarygames.spaceboi.gameobjects.GameWorld;
import com.binarygames.spaceboi.gameobjects.entities.Bullet;
import com.binarygames.spaceboi.gameobjects.entities.EntityDynamic;

public class Machinegun extends Weapon {

    public Machinegun(GameWorld aGameWorld, EntityDynamic shooter) {
        super(aGameWorld, shooter);

        this.bulletMass = 10;
        this.bulletRadius = 2f;
        this.bulletSpeed = 5;
        this.path = "game/entities/player/playerShip.png";
        this.recoil = 10;
        this.removeBulletDelay = 0;
        this.timeBetweenShots = 0.1f;
        this.magSize = this.currentMag = 25;
        this.reloadTime = 2;
        this.damage = 5;
    }

    @Override
    public void Shoot(float x, float y, Vector2 shootDirection) {
        if (canShoot()) {
            shootDirection.scl(bulletSpeed);
            new Bullet(gameWorld, x, y, path, shootDirection, bulletMass, bulletRadius, removeBulletDelay, damage, shooter);
            weaponMaths();
        }
    }
}
