package com.binarygames.spaceboi.gameobjects.entities.weapons;

import com.badlogic.gdx.math.Vector2;
import com.binarygames.spaceboi.gameobjects.GameWorld;
import com.binarygames.spaceboi.gameobjects.entities.EntityDynamic;

public class HomingRocket extends Bullet {
    public HomingRocket(GameWorld gameWorld, float x, float y, String path, Vector2 speed,
                        float mass, float radius, long removeDelay, int damage, EntityDynamic shooter) {
        super(gameWorld, x, y, path, speed, mass, radius, removeDelay, damage, shooter);
    }
    @Override
    public void update(float delta){
        Vector2 toPlayer = new Vector2(gameWorld.getPlayer().getBody().getPosition().x - this.getBody().getPosition().x,
                 gameWorld.getPlayer().getBody().getPosition().y - this.getBody().getPosition().y);

        toPlayer.setLength2(1).scl(5); // 5 är magisk bulletspeed
        this.getBody().setLinearVelocity(toPlayer);

    }

}
