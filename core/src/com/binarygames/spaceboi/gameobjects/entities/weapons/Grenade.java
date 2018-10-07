package com.binarygames.spaceboi.gameobjects.entities.weapons;

import com.badlogic.gdx.math.Vector2;
import com.binarygames.spaceboi.Assets;
import com.binarygames.spaceboi.gameobjects.GameWorld;
import com.binarygames.spaceboi.gameobjects.entities.EntityDynamic;

public class Grenade extends Bullet {

    private static final int SHRAPNEL_AMOUNT = 40;
    private static final String SHRAPNEL_PATH = Assets.WEAPON_BULLET;
    private static final int SHRAPNEL_SPEED = 30;
    private static final float SHRAPNEL_MASS = 10;
    private static final float SHRAPNEL_RADIUS = 1;
    private static final int SHRAPNEL_REMOVE_DELAY = 0; //Increase this feels better but it is simply a workaround
    private static final int SHRAPNEL_DAMAGE = 3;
    private Vector2 shrapnelDirection = new Vector2(2, 0);

    private float timeSinceShot;

    public Grenade(GameWorld gameWorld, float x, float y, String path, Vector2 speed, float mass, float radius, int removeDelay, int damage, EntityDynamic shooter, Weapon weapon) {
        super(gameWorld, x, y, path, speed, mass, radius, removeDelay, damage, shooter, weapon);
    }

    @Override
    public boolean shouldRemove(Vector2 playerPosition) {
        return hasHit && timeSinceShot > removeDelay || timeSinceShot > removeDelay; //???
    }

    @Override
    public void onRemove() {
        // Grenade explosion

        Vector2 shrapnelDirectionWithSpeed = new Vector2(shrapnelDirection.setLength2(1).scl(SHRAPNEL_SPEED));
        shrapnelDirection.setLength2(1);
        shrapnelDirection.scl(rad * PPM);

        float angleDiff = 360 / SHRAPNEL_AMOUNT;

        for (int i = 0; i + 1 <= SHRAPNEL_AMOUNT; i++) {
            Vector2 shrapnelDirectionAngle = shrapnelDirectionWithSpeed.cpy().rotate(angleDiff * i);
            new Bullet(gameWorld, getBody().getPosition().x * PPM + shrapnelDirectionAngle.x, getBody().getPosition().y * PPM + shrapnelDirectionAngle.y,
                    SHRAPNEL_PATH, shrapnelDirectionAngle, SHRAPNEL_MASS, SHRAPNEL_RADIUS, SHRAPNEL_REMOVE_DELAY, SHRAPNEL_DAMAGE, getShooter(), getWeapon());
        }
        gameWorld.getGame().getSoundManager().play(Assets.WEAPON_GRENADELAUNCHER_EXPLOSION);
    }

    @Override
    public void update(float delta) {
        timeSinceShot += delta;
    }
}
