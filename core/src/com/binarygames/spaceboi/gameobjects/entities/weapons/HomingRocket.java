package com.binarygames.spaceboi.gameobjects.entities.weapons;

import com.badlogic.gdx.math.Vector2;
import com.binarygames.spaceboi.Assets;
import com.binarygames.spaceboi.gameobjects.GameWorld;
import com.binarygames.spaceboi.gameobjects.entities.EntityDynamic;

public class HomingRocket extends Bullet {

    public static final float ROCKET_ROTATION_SPEED = 8;

    private float timeSinceShot;
    private Vector2 toPlayer;


    public HomingRocket(GameWorld gameWorld, float x, float y, String path, Vector2 speed,
                        float mass, float radius, int removeDelay, int damage, EntityDynamic shooter, Weapon weapon) {
        super(gameWorld, x, y, path, speed, mass, radius, removeDelay, damage, shooter, weapon);
        toPlayer = speed.cpy();
    }

    @Override
    public boolean shouldRemove(Vector2 playerPosition) {
        return hasHit || timeSinceShot > removeDelay;
    }

    @Override
    public void update(float delta) {
        timeSinceShot += delta;

        toPlayer.lerp(new Vector2(gameWorld.getPlayer().getBody().getPosition().x - this.getBody().getPosition().x,
                gameWorld.getPlayer().getBody().getPosition().y - this.getBody().getPosition().y), delta * ROCKET_ROTATION_SPEED);

        toPlayer.setLength2(1).scl(this.getBody().getLinearVelocity().len());
        this.getBody().setLinearVelocity(toPlayer);
    }

    @Override
    public void onRemove() {
        gameWorld.getGame().getSoundManager().play(Assets.MISSILE_HIT);
    }

}
