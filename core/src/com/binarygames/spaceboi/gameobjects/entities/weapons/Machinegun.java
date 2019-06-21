package com.binarygames.spaceboi.gameobjects.entities.weapons;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.binarygames.spaceboi.Assets;
import com.binarygames.spaceboi.gameobjects.GameWorld;
import com.binarygames.spaceboi.gameobjects.entities.EntityDynamic;
import com.binarygames.spaceboi.gameobjects.entities.Player;

public class Machinegun extends Weapon {

    private static final float WEAPON_WIDTH = 14;
    private static final float WEAPON_HEIGHT = 3;
    private Sound shot;
    private long shotID = 0;

    public Machinegun(GameWorld aGameWorld, EntityDynamic shooter) {
        super(aGameWorld, shooter);

        this.bulletMass = 10;
        this.bulletRadius = 1.5f;
        this.bulletSpeed = 5;
        this.path = Assets.WEAPON_MACHINEGUN;
        this.bulletPath = Assets.WEAPON_BULLET;
        this.recoil = 1;
        this.removeBulletDelay = 0;
        this.timeBetweenShots = 0.12f;
        this.magSize = this.currentMag = 20;
        this.reloadTime = 2;
        this.damage = 4;
        this.name = "Machine gun";

        this.sprite = new Sprite(aGameWorld.getGame().getAssetManager().get(path, Texture.class));
        sprite.setSize(sprite.getWidth() * 0.1f, sprite.getHeight() * 0.1f);
        sprite.setOriginCenter();
        shot = gameWorld.getGame().getAssetManager().get(Assets.WEAPON_MACHINEGUN_SHOT, Sound.class);
    }

    @Override
    public void shoot(Vector2 pos, Vector2 shootDirection) {
        if (canShoot()) {
            shootDirection.scl(bulletSpeed);
            if (this.shooter instanceof Player) {
                Vector2 muzzle = new Vector2(WEAPON_WIDTH, WEAPON_HEIGHT);
                if  (sprite.isFlipY()) {
                    muzzle.scl(1, -1);
                }
                muzzle.rotate(this.rotation);

                pos.add(muzzle);
            }
            new Bullet(gameWorld, pos.x, pos.y, bulletPath, shootDirection, bulletMass, bulletRadius, removeBulletDelay, damage, shooter, this);
            weaponMaths();

            gameWorld.getGame().getSoundManager().play(Assets.WEAPON_MACHINEGUN_SHOT);
        }

        /*
        if (canShoot() && gameWorld.getGame().getPreferences().isSoundEnabled()) {
            if (shotID != 0) {
                shot.stop(shotID);
            }
            shotID = shot.play(gameWorld.getGame().getPreferences().getSoundVolume());
        }
        */
    }

    @Override
    public void onReload() {
        gameWorld.getGame().getSoundManager().play(Assets.WEAPON_MACHINEGUN_RELOAD);
    }
}
