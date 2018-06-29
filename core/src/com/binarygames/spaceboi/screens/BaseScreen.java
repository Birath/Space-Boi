package com.binarygames.spaceboi.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.binarygames.spaceboi.SpaceBoi;

public abstract class BaseScreen {

    protected SpaceBoi game;

    protected OrthographicCamera camera;

    private Viewport viewport;

    Stage stage;

    BitmapFont titleFont;
    Label.LabelStyle titleStyle;

    BitmapFont buttonFont;
    TextButton.TextButtonStyle buttonStyle;

    private Screen previousScreen;


    BaseScreen(SpaceBoi game, Screen previousScreen) {
        this.game = game;
        this.previousScreen = previousScreen;

        camera = new OrthographicCamera();
        viewport = new FitViewport(SpaceBoi.VIRTUAL_WIDTH, SpaceBoi.VIRTUAL_HEIGHT, camera);
        viewport.apply();

        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        loadFonts();
        loadStyles();
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

    public Screen getPreviousScreen() {
        return previousScreen;
    }
}
