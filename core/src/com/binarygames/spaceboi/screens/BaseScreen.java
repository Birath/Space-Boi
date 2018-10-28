package com.binarygames.spaceboi.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.binarygames.spaceboi.Assets;
import com.binarygames.spaceboi.SpaceBoi;

public abstract class BaseScreen implements Screen {

    public static final int WORLD_WIDTH = 1280;
    public static final int WORLD_HEIGHT = 720;
    protected SpaceBoi game;

    protected OrthographicCamera camera;

    protected Viewport viewport;

    protected Stage stage;

    private BitmapFont titleFont;
    private BitmapFont labelFont;

    private LabelStyle titleStyle;

    private Screen previousScreen;

    private Skin uiSkin;

    BaseScreen(SpaceBoi game, Screen previousScreen) {
        this.game = game;
        this.previousScreen = previousScreen;

        camera = new OrthographicCamera();
        viewport = new FitViewport(SpaceBoi.VIRTUAL_WIDTH, SpaceBoi.VIRTUAL_HEIGHT, camera);
        viewport.apply();

        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        uiSkin = game.getAssetManager().get(Assets.MENU_UI_SKIN, Skin.class);

        titleFont = game.getAssetManager().get(Assets.TITLE_FONT, BitmapFont.class);
        labelFont = game.getAssetManager().get(Assets.LABEL_FONT, BitmapFont.class);

        titleStyle = uiSkin.get("title-1", LabelStyle.class);
        titleStyle.font = titleFont;

        // Changes the font for the default label style
        uiSkin.get("default", LabelStyle.class).font = labelFont;
        uiSkin.get(TextButtonStyle.class).font = labelFont;

    }

    abstract void loadScreen();


    public Screen getPreviousScreen() {
        return previousScreen;
    }

    @Override
    public void show() {
        Gdx.app.log("Screen", "Showing screen");
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().setWorldSize(width, height);
        stage.getViewport().update(width, height, true);
        stage.getCamera().update();
        stage.getViewport().apply();
        loadScreen();

    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void pause() {
        Gdx.app.log("BaseScreen", "Pausing");
    }

    @Override
    public void resume() {
        Gdx.app.log("BaseScreen", "Resuming.");
    }

    @Override
    public void hide() {
        Gdx.app.log("BaseScreen", "Hiding screen");
    }

    public Skin getUiSkin() {
        return uiSkin;
    }

    public BitmapFont getLabelFont() {
        return labelFont;
    }

    public BitmapFont getTitleFont() {
        return titleFont;
    }

    public LabelStyle getTitleStyle() {
        return titleStyle;
    }
}
