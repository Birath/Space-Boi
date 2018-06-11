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
import com.binarygames.spaceboi.gameobjects.entities.Player;

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
        Player player = new Player(world, 0, 0, "playerShip.png", 1000, 100, this);
        addDynamicEntity(player);
        this.player = player;

        Planet planet1 = new Planet(world, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight(), "moon.png",
                                    (float) Math.pow(4.6 * 10, 7), Gdx.graphics.getHeight());
        addStaticEntity(planet1);
        Planet planet2 = new Planet(world, Gdx.graphics.getWidth() * 2, Gdx.graphics.getHeight() / 2, "moon.png",
                                    (float) Math.pow(4.0 * 10, 7), Gdx.graphics.getHeight());
        addStaticEntity(planet2);

        world.setContactListener(new GameConctatListener(player));

    }

    public void update(float delta) {
        applyGravity(delta);
        for (EntityDynamic entity : dynamicEntities) {
            entity.updateMovement();
        }
        dynamicEntities.addAll(addDynamicEntities);
        addDynamicEntities.clear();
    }

    public void render(SpriteBatch batch, OrthographicCamera camera) {
        for (EntityStatic entity : staticEntities) {
            //entity.render(batch, camera);
        }
        for (EntityDynamic entity : dynamicEntities) {
            //entity.render(batch, camera);
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
