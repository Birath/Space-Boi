package com.binarygames.spaceboi.gameobjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.binarygames.spaceboi.SpaceBoi;
import com.binarygames.spaceboi.gameobjects.entities.EntityDynamic;
import com.binarygames.spaceboi.gameobjects.entities.EntityStatic;

import java.util.ArrayList;
import java.util.List;

public class GameWorld {

    private SpaceBoi game;

    private List<EntityDynamic> dynamicEntities;
    private List<EntityStatic> staticEntities;

    public GameWorld(SpaceBoi game) {
        this.game = game;

        dynamicEntities = new ArrayList<>();
        staticEntities = new ArrayList<>();
    }

    public void update(float delta) {
        for (EntityDynamic entity : dynamicEntities) {
            //entity.update(delta); TODO add
        }
    }

    public void render(SpriteBatch batch) {
        for (EntityStatic entity : staticEntities) {
            //entity.render(batch); TODO add
        }
        for (EntityDynamic entity : dynamicEntities) {
            //entity.render(batch); TODO add
        }
    }

    public void addDynamicEntity(EntityDynamic entity) {
        dynamicEntities.add(entity);
    }

    public void addStaticEntity(EntityStatic entity) {
        staticEntities.add(entity);
    }

}
