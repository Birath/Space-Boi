package com.binarygames.spaceboi.gameobjects.entities.enemies;

public enum EnemyType {
    CHASER (100, 8, 5, 15, 20), SHOOTER(150, 10, 50, 5, 50), FLYING_SHIP(300, 20, 200, 5, 50);

    private final float mass;
    private final float rad;
    private final int health;
    private final int moveSpeed;
    private final int jumpHeight;

    private EnemyType(float mass, float rad, int health, int moveSpeed, int jumpHeight) {
        this.mass = mass;
        this.rad = rad;
        this.health = health;
        this.moveSpeed = moveSpeed;
        this.jumpHeight = jumpHeight;
    }
    public float getMass(){
        return mass;
    }

    public float getRad(){
        return rad;
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
