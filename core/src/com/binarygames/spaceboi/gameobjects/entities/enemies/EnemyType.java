package com.binarygames.spaceboi.gameobjects.entities.enemies;

public enum EnemyType {
    CHASER (600, 8, 10, 25, 1, 20, EnemyKind.BIOLOGICAL),
    SHOOTER(1000, 10, 70, 20, 50, 50, EnemyKind.BIOLOGICAL),
    FLYING_SHIP(300, 40, 350, 13, 50, 100,  EnemyKind.MECHANICAL),
    SPAWNER(600, 15, 100, 0, 0, 80, EnemyKind.MECHANICAL),
    FINAL_BOSS(10000, 50, 200, 1000, 5, 2, 10000, EnemyKind.MECHANICAL);

    private final float mass;
    private final float rad;
    private final int health;
    private final int moveSpeed;
    private final int jumpHeight;
    private final EnemyKind enemyKind;
    private final int exp;

    private final int width;
    private final int height;

    private EnemyType(float mass, float rad, int health, int moveSpeed, int jumpHeight, int exp, EnemyKind enemyKind) {
        this.mass = mass;
        this.rad = rad;
        this.health = health;
        this.moveSpeed = moveSpeed;
        this.jumpHeight = jumpHeight;
        this.exp = exp;
        this.enemyKind = enemyKind;

        this.width = 0;
        this.height = 0;
    }

    EnemyType(int mass, int width, int height, int health, int moveSpeed, int jumpHeight, int exp,  EnemyKind enemyKind) {
        this.mass = mass;
        this.width = width;
        this.height = height;
        this.health = health;
        this.moveSpeed = moveSpeed;
        this.jumpHeight = jumpHeight;
        this.exp = exp;

        this.rad = 0;
        this.enemyKind = enemyKind;
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

    public EnemyKind getEnemyKind() {
        return enemyKind;
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
