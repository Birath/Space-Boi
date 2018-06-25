package com.binarygames.spaceboi;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.binarygames.spaceboi.screens.Fonts;
import com.binarygames.spaceboi.screens.MainMenuScreen;

public class SpaceBoi extends Game {

    private SpriteBatch batch;

    private AssetManager assetManager;

    public static final int VIRTUAL_WIDTH = 1280;
    public static final int VIRTUAL_HEIGHT = 720;

    public BitmapFont debugFont;

    public static Fonts font;

    private GamePreferences preferences = new GamePreferences();

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new Fonts();

        assetManager = new AssetManager();

        debugFont = new BitmapFont();
        loadDebugFont();

        this.setScreen(new MainMenuScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public GamePreferences getPreferences() {
        return preferences;
    }

    private void loadDebugFont() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Roboto-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter.size = 20;
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 2;

        debugFont = generator.generateFont(parameter);
        debugFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        generator.dispose();
    }
}
