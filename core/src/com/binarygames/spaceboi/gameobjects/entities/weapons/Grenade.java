package com.binarygames.spaceboi.gameobjects.entities.weapons;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.binarygames.spaceboi.gameobjects.GameWorld;
import com.binarygames.spaceboi.gameobjects.entities.Bullet;
import com.binarygames.spaceboi.gameobjects.entities.EntityDynamic;

public class Grenade extends Bullet {

    private int shrapnelAmount = 10;
    private Vector2 shrapnelPosition = getBody().getPosition();

    private String shrapnelPath = "game/entities/player/playerShip.png";
    private Vector2 shrapnelSpeed = new Vector2(2, 0);
    private float shrapnelMass = 2;
    private float shrapnelRadius = 2;
    private long shrapnelRemoveDelay = 0;
    private int shrapnelDamage = 1;

    public Grenade(GameWorld gameWorld, float x, float y, String path, Vector2 speed, float mass, float radius, long removeDelay, int damage, EntityDynamic shooter) {
        super(gameWorld, x, y, path, speed, mass, radius, removeDelay, damage, shooter);
    }

    @Override
    public void onRemove() {
        // Grenade explosion
        System.out.println("REMOVE");
        // TODO fixa det problem som finns här pls
        new Bullet(gameWorld, getBody().getPosition().x + rad, getBody().getPosition().y + rad, shrapnelPath, shrapnelSpeed, shrapnelMass, shrapnelRadius, shrapnelRemoveDelay, shrapnelDamage, getShooter());
        for (int i = 1; i + 1 < shrapnelAmount; i++) {
            new Bullet(gameWorld, getBody().getPosition().x + rad, getBody().getPosition().y + rad, shrapnelPath, shrapnelSpeed.rotate(360 / i), shrapnelMass, shrapnelRadius, shrapnelRemoveDelay, shrapnelDamage, getShooter());
        }
    }

}
