package com.binarygames.spaceboi.gameobjects.entities.weapons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.binarygames.spaceboi.Assets;
import com.binarygames.spaceboi.gameobjects.GameWorld;
import com.binarygames.spaceboi.gameobjects.entities.EntityDynamic;

public class GrenadeLauncher extends Weapon {

    public static final int WIDTH = 30;
    public static final int HEIGHT = 25;

    public GrenadeLauncher(GameWorld aGameWorld, EntityDynamic shooter) {
        super(aGameWorld, shooter);

        this.bulletMass = 40;
        this.bulletRadius = 4f;
        this.bulletSpeed = 2;
        this.path = Assets.WEAPON_GRENADELAUNCHER;
        this.bulletPath = Assets.WEAPON_GRENADE;
        this.recoil = 15;
        this.removeBulletDelay = 2;
        this.timeBetweenShots = 1;
        this.magSize = this.currentMag = 4;
        this.reloadTime = 4;
        this.damage = 0;
        this.name = "Grenade launcher";

        this.radius = 5;
        this.sprite = new Sprite(aGameWorld.getGame().getAssetManager().get(path, Texture.class));
        float scale = Math.min(WIDTH /sprite.getWidth(), HEIGHT /sprite.getHeight());
        sprite.setSize(sprite.getWidth() * scale, sprite.getHeight() * scale);
        sprite.setOriginCenter();
        //sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
    }

    @Override
    public void shoot(Vector2 pos, Vector2 shootDirection) {
        if (canShoot()) {
            shootDirection.scl(bulletSpeed);
            new Grenade(gameWorld, pos.x, pos.y, bulletPath, shootDirection, bulletMass, bulletRadius, removeBulletDelay, damage, shooter, this);
            weaponMaths();

            gameWorld.getGame().getSoundManager().play(Assets.WEAPON_GRENADELAUNCHER_SHOT);
        }
    }

    @Override
    public void onReload() {
        gameWorld.getGame().getSoundManager().play(Assets.WEAPON_GRENADELAUNCHER_RELOAD);
    }
}
