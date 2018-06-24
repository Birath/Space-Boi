package com.binarygames.spaceboi.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.binarygames.spaceboi.SpaceBoi;

public class MainMenuScreen implements Screen {

    private SpaceBoi game;

    private OrthographicCamera camera;

    private Viewport viewport;

    private Stage stage;

    private BitmapFont titleFont;
    private LabelStyle titleStyle;

    private BitmapFont buttonFont;
    private TextButtonStyle buttonStyle;

    public MainMenuScreen(final SpaceBoi game) {
        this.game = game;

        camera = new OrthographicCamera();
        viewport = new FitViewport(SpaceBoi.VIRTUAL_WIDTH, SpaceBoi.VIRTUAL_HEIGHT, camera);
        viewport.apply();

        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        /*
            UI Elements
         */

        loadFonts();
        loadStyles();

        // Game title
        Label titleLabel = new Label("SpaceBoi", titleStyle);
        titleLabel.setPosition(SpaceBoi.VIRTUAL_WIDTH / 2 - titleLabel.getWidth() / 2,
                SpaceBoi.VIRTUAL_HEIGHT * 0.7f + titleLabel.getHeight());
        stage.addActor(titleLabel);

        // Play Button
        TextButton playButton = new TextButton("Play", buttonStyle);
        playButton.setPosition(SpaceBoi.VIRTUAL_WIDTH / 2 - playButton.getWidth() / 2, SpaceBoi.VIRTUAL_HEIGHT * 0.4f);
        playButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // TODO GOTO loadingscreen
                game.setScreen(new GameScreen(game));
                dispose();
            }
        });
        stage.addActor(playButton);
        /* TODO Add
        TextButton settingsButton = new TextButton("Settings", buttonStyle);
        settingsButton.setPosition(SpaceBoi.VIRTUAL_WIDTH / 2 - settingsButton.getWidth() / 2, SpaceBoi.VIRTUAL_HEIGHT * 0.2f);
        playButton.addListener(event -> {
            game.setScreen(new SettingsScreen(game));
            dispose();
            return false;
        });
        stage.addActor(settingsButton);
        */



        // Quit Button
        TextButton quitButton = new TextButton("Quit", buttonStyle);
        quitButton.setPosition(SpaceBoi.VIRTUAL_WIDTH / 2 - quitButton.getWidth() / 2,
                playButton.getY() - quitButton.getHeight() - 75);
        quitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });
        stage.addActor(quitButton);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);

        stage.draw();

        game.getBatch().begin();
        game.debugFont.draw(game.getBatch(), "MAIN_MENU", 5, 20);
        game.getBatch().end();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(SpaceBoi.VIRTUAL_WIDTH, SpaceBoi.VIRTUAL_HEIGHT, true);
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
        titleFont.dispose();
        buttonFont.dispose();

        stage.dispose();
    }

    private void loadFonts() {
        // Title font
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Roboto-Regular.ttf"));
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
        titleStyle = new LabelStyle();
        titleStyle.font = titleFont;

        // Button style
        buttonStyle = new TextButtonStyle();
        buttonStyle.font = buttonFont;
    }
}
