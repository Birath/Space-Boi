package com.binarygames.spaceboi.gameobjects.entities.weapons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.binarygames.spaceboi.Assets;
import com.binarygames.spaceboi.gameobjects.GameWorld;
import com.binarygames.spaceboi.gameobjects.entities.EntityDynamic;
import com.binarygames.spaceboi.gameobjects.entities.Player;

public class Shotgun extends Weapon {

    private static final int WEAPON_WIDTH = 15;
    private static final int WEAPON_HEIGHT = 5;

    private int spread = 10;
    private int numberOfBullets = 7;

    public Shotgun(GameWorld aGameWorld, EntityDynamic shooter) {
        super(aGameWorld, shooter);

        this.bulletMass = 10;
        this.bulletRadius = 1.5f;
        this.bulletSpeed = 45;
        this.path = Assets.WEAPON_SHOTGUN;
        this.bulletPath = Assets.WEAPON_BULLET;
        this.recoil = 20;
        this.removeBulletDelay = 0;
        this.timeBetweenShots = 1;
        this.magSize = this.currentMag = 2;
        this.reloadTime = 2.2f;
        this.damage = 10;
        this.name = "Shotgun";

        this.length = 15;
        this.offset = 15;


        //Sprite setup
        this.sprite = new Sprite(aGameWorld.getGame().getAssetManager().get(path, Texture.class));
        float scale = 0.1f;
        sprite.setSize(sprite.getWidth() * scale, sprite.getHeight() * scale);
        sprite.setOriginCenter();
    }

    @Override
    public void shoot(Vector2 pos, Vector2 shootDirection) {
        if (canShoot()) {
            shootDirection.nor().scl(bulletSpeed);
            if (this.shooter instanceof Player) {
                Vector2 muzzle = new Vector2(WEAPON_WIDTH, WEAPON_HEIGHT);
                if  (sprite.isFlipY()) {
                    muzzle.scl(1, -1);
                }
                muzzle.rotate(this.rotation);

                pos.add(muzzle);
            }
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
