package com.binarygames.spaceboi.gameobjects.entities.weapons;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.binarygames.spaceboi.gameobjects.GameWorld;

public abstract class Weapon {

    protected World world;
    protected GameWorld gameWorld;
    private Sprite sprite;
    protected String path;

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

    public Weapon(World aWorld, GameWorld aGameWorld) {
        this.world = aWorld;
        this.gameWorld = aGameWorld;
        //this.sprite = new Sprite(new Texture(path)); - Sprites shall be added later
        //sprite.setSize(radius * 2, radius * 2);
    }

    public void Shoot(float x, float y, Vector2 shootDirection) {
        //depends on sub-weapon
    }

    public boolean canShoot() {
        if (currentMag > 1 && !reloading && timeBetweenShotsIsFinished) {
            return true;
        }
        return false;
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
        return this.recoil;
    }

}
