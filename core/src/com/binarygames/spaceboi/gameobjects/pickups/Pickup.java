package com.binarygames.spaceboi.gameobjects.pickups;

import com.binarygames.spaceboi.gameobjects.GameWorld;
import com.binarygames.spaceboi.gameobjects.entities.EntityDynamic;
import com.binarygames.spaceboi.gameobjects.entities.Player;

public abstract class Pickup extends EntityDynamic {
    protected Pickup(GameWorld gameWorld, float x, float y, String path, float mass, float radius) {
        super(gameWorld, x, y, path, mass, radius);
    }

    public abstract void onHit(Player player);
}
