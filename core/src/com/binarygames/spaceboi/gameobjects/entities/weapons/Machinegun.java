package com.binarygames.spaceboi.gameobjects.entities.weapons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.binarygames.spaceboi.gameobjects.GameWorld;
import com.binarygames.spaceboi.gameobjects.entities.Bullet;
import com.binarygames.spaceboi.gameobjects.entities.EntityDynamic;

public class Machinegun extends Weapon {

    public Machinegun(World aWorld, GameWorld aGameWorld, EntityDynamic shooter){
        super(aWorld, aGameWorld, shooter);

        this.bulletMass = 10;
        this.bulletRadius = 2f;
        this.bulletSpeed = 5;
        this.recoil = 10;
        this.removeBulletDelay = 0;
        this.timeBetweenShots = 0.1f;
        this.magSize = this.currentMag = 25;
        this.reloadTime = 2;
        this.damage = 5;

        this.path = "playerShip.png";
        this.radius = 5;
        this.sprite = new Sprite(new Texture(path));
        sprite.setSize(radius * 2, radius * 2);
        sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
    }

    @Override
    public void Shoot(float x, float y, Vector2 shootDirection) {
        if (canShoot()) {
            shootDirection.scl(bulletSpeed);
            new Bullet(world, x, y, path, shootDirection, gameWorld, bulletMass, bulletRadius, removeBulletDelay, damage, shooter);
            weaponMaths();
        }
    }
}
