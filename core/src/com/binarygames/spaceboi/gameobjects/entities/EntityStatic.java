package com.binarygames.spaceboi.gameobjects.entities;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.World;
import com.binarygames.spaceboi.gameobjects.bodies.BaseStaticBody;

public abstract class EntityStatic extends BaseStaticBody {

    private Body body;

    private Texture texture;

    private float x;
    private float y;

    //private ShapeRenderer renderer = new ShapeRenderer();
    private static final int DENSITY = 50000;

    public EntityStatic(World aWorld, float x, float y, String path, float mass, float radius) {
        super(aWorld, x, y, mass, radius);
        texture = new Texture(path);
        this.x = x;
        this.y = y;

        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.position.set(pos);

        this.body = world.createBody(groundBodyDef);

        CircleShape groundCircle = new CircleShape();
        groundCircle.setRadius(rad);

        body.createFixture(groundCircle, 0.0f);
        groundCircle.dispose();

    }

    @Override
    public void render(SpriteBatch batch, OrthographicCamera camera) {
        Vector3 screenCoords = camera.project(new Vector3(body.getPosition(), 0));
        batch.draw(texture, screenCoords.x - texture.getWidth() / 2, screenCoords.y - texture.getHeight() / 2);
    }

    public Body getBody() {
        return body;
    }

    public float getMass() {
        return mass;
    }

    @Override
    protected int getDensity() {
        return DENSITY;
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