package com.binarygames.spaceboi.gameobjects.entities.weapons;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.binarygames.spaceboi.gameobjects.GameWorld;
import com.binarygames.spaceboi.gameobjects.entities.EntityDynamic;
import com.binarygames.spaceboi.gameobjects.entities.Player;
import com.binarygames.spaceboi.gameobjects.pickups.WeaponAttachments.WeaponAttachment;

import java.util.ArrayList;
import java.util.List;

import static com.binarygames.spaceboi.gameobjects.bodies.BaseBody.PPM;

public abstract class Weapon {

    protected List<WeaponAttachment> attachments;

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

    String name;

    public Weapon(GameWorld gameWorld, EntityDynamic shooter) {
        this.world = gameWorld.getWorld();
        this.gameWorld = gameWorld;
        this.shooter = shooter;

        reloading = false;
        timeBetweenShotsIsFinished = false;

        attachments = new ArrayList<>();

        gameWorld.addWeapon(this);
    }

    public void update(float delta) {
        if (reloading) {
            if (currentMag != 0 && gameWorld.getPlayer().isMouseHeld()) { //Interrupt reloading if you want to shoot and have ammo
                reloading = false;
                currentReloadTime = 0;
            } else if (currentReloadTime >= reloadTime) { //If done reloading
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

    public abstract void Shoot(float x, float y, Vector2 shootDirection);

    public void timeBetweenShotsStart() {

    }

    public void onReload() {

    }

    public boolean canShoot() {
        if (shooter instanceof Player && gameWorld.getPlayer().hasInfiniteAmmo()) {
            return timeBetweenShotsIsFinished;
        }
        return (currentMag >= 1 && !reloading && timeBetweenShotsIsFinished);
    }

    public void weaponMaths() {
        currentMag--;

        if (currentMag == 0) {
            // Do reload
            reload();
        } else {
            // Do between shots delay
            timeBetweenShotsIsFinished = false;
            currentTimeBetweenShots = 0;
            timeBetweenShotsStart();
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

    public int getCurrentMag() {
        return currentMag;
    }

    public int getMagSize() {
        return magSize;
    }

    public boolean isReloading() {
        return reloading;
    }

    public boolean isTimeBetweenShotsIsFinished() {
        return timeBetweenShotsIsFinished;
    }

    public float getCurrentReloadTime() {
        return currentReloadTime;
    }

    public float getReloadTime() {
        return reloadTime;
    }

    public void reload() {
        if ((!(currentMag == magSize)) && !reloading) {
            reloading = true;
            currentReloadTime = 0;
            onReload();
        }
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public float getBulletSpeed() {
        return bulletSpeed;
    }

    public void setBulletSpeed(float bulletSpeed) {
        this.bulletSpeed = bulletSpeed;
    }

    public void setRecoil(float recoil) {
        this.recoil = recoil;
    }

    public void setReloadTime(float reloadTime) {
        this.reloadTime = reloadTime;
    }

    public float getTimeBetweenShots() {
        return timeBetweenShots;
    }

    public void setTimeBetweenShots(float timeBetweenShots) {
        this.timeBetweenShots = timeBetweenShots;
    }

    public void setMagSize(int magSize) {
        this.magSize = magSize;
    }

    public EntityDynamic getShooter() {
        return this.shooter;
    }

    public List<WeaponAttachment> getAttachments() {
        return attachments;
    }

    public boolean addAttachement(WeaponAttachment attachment) {
        if (attachments.size() < 3) {
            attachments.add(attachment);
            attachment.setEquipped(true);
            return true;
        }
        return false;
    }

    public boolean removeAttachement(WeaponAttachment attachment) {
        if (attachments.contains(attachment)) {
            attachments.remove(attachment);
            attachment.setEquipped(false);
            return true;
        }
        return false;
    }

    public String getName() {
        return name;
    }
}
