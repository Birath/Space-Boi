package com.binarygames.spaceboi.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.binarygames.spaceboi.SpaceBoi;
import com.binarygames.spaceboi.gameobjects.entities.EntityDynamic;
import com.binarygames.spaceboi.gameobjects.entities.EntityStatic;
import com.binarygames.spaceboi.gameobjects.entities.Planet;

import java.util.ArrayList;
import java.util.List;

public class GameWorld {

    private SpaceBoi game;
    private World world;

    private List<EntityDynamic> dynamicEntities;
    private List<EntityStatic> staticEntities;

    private static final double GRAVITY_CONSTANT = 6.674 * Math.pow(10, -11);

    public GameWorld(SpaceBoi game, World world) {
        this.game = game;
        this.world = world;

        dynamicEntities = new ArrayList<>();
        staticEntities = new ArrayList<>();
    }

    public void update(float delta) {
        applyGravity(delta);
        for (EntityDynamic entity : dynamicEntities) {
            entity.updateMovement();
        }
    }

    public void render(SpriteBatch batch, OrthographicCamera camera) {
        for (EntityStatic entity : staticEntities) {
            entity.render(batch, camera);
        }
        for (EntityDynamic entity : dynamicEntities) {
            entity.render(batch, camera);
        }
    }

    public void applyGravity(float delta) {
        for (EntityDynamic entity : dynamicEntities) {
            Vector2 entityPos = entity.getBody().getPosition();
            Planet closestPlanet = getClosestPlanet(entityPos);

            Vector2 closestPlanetPos = closestPlanet.getBody().getPosition();
            float distance = closestPlanetPos.dst(entityPos);
            if (closestPlanet.getRad() * 1.5 >= distance) {
                float angle = MathUtils.atan2(closestPlanetPos.y - entityPos.y, closestPlanetPos.x - entityPos.x);
                // Set constant gravity while inside a set radius

                // TODO Maybe change to not depend on planet radius. Not sure what else to use tho
                double force = GRAVITY_CONSTANT * closestPlanet.getMass() * entity.getMass() / closestPlanet.getRad();
                float forceX = MathUtils.cos(angle) * (float) force;
                float forceY = MathUtils.sin(angle) * (float) force;
                entity.getBody().applyForceToCenter(forceX, forceY, true);
            }
        }
        world.step(delta, 6, 2);
    }

    private Planet getClosestPlanet(Vector2 bodyPos) {
        float closestDistance = 0.0f;
        Planet closestPlanet = null;
        for (EntityStatic entityStatic : staticEntities) {
            if (Planet.class.isInstance(entityStatic)) {
                Planet planet = (Planet) entityStatic;
                Vector2 planetPos = planet.getBody().getPosition();
                float distance = planetPos.dst(bodyPos) - planet.getRad();
                if (closestDistance == 0.0f) {
                    closestDistance = distance;
                    closestPlanet = planet;
                } else if (distance < closestDistance) {
                    closestDistance = distance;
                    closestPlanet = planet;
                }
            }

        } return closestPlanet;
    }

    public void addDynamicEntity(EntityDynamic entity) {
        dynamicEntities.add(entity);
    }

    public void addStaticEntity(EntityStatic entity) {
        staticEntities.add(entity);
    }

}
