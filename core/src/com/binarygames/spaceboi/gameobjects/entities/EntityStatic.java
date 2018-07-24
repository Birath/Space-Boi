package com.binarygames.spaceboi.gameobjects.entities;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.binarygames.spaceboi.gameobjects.GameWorld;
import com.binarygames.spaceboi.gameobjects.bodies.BaseStaticBody;

public abstract class EntityStatic extends BaseStaticBody {

    private Sprite sprite;

    public EntityStatic(GameWorld gameWorld, float x, float y, String path, float mass, float radius) {
        super(gameWorld, x, y, mass, radius);
        body.setUserData(this);
        this.sprite = new Sprite(gameWorld.getGame().getAssetManager().get(path, Texture.class));
        sprite.setSize(radius * 2, radius * 2);
        sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
    }

    public EntityStatic(GameWorld gameWorld, float x, float y, String path, float mass, float width, float height) {
        super(gameWorld, x, y, mass, width, height);
        body.setUserData(this);
        this.sprite = new Sprite(gameWorld.getGame().getAssetManager().get(path, Texture.class));
        sprite.setSize(width, height);
        sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
    }


    @Override
    public void render(SpriteBatch batch, OrthographicCamera camera) {
        sprite.setPosition(body.getPosition().x * PPM - sprite.getWidth() / 2, body.getPosition().y * PPM - sprite.getHeight() / 2);
        sprite.draw(batch);
    }

    public Sprite getSprite() {
        return sprite;
    }


    /*public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw (Batch batch, float parentAlpha) {
        batch.end();
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setProjectionMatrix(batch.getProjectionMatrix());
        renderer.setTransformMatrix(batch.getTransformMatrix());
        System.out.println(renderer.getProjectionMatrix());
        renderer.setColor(Color.BLUE);
        Vector2 bodyPos = body.getPosition();
        //System.out.println(body.getWorldPoint(body.getLocalCenter())  + ", " + body.getLocalCenter().y * BOX_TO_WORLD);
        //System.out.println(body.getWorldCenter().x * BOX_TO_WORLD + ", " + body.getWorldCenter().y * BOX_TO_WORLD);
        renderer.circle(body.getWorldCenter().x * BOX_TO_WORLD, body.getWorldCenter().y * BOX_TO_WORLD, rad * BOX_TO_WORLD);
        renderer.end();
        batch.begin();


    }
    */
}