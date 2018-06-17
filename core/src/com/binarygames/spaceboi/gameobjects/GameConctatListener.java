package com.binarygames.spaceboi.gameobjects;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.binarygames.spaceboi.gameobjects.entities.Bullet;
import com.binarygames.spaceboi.gameobjects.entities.EntityDynamic;
import com.binarygames.spaceboi.gameobjects.entities.Planet;
import com.binarygames.spaceboi.gameobjects.entities.Player;

public class GameConctatListener implements ContactListener {

    private EntityDynamic entity;

    public GameConctatListener() {
    }

    @Override public void beginContact(Contact contact) {
        //ENTITY IS PLAYER
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
        if (fixtureA.getBody().getUserData() == null || fixtureB.getBody().getUserData() == null) return;

        if (Planet.class.isInstance(fixtureA.getBody().getUserData()) &&
            Player.class.isInstance(fixtureB.getBody().getUserData())) {
            entity = (Player) fixtureB.getBody().getUserData();
            entity.hitPlanet((Planet) fixtureA.getBody().getUserData());
        }
        else if (Player.class.isInstance(fixtureA.getBody().getUserData()) &&
                   Planet.class.isInstance(fixtureB.getBody().getUserData())) {
            entity = (Player) fixtureA.getBody().getUserData();
            entity.hitPlanet((Planet) fixtureB.getBody().getUserData());
        }

        //ENTITY IS BULLET
        else if (Planet.class.isInstance(fixtureA.getBody().getUserData()) &&
                Bullet.class.isInstance(fixtureB.getBody().getUserData())) {
            entity = (Bullet) fixtureB.getBody().getUserData();
            entity.hitPlanet((Planet) fixtureA.getBody().getUserData());
            }

        else if (Bullet.class.isInstance(fixtureA.getBody().getUserData()) &&
                Planet.class.isInstance(fixtureB.getBody().getUserData())) {
            entity = (Bullet) fixtureA.getBody().getUserData();
            entity.hitPlanet((Planet) fixtureB.getBody().getUserData());
        }
    }

    @Override public void endContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
        if (fixtureA.getBody().getUserData() == null || fixtureB.getBody().getUserData() == null) return;
        if (Planet.class.isInstance(fixtureA.getBody().getUserData()) &&
            Player.class.isInstance(fixtureB.getBody().getUserData())) {
            entity.leftPlanet();
        } else if (Player.class.isInstance(fixtureA.getBody().getUserData()) &&
                   Planet.class.isInstance(fixtureB.getBody().getUserData())) {
            entity.leftPlanet();
        }
    }

    @Override public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}