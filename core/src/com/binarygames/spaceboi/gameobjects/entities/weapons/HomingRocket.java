package com.binarygames.spaceboi.gameobjects.entities.weapons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.binarygames.spaceboi.Assets;
import com.binarygames.spaceboi.gameobjects.GameWorld;
import com.binarygames.spaceboi.gameobjects.entities.EntityDynamic;

public class HomingRocket extends Bullet {

    private float timeSinceShot;

    public HomingRocket(GameWorld gameWorld, float x, float y, String path, Vector2 speed,
                        float mass, float radius, int removeDelay, int damage, EntityDynamic shooter, Weapon weapon) {
        super(gameWorld, x, y, path, speed, mass, radius, removeDelay, damage, shooter, weapon);
    }

    @Override
    public boolean shouldRemove(Vector2 playerPosition) {
        return hasHit || timeSinceShot > removeDelay;
    }

    @Override
    public void update(float delta) {
        timeSinceShot += delta;
        Vector2 toPlayer = new Vector2(gameWorld.getPlayer().getBody().getPosition().x - this.getBody().getPosition().x,
                gameWorld.getPlayer().getBody().getPosition().y - this.getBody().getPosition().y);

        toPlayer.setLength2(1).scl(this.getBody().getLinearVelocity().len());
        this.getBody().setLinearVelocity(toPlayer);
    }

    @Override
    public void onRemove() {
        gameWorld.getGame().getSoundManager().play(Assets.MISSILE_HIT);
    }

}
