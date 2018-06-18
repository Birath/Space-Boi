package com.binarygames.spaceboi.gameobjects.entities.weapons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.binarygames.spaceboi.gameobjects.GameWorld;

public abstract class Weapon {
    protected World world;
    protected GameWorld gameWorld;
    private Sprite sprite;
    protected String path;

    protected float bulletMass;
    protected float bulletRadius;
    protected float bulletSpeed;
    protected float removeBulletDelay;
    protected float reloadDelay;
    protected int magSize;
    protected float recoil;

    public Weapon(World aWorld, GameWorld aGameWorld){
        this.world = aWorld;
        this.gameWorld = aGameWorld;
        //this.sprite = new Sprite(new Texture(path)); - Sprites shall be added later
        //sprite.setSize(radius * 2, radius * 2);

    }

    public void Shoot(float x, float y, Vector2 shootDirection){
        //depends on sub-weapon
    }
    public float getRecoil(){
        return this.recoil;
    }

}
