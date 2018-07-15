package com.binarygames.spaceboi.gameobjects.entities.weapons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.binarygames.spaceboi.Assets;
import com.binarygames.spaceboi.gameobjects.GameWorld;
import com.binarygames.spaceboi.gameobjects.entities.weapons.Bullet;
import com.binarygames.spaceboi.gameobjects.entities.EntityDynamic;

public class Grenade extends Bullet {

    private int shrapnelAmount = 20;
    private long timeTouched;
    private long removeDelay;

    private String shrapnelPath = Assets.PLAYER;
    private Vector2 shrapnelDirection = new Vector2(2, 0);
    private int shrapnelSpeed = 50;
    private float shrapnelMass = 2;
    private float shrapnelRadius = 1;
    private long shrapnelRemoveDelay = 0; //Increase this feels better but it is simply a workaround
    private int shrapnelDamage = 3;

    public Grenade(GameWorld gameWorld, float x, float y, String path, Vector2 speed, float mass, float radius, long removeDelay, int damage, EntityDynamic shooter) {
        super(gameWorld, x, y, path, speed, mass, radius, removeDelay, damage, shooter);
        this.removeDelay = removeDelay;
        timeTouched = TimeUtils.millis();
    }


    @Override
    public boolean shouldRemove(Vector2 playerPosition) {
        return hasHit && (TimeUtils.millis() - timeTouched) > removeDelay || (TimeUtils.millis() - timeTouched) > removeDelay;
    }

    @Override
    public void onRemove() {
        // Grenade explosion

        Vector2 shrapnelDirectionWithSpeed = new Vector2(shrapnelDirection.setLength2(1).scl(shrapnelSpeed));
        shrapnelDirection.setLength2(1);
        shrapnelDirection.scl(rad * PPM);

        float angleDiff = 360 / shrapnelAmount;

        for (int i = 1; i + 1 < shrapnelAmount; i++) {
            new Bullet(gameWorld, getBody().getPosition().x * PPM + shrapnelDirection.x, getBody().getPosition().y * PPM + shrapnelDirection.y,
                    shrapnelPath, shrapnelDirectionWithSpeed.rotate(angleDiff * i), shrapnelMass, shrapnelRadius, shrapnelRemoveDelay, shrapnelDamage, getShooter());
        }

        gameWorld.getGame().getSoundManager().play(Assets.WEAPON_GRENADELAUNCHER_EXPLOSION);
    }
}
