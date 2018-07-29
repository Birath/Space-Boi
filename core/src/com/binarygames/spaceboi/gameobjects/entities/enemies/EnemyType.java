package com.binarygames.spaceboi.gameobjects.entities.enemies;

public enum EnemyType {
    CHASER (400, 8, 5, 15, 10, EnemyKind.BIOLOGICAL), SHOOTER(150, 10, 50, 5, 50, EnemyKind.BIOLOGICAL),
    FLYING_SHIP(300, 20, 200, 5, 50, EnemyKind.MECHANICAL), SPAWNER(600, 15, 100, 0, 0, EnemyKind.MECHANICAL);

    private final float mass;
    private final float rad;
    private final int health;
    private final int moveSpeed;
    private final int jumpHeight;
    private final EnemyKind enemyKind;

    private EnemyType(float mass, float rad, int health, int moveSpeed, int jumpHeight, EnemyKind enemyKind) {
        this.mass = mass;
        this.rad = rad;
        this.health = health;
        this.moveSpeed = moveSpeed;
        this.jumpHeight = jumpHeight;
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
}
