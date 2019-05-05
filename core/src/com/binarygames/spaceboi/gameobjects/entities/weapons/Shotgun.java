package com.binarygames.spaceboi.gameobjects.entities.weapons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.binarygames.spaceboi.Assets;
import com.binarygames.spaceboi.gameobjects.GameWorld;
import com.binarygames.spaceboi.gameobjects.entities.EntityDynamic;

public class Shotgun extends Weapon {

    public static final float SPRITE_SCALING = 0.1f;
    private int spread = 10;
    private int numberOfBullets = 7;

    public Shotgun(GameWorld aGameWorld, EntityDynamic shooter) {
        super(aGameWorld, shooter);

        this.bulletMass = 10;
        this.bulletRadius = 1.5f;
        this.bulletSpeed = 5;
        this.path = Assets.WEAPON_SHOTGUN;
        this.bulletPath = Assets.WEAPON_BULLET;
        this.recoil = 20;
        this.removeBulletDelay = 0;
        this.timeBetweenShots = 1;
        this.magSize = this.currentMag = 2;
        this.reloadTime = 2.2f;
        this.damage = 10;
        this.name = "Shotgun";

        //Sprite setup
        this.sprite = new Sprite(aGameWorld.getGame().getAssetManager().get(path, Texture.class));
        sprite.setSize(sprite.getWidth() * SPRITE_SCALING, sprite.getHeight() * SPRITE_SCALING);
        sprite.setOriginCenter();
    }

    @Override
    public void shoot(Vector2 pos, Vector2 shootDirection) {
        if (canShoot()) {
            shootDirection.scl(bulletSpeed);
            for (int i = 0; i < numberOfBullets; i++) {
                int randomAngle = MathUtils.random(-spread, spread);
                Vector2 randomShootDirection = shootDirection.rotate(randomAngle);
                new Bullet(gameWorld, pos.x, pos.y, bulletPath, randomShootDirection, bulletMass, bulletRadius, removeBulletDelay, damage, shooter, this);
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
