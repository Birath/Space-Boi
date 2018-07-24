package com.binarygames.spaceboi.gameobjects.entities.enemies;

import com.badlogic.gdx.utils.TimeUtils;
import com.binarygames.spaceboi.Assets;
import com.binarygames.spaceboi.gameobjects.GameWorld;

import java.sql.Time;

public class Spawner extends Enemy {

    private long lastSpawn;
    private long spawnDelay = 2000;

    public Spawner(GameWorld gameWorld, float x, float y, String path, EnemyType enemyType) {
        super(gameWorld, x, y, path, enemyType);
        lastSpawn = 0;
    }

    @Override
    protected void updateIdle() {
        standStill();
    }

    @Override
    protected void updateHunting() {
        if(TimeUtils.millis() - lastSpawn < 2*spawnDelay){ //Spawn less often if player is on another planet
            spawnChaser();
            lastSpawn = TimeUtils.millis();
        }
    }

    @Override
    protected void updateAttacking() {
        if(TimeUtils.millis() - lastSpawn < spawnDelay){
            spawnChaser();
            lastSpawn = TimeUtils.millis();
        }
    }

    @Override
    protected void updateJumping() {
        //Should not happen
    }
    private void spawnChaser(){
        float offset = this.getRad() + EnemyType.CHASER.getRad();
        Chaser chaser = new Chaser(gameWorld, this.getBody().getPosition().x + offset,
                this.getBody().getPosition().y, Assets.PLANET_MOON);
        gameWorld.addDynamicEntity(chaser);
    }
}
