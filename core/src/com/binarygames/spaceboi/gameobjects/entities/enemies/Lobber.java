package com.binarygames.spaceboi.gameobjects.entities.enemies;

import com.binarygames.spaceboi.gameobjects.GameWorld;

public class Lobber extends Enemy {
    public Lobber(GameWorld gameWorld, float x, float y, String path, EnemyType enemyType) {
        super(gameWorld, x, y, path, enemyType);
    }

    @Override
    protected void getSounds() {

    }

    @Override
    protected void updateIdle(float delta) {

    }

    @Override
    protected void updateHunting(float delta) {

    }

    @Override
    protected void updateAttacking(float delta) {

    }

    @Override
    protected void updateJumping(float delta) {

    }
}
