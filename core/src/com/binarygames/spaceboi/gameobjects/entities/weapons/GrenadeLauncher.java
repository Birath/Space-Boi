package com.binarygames.spaceboi.gameobjects.entities.weapons;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.binarygames.spaceboi.gameobjects.GameWorld;
import com.binarygames.spaceboi.gameobjects.entities.Bullet;

public class GrenadeLauncher extends Weapon {

    public GrenadeLauncher(World aWorld, GameWorld aGameWorld) {
        super(aWorld, aGameWorld);

        this.bulletMass = 20;
        this.bulletRadius = 4f;
        this.bulletSpeed = 5;
        this.path = "playerShip.png";
        this.recoil = 35;
        this.removeBulletDelay = 2000;
    }

    @Override
    public void Shoot(float x, float y, Vector2 shootDirection) {
        shootDirection.scl(bulletSpeed);
        new Bullet(world, x, y, path, shootDirection, gameWorld, bulletMass, bulletRadius, removeBulletDelay);
    }
}
