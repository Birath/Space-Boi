package com.binarygames.spaceboi.gameobjects.entities.weapons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.binarygames.spaceboi.Assets;
import com.binarygames.spaceboi.gameobjects.GameWorld;
import com.binarygames.spaceboi.gameobjects.entities.EntityDynamic;

public class HomingRocketLauncher extends Weapon {
    public HomingRocketLauncher(GameWorld aGameWorld, EntityDynamic shooter) {
        super(aGameWorld, shooter);

        this.bulletMass = 10;
        this.bulletRadius = 2f;
        this.bulletSpeed = 1;
        this.path = Assets.PLAYER;
        this.recoil = 0;
        this.removeBulletDelay = 0;
        this.timeBetweenShots = 0.1f;
        this.magSize = this.currentMag = 8;
        this.reloadTime = 2;
        this.damage = 10;

        this.radius = 5;
        this.sprite = new Sprite(aGameWorld.getGame().getAssetManager().get(path, Texture.class));
        sprite.setSize(radius * 2, radius * 2);
        sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
    }
    @Override
    public void Shoot(float x, float y, Vector2 shootDirection) {
        if (canShoot()) {
            shootDirection.scl(bulletSpeed);

            new HomingRocket(gameWorld, x, y, path, shootDirection, bulletMass, bulletRadius, removeBulletDelay, damage, shooter);
            weaponMaths();

            gameWorld.getGame().getSoundManager().play(Assets.WEAPON_GRENADELAUNCHER_SHOT);
            weaponMaths();
        }
    }
}
