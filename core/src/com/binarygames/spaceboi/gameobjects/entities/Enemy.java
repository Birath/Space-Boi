package com.binarygames.spaceboi.gameobjects.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.binarygames.spaceboi.gameobjects.GameWorld;

public class Enemy extends EntityDynamic {

    private Vector2 toPlanet = new Vector2(0, 0);
    private Vector2 toPlayer = new Vector2(0, 0);
    private Vector2 perpen = new Vector2(0,0);
    private GameWorld gameWorld;
    private Player player;

    private float moveSpeed = 5;


    public Enemy(World world, float x, float y, String path, float mass, float radius, GameWorld gameWorld){
        super(world, x, y, path, mass, radius);
        this.gameWorld = gameWorld; //Add this to entityDynamic?
        player = gameWorld.getPlayer();
        body.setUserData(this);
    }
    @Override
    public void updateMovement() {
        if (entityState == ENTITY_STATE.STANDING) {
            updateToPlanet(); //Probably doesnt matter if these are inside the if or not
            updateWalkingDirection();

            perpen.set(-toPlanet.y, toPlanet.x);
            perpen.setLength2(1);
            perpen.scl(moveSpeed);

            //MOVE
            if (moveRight) {
                body.setLinearVelocity(perpen);
            } else if (moveLeft) {
                perpen.scl(1); //För att stoppa copyrightstrike
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
    private void updateWalkingDirection(){
        toPlayer = player.getBody().getPosition().sub(this.getBody().getPosition()); //From enemy to player
        //System.out.println(toPlayer);

        float angle = perpen.angle(toPlayer);
        System.out.println(Math.abs(angle));
        if(Math.abs(angle) < 90){
            moveLeft = false;
            moveRight = true;
        }
        else{
            moveRight = false;
            moveLeft = true;
        }
    }
}