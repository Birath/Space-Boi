package com.binarygames.spaceboi.gameobjects.pickups;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Filter;
import com.binarygames.spaceboi.Assets;
import com.binarygames.spaceboi.gameobjects.GameWorld;
import com.binarygames.spaceboi.gameobjects.entities.Player;

public class HealthPickup extends Pickup {

    private static final int HEALTH_INCREASE = 20;

    public HealthPickup(GameWorld gameWorld, float x, float y, String path, float mass, float radius) {
        super(gameWorld, x, y, path, mass, radius);

    }

    @Override
    public void onHit(Player player) {
        player.increaseHealth(HEALTH_INCREASE);
        gameWorld.getGame().getSoundManager().play(Assets.PLAYER_PICKUP_HEALTH);
        Gdx.app.log("HealthPickup", "Player health: " + player.getHealth());
        remove = true;
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
