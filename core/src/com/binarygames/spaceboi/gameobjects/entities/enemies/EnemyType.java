package com.binarygames.spaceboi.gameobjects.entities.enemies;

public enum EnemyType {
    CHASER (5, 15, 20), SHOOTER(50, 5, 50), FLYING_SHIP(200, 5, 50);

    private final int health;
    private final int moveSpeed;
    private final int jumpHeight;

    private EnemyType(int health, int moveSpeed, int jumpHeight) {
        this.health = health;
        this.moveSpeed = moveSpeed;
        this.jumpHeight = jumpHeight;
    }

    public int getHealth() {
        return health;
    }

    public int getMoveSpeed() {
        return moveSpeed;
    }

    public int getJumpHeight() {
        return jumpHeight;
    }
}
