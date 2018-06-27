package com.binarygames.spaceboi.gameobjects.entities.weapons;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.binarygames.spaceboi.gameobjects.GameWorld;
import com.binarygames.spaceboi.gameobjects.entities.EntityDynamic;
import com.binarygames.spaceboi.gameobjects.entities.Player;

import static com.binarygames.spaceboi.gameobjects.bodies.BaseBody.PPM;

public abstract class Weapon {

    protected World world;
    protected GameWorld gameWorld;
    protected Sprite sprite;
    protected String path;
    protected float radius;

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
    public void render(SpriteBatch batch, OrthographicCamera camera, Player player) {
        Vector2 weaponPosition = new Vector2(player.getBody().getPosition().x * PPM - player.getMouseCoords().x,
                player.getBody().getPosition().y * PPM - player.getMouseCoords().y);
        weaponPosition.setLength2(1);
        weaponPosition.scl(-(player.getRad() * PPM));

        sprite.setPosition(player.getBody().getPosition().x * PPM - sprite.getWidth() / 2 + weaponPosition.x,
                player.getBody().getPosition().y * PPM - sprite.getHeight() / 2 + weaponPosition.y);

        Vector2 relativeVector = player.getMouseCoords().sub(sprite.getX(), sprite.getY());
        float angleToMouse = MathUtils.atan2(relativeVector.y, relativeVector.x) * MathUtils.radiansToDegrees;
        sprite.setRotation(angleToMouse - 90);

        sprite.draw(batch);
    }
}
