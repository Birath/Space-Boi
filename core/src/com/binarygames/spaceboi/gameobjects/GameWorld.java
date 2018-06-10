package com.binarygames.spaceboi.gameobjects;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.binarygames.spaceboi.SpaceBoi;
import com.binarygames.spaceboi.gameobjects.entities.EntityDynamic;
import com.binarygames.spaceboi.gameobjects.entities.EntityStatic;

import java.util.ArrayList;
import java.util.List;

public class GameWorld {

    private SpaceBoi game;
    private World world;

    private List<EntityDynamic> dynamicEntities;
    private List<EntityStatic> staticEntities;

    public GameWorld(SpaceBoi game, World world) {
        this.game = game;
        this.world = world;

        dynamicEntities = new ArrayList<>();
        staticEntities = new ArrayList<>();
    }

    public void update(float delta) {
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

    public void addDynamicEntity(EntityDynamic entity) {
        dynamicEntities.add(entity);
    }

    public void addStaticEntity(EntityStatic entity) {
        staticEntities.add(entity);
    }

}
