package com.binarygames.spaceboi.gameobjects.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.binarygames.spaceboi.gameobjects.GameWorld;
import com.binarygames.spaceboi.gameobjects.entities.weapons.Machinegun;
import com.binarygames.spaceboi.gameobjects.entities.weapons.Weapon;

public class Enemy extends EntityDynamic {

    private Vector2 toPlanet = new Vector2(0, 0);
    private Vector2 toPlayer = new Vector2(0, 0);
    private Vector2 perpen = new Vector2(0, 0);
    private GameWorld gameWorld;
    private Player player;

    private Weapon weapon;

    private float moveSpeed = 5;


    public Enemy(World world, float x, float y, String path, float mass, float radius, GameWorld gameWorld) {
        super(gameWorld, x, y, path, mass, radius);
        this.gameWorld = gameWorld;
        player = gameWorld.getPlayer();
        body.setUserData(this);

        this.health = 100;
        this.weapon = new Machinegun(gameWorld, this);
    }

    @Override
    public void update(float delta) {
        updateToPlanet();
        updateWalkingDirection();
        weapon.update(delta);
        if (entityState == ENTITY_STATE.STANDING) {
            perpen.set(-toPlanet.y, toPlanet.x);
            perpen.setLength2(1);
            perpen.scl(moveSpeed);

            if (Shoot()) {

            }
            //MOVE
            else if (moveRight) {
                body.setLinearVelocity(perpen);
            } else if (moveLeft) {
                body.setLinearVelocity(-perpen.x, -perpen.y);
            } else {
                body.setLinearVelocity(0, 0);
            }
        }
    }

    private void updateToPlanet() {
        toPlanet = new Vector2(planetBody.getPosition().x - body.getPosition().x, planetBody.getPosition().y - body.getPosition().y);
        toPlanet.setLength2(1);
        toPlanet.scl(50);
    }

    private void updateWalkingDirection() {
        toPlayer = player.getBody().getPosition().sub(this.getBody().getPosition()); //From enemy to player

        float angle = perpen.angle(toPlayer);
        if (Math.abs(angle) < 90) {
            moveLeft = false;
            moveRight = true;
        } else {
            moveRight = false;
            moveLeft = true;
        }
    }

    private boolean Shoot() {
        //Calculating if shooting is to happen
        Vector2 awayFromPlanet = new Vector2(-toPlanet.x, -toPlanet.y);
        float angle = awayFromPlanet.angle(toPlayer);

        if (Math.abs(angle) < 110) {
            Vector2 recoil = new Vector2(-toPlayer.x, -toPlayer.y);
            recoil.setLength2(1);

            //Setting recoil
            recoil.scl(weapon.getRecoil());
            body.setLinearVelocity(recoil);

            //Creating the bullet
            recoil.setLength2(1);
            recoil.scl(-(rad * PPM));
            Vector2 shootFrom = new Vector2(body.getPosition().x * PPM + recoil.x, body.getPosition().y * PPM + recoil.y);

            weapon.Shoot(shootFrom.x, shootFrom.y, recoil);
        }
        return (Math.abs(angle) < 110);
    }
}