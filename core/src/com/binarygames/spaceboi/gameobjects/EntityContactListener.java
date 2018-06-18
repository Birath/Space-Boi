package com.binarygames.spaceboi.gameobjects;

import com.badlogic.gdx.physics.box2d.*;
import com.binarygames.spaceboi.gameobjects.entities.Bullet;
import com.binarygames.spaceboi.gameobjects.entities.Planet;
import com.binarygames.spaceboi.gameobjects.entities.Player;

public class EntityContactListener implements ContactListener {


    public EntityContactListener() {
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
        if (fixtureA.getBody().getUserData() == null || fixtureB.getBody().getUserData() == null) return;

        //ENTITY IS PLAYER
        if (Planet.class.isInstance(fixtureA.getBody().getUserData()) &&
                Player.class.isInstance(fixtureB.getBody().getUserData())) {
            Player player = (Player) fixtureB.getBody().getUserData();
            player.hitPlanet((Planet) fixtureA.getBody().getUserData());
        } else if (Player.class.isInstance(fixtureA.getBody().getUserData()) &&
                Planet.class.isInstance(fixtureB.getBody().getUserData())) {
            Player player = (Player) fixtureA.getBody().getUserData();
            player.hitPlanet((Planet) fixtureB.getBody().getUserData());
        }

        //ENTITY IS BULLET
        else if (Planet.class.isInstance(fixtureA.getBody().getUserData()) &&
                Bullet.class.isInstance(fixtureB.getBody().getUserData())) {
            Bullet bullet = (Bullet) fixtureB.getBody().getUserData();
            bullet.hitPlanet((Planet) fixtureA.getBody().getUserData());
        } else if (Bullet.class.isInstance(fixtureA.getBody().getUserData()) &&
                Planet.class.isInstance(fixtureB.getBody().getUserData())) {
            Bullet bullet = (Bullet) fixtureA.getBody().getUserData();
            bullet.hitPlanet((Planet) fixtureB.getBody().getUserData());
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
        if (fixtureA.getBody().getUserData() == null || fixtureB.getBody().getUserData() == null) return;
        if (Planet.class.isInstance(fixtureA.getBody().getUserData()) &&
                Player.class.isInstance(fixtureB.getBody().getUserData())) {
            Player player = (Player) fixtureB.getBody().getUserData();
            player.leftPlanet();
        } else if (Player.class.isInstance(fixtureA.getBody().getUserData()) &&
                Planet.class.isInstance(fixtureB.getBody().getUserData())) {
            Player player = (Player) fixtureA.getBody().getUserData();
            player.leftPlanet();
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}