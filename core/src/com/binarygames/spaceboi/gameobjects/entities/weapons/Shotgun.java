package com.binarygames.spaceboi.gameobjects.entities.weapons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.binarygames.spaceboi.Assets;
import com.binarygames.spaceboi.gameobjects.GameWorld;
import com.binarygames.spaceboi.gameobjects.entities.EntityDynamic;

public class Shotgun extends Weapon {

    private int spread = 10;

    public Shotgun(GameWorld aGameWorld, EntityDynamic shooter) {
        super(aGameWorld, shooter);

        this.bulletMass = 10;
        this.bulletRadius = 2f;
        this.bulletSpeed = 5;
        this.path = Assets.PLAYER;
        this.recoil = 20;
        this.removeBulletDelay = 0;
        this.timeBetweenShots = 1;
        this.magSize = this.currentMag = 2;
        this.reloadTime = 2.2f;
        this.damage = 10;

        //Sprite setup:
        this.radius = 5;
        this.sprite = new Sprite(aGameWorld.getGame().getAssetManager().get(path, Texture.class));
        sprite.setSize(radius * 2, radius * 2);
        sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
    }

    @Override
    public void Shoot(float x, float y, Vector2 shootDirection) {
        if (canShoot()) {
            shootDirection.scl(bulletSpeed);
            int bullets = MathUtils.random(4, 7);
            int randomAngle = MathUtils.random(-spread, spread);
            for (int i = 0; i < bullets; i++) {
                Vector2 randomShootDirection = shootDirection.rotate(randomAngle);
                new Bullet(gameWorld, x, y, path, randomShootDirection, bulletMass, bulletRadius, removeBulletDelay, damage, shooter);
            }
            weaponMaths();

            gameWorld.getGame().getSoundManager().play(Assets.WEAPON_SHOTGUN_SHOT);
        }
    }

    @Override
    public void onReload() {
        gameWorld.getGame().getSoundManager().play(Assets.WEAPON_SHOTGUN_RELOAD);
    }
}
