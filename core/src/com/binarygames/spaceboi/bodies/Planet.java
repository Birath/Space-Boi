package com.binarygames.spaceboi.bodies;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.World;


public class Planet extends BaseStaticBody {
    private Body body;

    //private ShapeRenderer renderer = new ShapeRenderer();
    private static final int DENSITY = 50000;


    public Planet(World aWorld, float x, float y, float radius) {
        super(aWorld, x, y, radius);

        System.out.println("Planet: " + x + ", " + y);
        System.out.println("Planet rad: " + rad);


        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.position.set(pos);

        this.body = world.createBody(groundBodyDef);

        CircleShape groundCircle = new CircleShape();
        groundCircle.setRadius(rad);

        body.createFixture(groundCircle, 0.0f);
        groundCircle.dispose();
        System.out.println(body);

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
