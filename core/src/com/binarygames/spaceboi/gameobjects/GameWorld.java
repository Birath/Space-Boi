package com.binarygames.spaceboi.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.binarygames.spaceboi.SpaceBoi;
import com.binarygames.spaceboi.gameobjects.entities.*;

import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;
import java.util.List;

public class GameWorld {

    private SpaceBoi game;
    private World world;
    private Player player;

    private List<EntityDynamic> dynamicEntities;
    private List<EntityStatic> staticEntities;

    private List<EntityDynamic> addDynamicEntities = new ArrayList<>();

    private static final double GRAVITY_CONSTANT = 6.674 * Math.pow(10, -11);

    public GameWorld(SpaceBoi game, World world) {
        this.game = game;
        this.world = world;

        dynamicEntities = new ArrayList<>();
        staticEntities = new ArrayList<>();


    }

    public void createWorld() {
        Player player = new Player(world, 0, 0, "playerShip.png", 500, 2, this);
        addDynamicEntity(player);
        this.player = player;

        Planet planet1 = new Planet(world, 35, 30, "moon.png",(float) Math.pow(4.6 * 10, 7), 20);
        addStaticEntity(planet1);
        Planet planet2 = new Planet(world, 100, 30, "moon.png", (float) Math.pow(4.0 * 10, 7), 15);
        addStaticEntity(planet2);

        world.setContactListener(new GameConctatListener(player));

    }

    public void update(float delta) {
        applyGravity(delta, dynamicEntities);
        for (EntityDynamic entity : dynamicEntities) {
            entity.updateMovement();
        }
        dynamicEntities.addAll(addDynamicEntities);
        addDynamicEntities.clear();
    }

    public void render(SpriteBatch batch, OrthographicCamera camera) {
        for (EntityStatic entity : staticEntities) {
            entity.render(batch, camera);
        }
        for (EntityDynamic entity : dynamicEntities) {
            entity.render(batch, camera);
        }
    }

    public void applyGravity(float delta, List<EntityDynamic> dynamicObjectList) {
        for (EntityDynamic entity : dynamicObjectList) {

            Vector2 entityPos = entity.getBody().getPosition();
            Planet closestPlanet = getClosestPlanet(entityPos);

            Vector2 closestPlanetPos = closestPlanet.getBody().getPosition();
            float distance = closestPlanetPos.dst(entityPos);
            if (closestPlanet.getRad() * 2 >= distance) {
                float angle = MathUtils.atan2(closestPlanetPos.y - entityPos.y, closestPlanetPos.x - entityPos.x);
                // Set constant gravity while inside a set radius

                // TODO Maybe change to not depend on planet radius. Not sure what else to use tho - Maybe remove radius and then change Gravity Constant? //ALbin
                double force = GRAVITY_CONSTANT * closestPlanet.getMass() * entity.getMass() / closestPlanet.getRad();
                float forceX = MathUtils.cos(angle) * (float) force;
                float forceY = MathUtils.sin(angle) * (float) force;
                entity.getBody().applyForceToCenter(forceX, forceY, true);
            }
        }
        world.step(delta, 6, 2);
    }

    private void removeBullets(List<EntityDynamic> toRemoveList){
        for (EntityDynamic entity : toRemoveList){
            if (entity.entityState == ENTITY_STATE.STANDING){
                world.destroyBody(entity.getBody()); //Also remove from list?
            }
        }
    }

    private Planet getClosestPlanet(Vector2 bodyPos) {
        float closestDistance = 0.0f;
        Planet closestPlanet = null;
        for (EntityStatic entityStatic : staticEntities) { //Seems like we need to have a planetList since we want to apply gravity using every object in staticEntities
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
        }
        return closestPlanet;
    }

    public void addDynamicEntity(EntityDynamic entity) {
        addDynamicEntities.add(entity);
    }

    public void addStaticEntity(EntityStatic entity) {
        staticEntities.add(entity);
    }

    public Player getPlayer() {
        return player;
    }

}
