package com.binarygames.spaceboi.animation;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.binarygames.spaceboi.gameobjects.GameWorld;

public class AnimationHandler {
    private Texture textureAtlas;
    private Animation animation;

    private float animationTime = 0;
    private TextureRegion currentFrame;

    private float frameDuration;

    private GameWorld gameWorld;
    int frameColumns;
    int frameRows;

    String path;

    public AnimationHandler(GameWorld gameWorld, int frameColumns, int frameRows, float frameDuration, String path) {
        // A class that handles animation
        this.gameWorld = gameWorld;
        this.frameColumns = frameColumns;
        this.frameRows = frameRows;
        this.frameDuration = frameDuration;
        this.path = path;

        this.textureAtlas = gameWorld.getGame().getAssetManager().get(this.path, Texture.class);
        TextureRegion[][] textureTemp = TextureRegion.split(textureAtlas, textureAtlas.getWidth() / frameColumns, textureAtlas.getHeight() / frameRows);
        TextureRegion[] animationFrames = new TextureRegion[frameColumns * frameRows];
        int Index = 0;
        for (int i = 0; i < frameRows; i++) {
            for (int j = 0; j < frameColumns; j++) {
                animationFrames[Index++] = textureTemp[i][j];
            }
        }
        animation = new Animation(frameDuration, animationFrames);
        currentFrame = (TextureRegion) animation.getKeyFrame(animationTime, true);

    }

    public TextureRegion getCurrentFrame() {
        return currentFrame;
    }

    public void updateAnimation(float delta) {
        //Uppdates the animation if the passed in time delta makes the animationtime long enough
        animationTime += delta;
        currentFrame = (TextureRegion) animation.getKeyFrame(animationTime, true);
    }

    public void dispose() {

    }
}
