package com.binarygames.spaceboi.gameobjects.entities.weapons;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.binarygames.spaceboi.gameobjects.GameWorld;
import com.binarygames.spaceboi.gameobjects.entities.EntityDynamic;

public abstract class Weapon {

    protected World world;
    protected GameWorld gameWorld;
    private Sprite sprite;
    protected String path;
    protected EntityDynamic shooter;

    protected int damage;

    protected float bulletMass;
    protected float bulletRadius;
    protected float bulletSpeed;
    protected float recoil;

    protected long removeBulletDelay;

    protected boolean reloading;
    protected float reloadTime;
    protected float currentReloadTime;

    protected boolean timeBetweenShotsIsFinished;
    protected float timeBetweenShots;
    protected float currentTimeBetweenShots;

    protected int magSize;
    protected int currentMag = magSize;

    public Weapon(World aWorld, GameWorld aGameWorld, EntityDynamic shooter) {
        this.world = aWorld;
        this.gameWorld = aGameWorld;
        this.shooter = shooter;

        reloading = false;
        timeBetweenShotsIsFinished = false;
        //this.sprite = new Sprite(new Texture(path)); - Sprites shall be added later
        //sprite.setSize(radius * 2, radius * 2);
    }

    public void update(float delta) {
        if (reloading) {
            if (currentReloadTime >= reloadTime) {
                reloading = false;
                currentReloadTime = 0;
                currentMag = magSize;
            } else {
                currentReloadTime += delta;
            }
        }
        if (!timeBetweenShotsIsFinished) {
            if (currentTimeBetweenShots >= timeBetweenShots) {
                timeBetweenShotsIsFinished = true;
                currentTimeBetweenShots = 0;
            } else {
                currentTimeBetweenShots += delta;
            }
        }
    }

    public void Shoot(float x, float y, Vector2 shootDirection) {
        //depends on sub-weapon
    }

    public boolean canShoot() {
        return (currentMag >= 1 && !reloading && timeBetweenShotsIsFinished);
    }

    public void weaponMaths() {
        currentMag--;

        if (currentMag == 0) {
            // Do reload
            reloading = true;
            currentReloadTime = 0;
        } else {
            // Do between shots delay
            timeBetweenShotsIsFinished = false;
            currentTimeBetweenShots = 0;
        }
    }

    public float getRecoil() {
        if (canShoot()) { //A solution to recoil problem that affects the player minimally
            return this.recoil;
        } else {
            return 0;
        }
    }
    public String toString() {
        return getClass().getName();
    }
}
