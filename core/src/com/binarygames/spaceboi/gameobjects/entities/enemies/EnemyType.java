package com.binarygames.spaceboi.gameobjects.entities.enemies;

public enum EnemyType {
    CHASER (400, 8, 5, 15, 10, 20),
    SHOOTER(150, 10, 50, 5, 50, 50),
    FLYING_SHIP(300, 20, 200, 5, 50, 100),
    SPAWNER(600, 15, 100, 0, 0, 80),
    FINAL_BOSS(100000, 50, 200, 1000, 5, 2, 10000);

    private final float mass;
    private final float rad;
    private final int health;
    private final int moveSpeed;
    private final int jumpHeight;
    private final int exp;

    private final int width;
    private final int height;

    private EnemyType(float mass, float rad, int health, int moveSpeed, int jumpHeight, int exp) {
        this.mass = mass;
        this.rad = rad;
        this.health = health;
        this.moveSpeed = moveSpeed;
        this.jumpHeight = jumpHeight;
        this.exp = exp;

        this.width = 0;
        this.height = 0;
    }

    EnemyType(int mass, int width, int height, int health, int moveSpeed, int jumpHeight, int exp) {
        this.mass = mass;
        this.width = width;
        this.height = height;
        this.health = health;
        this.moveSpeed = moveSpeed;
        this.jumpHeight = jumpHeight;
        this.exp = exp;

        this.rad = 0;
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

    public int getExp() {
        return exp;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
