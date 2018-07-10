package com.binarygames.spaceboi.gameobjects.pickups;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Filter;
import com.binarygames.spaceboi.gameobjects.GameWorld;
import com.binarygames.spaceboi.gameobjects.entities.Player;

public class HealthPickup extends Pickup {

    private static final int HEALTH_INCREASE = 20;
    private boolean remove = false;

    public HealthPickup(GameWorld gameWorld, float x, float y, String path, float mass, float radius) {
        super(gameWorld, x, y, path, mass, radius);


    }

    @Override
    public void onHit(Player player) {
        player.increaseHealth(HEALTH_INCREASE);
        Gdx.app.log("HealthPickup", "Player health: " + player.getHealth());
        remove = true;
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public boolean shouldRemove(Vector2 playerPosition) {
        return remove;
    }

    @Override
    public void onRemove() {
        // OnRemove
    }

    @Override
    public boolean isAffectedByGravity() {
        return true;
    }
}
