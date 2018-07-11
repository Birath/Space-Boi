package com.binarygames.spaceboi.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.binarygames.spaceboi.SpaceBoi;

public class New_StartupScreen implements Screen {

    protected SpaceBoi game;

    protected OrthographicCamera camera;

    private Viewport viewport;

    Stage stage;

    BitmapFont titleFont;
    Label.LabelStyle titleStyle;

    BitmapFont buttonFont;
    TextButton.TextButtonStyle buttonStyle;

    private Image startupImage;

    public New_StartupScreen(SpaceBoi game) {
        this.game = game;

        camera = new OrthographicCamera();
        viewport = new FitViewport(SpaceBoi.VIRTUAL_WIDTH, SpaceBoi.VIRTUAL_HEIGHT, camera);
        viewport.apply();

        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        loadFonts();
        loadStyles();

        // Game startup image
        startupImage = new Image(new Texture(Gdx.files.internal("menu/startup_screen/startup.jpg")));
        startupImage.setOrigin(startupImage.getWidth() / 2, startupImage.getHeight() / 2);
        startupImage.setSize(stage.getWidth(), stage.getHeight());
        stage.addActor(startupImage);

        // Loading label // TODO add text similar to loading screen?
        Label startupLabel = new Label("Launching SpaceBoi,\nplease fasten your seatbelts...", titleStyle);
        startupLabel.setX(stage.getWidth() / 2 - startupLabel.getWidth() / 2);
        startupLabel.setY(stage.getHeight() / 2 + startupLabel.getHeight() / 2);
        stage.addActor(startupLabel);
    }


    private void update(float delta) {
        // Load menu and sound assets
        if (game.getAssetManager().update()) {
            game.setScreen(new MainMenuScreen(game));
            dispose();
        }

        stage.act(delta);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT |
                (Gdx.graphics.getBufferFormat().coverageSampling ? GL20.GL_COVERAGE_BUFFER_BIT_NV : 0));

        update(delta);

        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        // TODO dispose of startup image
    }

    private void loadFonts() {
        // Title font
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Roboto-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter.size = 72;
        parameter.color = Color.WHITE;
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 3;

        titleFont = generator.generateFont(parameter);
        titleFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        // Button font
        parameter.size = 46;
        parameter.color = Color.WHITE;
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 3;

        buttonFont = generator.generateFont(parameter);
        buttonFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        // Dispose of FontGenerator
        generator.dispose();
    }

    private void loadStyles() {
        // Title style
        titleStyle = new Label.LabelStyle();
        titleStyle.font = titleFont;

        // Button style
        buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = buttonFont;
    }
}