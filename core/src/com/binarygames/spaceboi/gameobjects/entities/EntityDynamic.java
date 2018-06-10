package com.binarygames.spaceboi.gameobjects.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.binarygames.spaceboi.gameobjects.bodies.BaseDynamicBody;

public abstract class EntityDynamic extends BaseDynamicBody {

    private Texture img;
    private Sprite sprite;

    private int speedX = 8;
    private int speedY = 8;

    private float x;
    private float y;

    protected boolean moveUp = false;
    protected boolean moveDown = false;
    protected boolean moveRight = false;
    protected boolean moveLeft = false;

    public EntityDynamic(World world, float x, float y, String path, float mass, float radius) {
        super(world, x, y, mass, radius);
        img = new Texture(path);
        this.sprite = new Sprite(img);
        this.x = x;
        this.y = y;
    }

    public void updateMovement() { //Detta används ej längre, bör tas bort!
        if (moveUp) {
            y += speedY;
            if (y > Gdx.graphics.getHeight() - this.sprite.getHeight()) {
                y = Gdx.graphics.getHeight() - this.sprite.getHeight();
            }
        }
        if (moveDown) {
            y -= speedY;
            if (y < 0) {
                y = 0;
            }
        }
        if (moveRight) {
            x += speedX; //Behövs det * deltatime?
            if (x > Gdx.graphics.getWidth() - this.sprite.getWidth()) {
                x = Gdx.graphics.getWidth() - this.sprite.getWidth();
                body.applyForceToCenter(200f, 0, true);
            }
        }
        if (moveLeft) {
            x -= speedX;
            if (x < 0) {
                x = 0;
                body.applyForceToCenter(-200f, 0, true);
            }
        }
    }

    public void setMoveUp(boolean moveUp) {
        this.moveUp = moveUp; //Dessa kallas av PlayerInputProcessor, i fallet för Player-Objektet
    }

    public void setMoveDown(boolean moveDown) {
        this.moveDown = moveDown;
    }

    public void setMoveRight(boolean moveRight) {
        this.moveRight = moveRight;
    }

    public void setMoveLeft(boolean moveLeft) {
        this.moveLeft = moveLeft;
    }


    //Getters and setters
    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public Body getBody() {
        return body;
    }


}
