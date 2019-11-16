package com.binarygames.spaceboi;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.binarygames.spaceboi.audio.MusicManager;
import com.binarygames.spaceboi.audio.SoundManager;
import com.binarygames.spaceboi.screens.Fonts;
import com.binarygames.spaceboi.screens.StartupScreen;

public class SpaceBoi extends Game {

    private SpriteBatch batch;

    private AssetManager assetManager;
    private Assets assets;

    private MusicManager musicManager;
    private SoundManager soundManager;

    public static final int VIRTUAL_WIDTH = 1280;
    public static final int VIRTUAL_HEIGHT = 720;

    public BitmapFont debugFont;

    public static Fonts font;

    private GamePreferences preferences = new GamePreferences(this);

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new Fonts();

        this.setScreen(new StartupScreen(this));

        // Assets
        assetManager = new AssetManager();
        assets = new Assets(assetManager);
        assets.loadMenuAssets();

        // Audio
        musicManager = new MusicManager(this);
        soundManager = new SoundManager(this);

        // Video
        // TODO fix
        Graphics.Monitor currMonitor = Gdx.graphics.getMonitor();
        DisplayMode displayMode = Gdx.graphics.getDisplayMode(currMonitor);
        if (preferences.isFullscreenEnabled() && !Gdx.graphics.setFullscreenMode(displayMode)) {
            // switching to full-screen mode failed
        }

        //Gdx.graphics.setVSync(preferences.isVsyncEnabled());

        debugFont = new BitmapFont();
        loadDebugFont();
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

    public Assets getAssets() {
        return assets;
    }

    public MusicManager getMusicManager() {
        return musicManager;
    }

    public SoundManager getSoundManager() {
        return soundManager;
    }

    public GamePreferences getPreferences() {
        return preferences;
    }

    private void loadDebugFont() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Roboto-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter.size = 20;
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 2;

        debugFont = generator.generateFont(parameter);
        debugFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        generator.dispose();
    }
}
