package com.binarygames.spaceboi.gameobjects.entities.enemies;

import com.binarygames.spaceboi.Assets;
import com.binarygames.spaceboi.gameobjects.GameWorld;

public class Spawner extends Enemy {

    private float timeSinceLastSpawn;
    private static final float SPAWN_DELAY = 2; // Seconds

    public Spawner(GameWorld gameWorld, float x, float y, String path, EnemyType enemyType) {
        super(gameWorld, x, y, path, enemyType);
        timeSinceLastSpawn = 0;
    }

    @Override
    protected void updateIdle(float delta) {
        standStill();
    }

    @Override
    protected void updateHunting(float delta) {
        if (timeSinceLastSpawn > 2 * SPAWN_DELAY) { //Spawn less often if player is on another planet
            spawnChaser();
            timeSinceLastSpawn = 0;
        } else {
            timeSinceLastSpawn += delta;
        }
    }

    @Override
    protected void updateAttacking(float delta) {
        if (timeSinceLastSpawn > SPAWN_DELAY) {
            spawnChaser();
            timeSinceLastSpawn = 0;
        } else {
            timeSinceLastSpawn += delta;
        }
    }

    @Override
    protected void updateJumping(float delta) {
        //Should not happen
    }

    private void spawnChaser() {
        float offset = this.getRad() + EnemyType.CHASER.getRad();
        Chaser chaser = new Chaser(gameWorld, this.getBody().getPosition().x * PPM + offset,
            this.getBody().getPosition().y * PPM, Assets.PLANET_MOON);
        gameWorld.addDynamicEntity(chaser);
    }
}
