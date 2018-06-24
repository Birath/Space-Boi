package com.binarygames.spaceboi.gameobjects.entities.weapons;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.binarygames.spaceboi.gameobjects.GameWorld;
import com.binarygames.spaceboi.gameobjects.entities.Bullet;
import com.binarygames.spaceboi.gameobjects.entities.EntityDynamic;

public class Shotgun extends Weapon {

    private int spread = 10;

    public Shotgun(World aWorld, GameWorld aGameWorld, EntityDynamic shooter){
        super(aWorld, aGameWorld, shooter);

        this.bulletMass = 10;
        this.bulletRadius = 2f;
        this.bulletSpeed = 5;
        this.path = "playerShip.png";
        this.recoil = 50;
        this.removeBulletDelay = 0;
        this.timeBetweenShots = 1;
        this.magSize = this.currentMag = 2;
        this.reloadTime = 3;
        this.damage = 10;
    }
    @Override
    public void Shoot(float x, float y, Vector2 shootDirection) {
        if (canShoot()) {
            shootDirection.scl(bulletSpeed);
            int bullets = MathUtils.random(4, 7);
            int randomAngle = MathUtils.random(-spread, spread);
            for(int i = 0; i < bullets; i++){
                Vector2 randomShootDirection = shootDirection.rotate(randomAngle);
                new Bullet(world, x, y, path, randomShootDirection, gameWorld, bulletMass, bulletRadius, removeBulletDelay, damage, shooter);
            }
            weaponMaths();
        }
    }
}
