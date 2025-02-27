package com.binarygames.spaceboi.gameobjects.entities.weapons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.binarygames.spaceboi.Assets;
import com.binarygames.spaceboi.gameobjects.GameWorld;
import com.binarygames.spaceboi.gameobjects.entities.EntityDynamic;

public class HomingRocketLauncher extends Weapon {

    public static final int ROCKET_HEIGHT = 16;
    public static final int ROCKET_WIDTH = 8;

    public HomingRocketLauncher(GameWorld aGameWorld, EntityDynamic shooter) {
        super(aGameWorld, shooter);

        this.bulletMass = 10;
        this.bulletRadius = 6f;
        this.bulletSpeed = 30f;
        this.path = Assets.PLAYER;
        this.bulletPath = Assets.WEAPON_MISSILE;
        this.recoil = 0;
        this.removeBulletDelay = 6;
        this.timeBetweenShots = 0.1f;
        this.magSize = this.currentMag = 8;
        this.reloadTime = 5;
        this.damage = 10;

        this.radius = 5;
        this.sprite = new Sprite(aGameWorld.getGame().getAssetManager().get(path, Texture.class));
        sprite.setSize(radius * 2, radius * 2);
        sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
    }

    @Override
    public void shoot(Vector2 pos, Vector2 shootDirection) {
        if (canShoot()) {
            shootDirection.setLength(bulletSpeed);

            new HomingRocket(gameWorld, pos.x, pos.y, bulletPath, shootDirection, bulletMass, ROCKET_WIDTH, ROCKET_HEIGHT, removeBulletDelay, damage, shooter, this);

            gameWorld.getGame().getSoundManager().play(Assets.WEAPON_GRENADELAUNCHER_SHOT);
            weaponMaths();

            gameWorld.getGame().getSoundManager().play(Assets.MISSILE_LAUNCH);
        }
    }
}
