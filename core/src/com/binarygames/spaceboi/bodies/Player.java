package com.binarygames.spaceboi.bodies;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Player extends BaseStaticBody{
    private Body body;

    private static final int DENSITY = 300;

    public Player(World world, float x, float y, float radius) {
        super(world, x, y, radius);
        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.type = BodyDef.BodyType.DynamicBody;
        groundBodyDef.position.set(pos);
        body = world.createBody(groundBodyDef);

        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(rad);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circleShape;
        fixtureDef.density = 0.2f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.8f;
        Fixture fixture = body.createFixture(fixtureDef);
        circleShape.dispose();
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

    /*
    @Override
    public void draw (Batch batch, float parentAlpha) {
        //System.out.println("Ball pos x: " + body.getPosition().x + "Ball pos y " + body.getPosition().y);
        batch.end();
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setProjectionMatrix(batch.getProjectionMatrix());
        renderer.setTransformMatrix(batch.getTransformMatrix());


        renderer.setColor(Color.RED);
        Vector2 bodyPos = body.getPosition();

        renderer.circle(bodyPos.x * BOX_TO_WORLD, bodyPos.y * BOX_TO_WORLD, 40f);
        renderer.end();
        batch.begin();
    }
    */
}
