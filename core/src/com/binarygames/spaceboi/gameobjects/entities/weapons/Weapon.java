package com.binarygames.spaceboi.gameobjects.entities.weapons;

import com.badlogic.gdx.Gdx;
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
    protected String bulletPath;
    protected float radius;

    private boolean spriteFlip;

    protected EntityDynamic shooter;

    protected int damage;

    protected float bulletMass;
    protected float bulletRadius;
    protected float bulletSpeed;
    protected float recoil;

    protected int removeBulletDelay;

    protected boolean reloading;
    protected float reloadTime;
    protected float currentReloadTime;

    protected boolean timeBetweenShotsIsFinished;
    protected float timeBetweenShots;
    protected float currentTimeBetweenShots;

    protected int magSize;
    protected int currentMag = magSize;

    private float xpFactor = 1f;

    private int bioDamage = 0;
    private int mechDamage = 0;
    private int lifeSteal = 0;
    private int slow = 0;

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
        if (!(shooter instanceof Player && gameWorld.getPlayer().hasInfiniteAmmo())) {
            currentMag--;
        }

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

    public float getBulletSpeed() {
        return bulletSpeed;
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
        Vector2 relativeVector = player.getMouseCoords().cpy().sub(sprite.getX(), sprite.getY());
        float angleToMouse = MathUtils.atan2(relativeVector.y, relativeVector.x) * MathUtils.radiansToDegrees;
        //sprite.setRotation(angleToMouse - 90);
        sprite.setRotation(angleToMouse);

        // Image flipping for player
        boolean shouldFlip = Gdx.input.getX() < Gdx.graphics.getWidth() / 2;
        if(shouldFlip != spriteFlip) {
            spriteFlip = shouldFlip;
            sprite.flip(false, true);
        }

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

    public boolean addAttachment(WeaponAttachment attachment) {
        if (attachments.size() < 3) {
            attachments.add(attachment);
            attachment.setEquipped(true);
            attachment.applyAttachment(this);
            return true;
        }
        return false;
    }

    public boolean removeAttachment(WeaponAttachment attachment) {
        if (attachments.contains(attachment)) {
            attachments.remove(attachment);
            attachment.setEquipped(false);
            attachment.removeAttachment(this);
            return true;
        }
        return false;
    }

    public float getXpFactor() {
        return xpFactor;
    }

    public void setXpFactor(float factor) {
        xpFactor = factor;
    }

    public String getName() {
        return name;
    }

    public int getBioDamage() {
        return bioDamage;
    }

    public void setBioDamage(int bioDamage) {
        this.bioDamage = bioDamage;
    }

    public int getMechDamage() {
        return mechDamage;
    }

    public void setMechDamage(int mechDamage) {
        this.mechDamage = mechDamage;
    }

    public int getLifeSteal() {
        return lifeSteal;
    }

    public void setLifeSteal(int lifeSteal) {
        this.lifeSteal = lifeSteal;
    }

    public int getSlow() {
        return slow;
    }

    public void setSlow(int slow) {
        this.slow = slow;
    }
}
