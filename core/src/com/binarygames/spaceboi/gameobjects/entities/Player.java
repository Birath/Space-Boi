package com.binarygames.spaceboi.gameobjects.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public class Player extends EntityDynamic {

    private PLAYER_STATE playerState;

    private Body planetBody;

    protected boolean mouseHeld = false;

    public Player(World world, float x, float y, String path, float mass, float radius) {
        super(world, x, y, path, mass, radius);
        body.setUserData("player");
    }

    @Override
    public void updateMovement() {
        if (playerState == PLAYER_STATE.STANDING) {
            Vector2 toPlanet = body.getPosition().sub(planetBody.getPosition());
            toPlanet.setLength(1);
            toPlanet.scl(10);

            Vector2 perpen = new Vector2(toPlanet.y, -toPlanet.x);
            perpen.setLength(1);
            perpen.scl(20);

            //JUMP
            if (moveUp) {
                System.out.println("JUMPING");
                specialVel.add(toPlanet);
            }

            //MOVE
            if (moveRight){
                baseVel.set(perpen);
            }
            else if (moveLeft){
                baseVel.set(-perpen.x, -perpen.y);
            }
            else{
                baseVel.setZero();
            }
        }
        else if (playerState == PLAYER_STATE.JUMPING){
            body.setLinearVelocity(0,0);
            specialVel.setZero();
        }
        Vector2 recoil = new Vector2(Gdx.input.getX() - body.getPosition().x, Gdx.input.getY() - body.getPosition().y);
        recoil.setLength(1); //Normerar så att endast riktningen bevaras
        recoil.scl(2); //Här kan man ta en konstant som beror på vapnet

        //SHOOT
        if (mouseHeld){
            System.out.println("SHOOTING");
            specialVel.set(-recoil.x + specialVel.x, -recoil.y + specialVel.y); //Konvertering????
        }
        body.setLinearVelocity(baseVel.add(specialVel));
    }

    public PLAYER_STATE getPlayerState() {
        return playerState;
    }

    public void setPlayerState(PLAYER_STATE playerState) {
        this.playerState = playerState;
    }

    public void setPlanetBody(Body planetBody) {
        this.planetBody = planetBody;
    }

    public void setMouseHeld(boolean mouseHeld){
        this.mouseHeld = mouseHeld;
    }
}


