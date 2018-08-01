package com.binarygames.spaceboi.gameobjects.entities.weapons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.binarygames.spaceboi.Assets;
import com.binarygames.spaceboi.gameobjects.GameWorld;
import com.binarygames.spaceboi.gameobjects.entities.EntityDynamic;

public class Cannon extends Weapon {
    public Cannon(GameWorld gameWorld, EntityDynamic shooter) {
        super(gameWorld, shooter);
        this.bulletMass = 25;
        this.bulletRadius = 10;
        this.bulletSpeed = 0.75f;
        this.path = Assets.PLAYER;
        this.recoil = 40;
        this.removeBulletDelay = 2;
        this.timeBetweenShots = 0f;
        this.magSize = this.currentMag = 1;
        this.reloadTime = 5;
        this.damage = 50;
        this.name = "Cannon";

        this.sprite = new Sprite(gameWorld.getGame().getAssetManager().get(path, Texture.class));
        sprite.setSize(radius * 2, radius * 2);
        sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
    }

    @Override
    public void Shoot(float x, float y, Vector2 shootDirection) {
        if (canShoot()) {
            shootDirection.scl(bulletSpeed);
            new Bullet(gameWorld, x, y, path, shootDirection, bulletMass, bulletRadius, removeBulletDelay, damage, shooter, this);
            weaponMaths();
            gameWorld.getGame().getSoundManager().play(Assets.WEAPON_GRENADELAUNCHER_SHOT);

       }
    }
}
