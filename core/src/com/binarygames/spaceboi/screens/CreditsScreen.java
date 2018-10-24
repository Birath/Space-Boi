package com.binarygames.spaceboi.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.binarygames.spaceboi.Assets;
import com.binarygames.spaceboi.SpaceBoi;

public class CreditsScreen extends BaseScreen implements Screen {

    private Label creditsLabel;

    private int crawlSpeed = 150;

    protected CreditsScreen(SpaceBoi game, Screen previousScreen) {
        super(game, previousScreen);

        stage.clear();

        // Menu background
        Image backgroundImage = new Image(game.getAssetManager().get(Assets.MENU_BACKGROUND_IMAGE, Texture.class));
        backgroundImage.setOrigin(backgroundImage.getWidth() / 2, backgroundImage.getHeight() / 2);
        backgroundImage.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.addActor(backgroundImage);

        FileHandle textHandler = Gdx.files.internal("menu/credits_screen/credits.txt");
        String creditText = textHandler.readString();

        // Credits label
        creditsLabel = new Label(creditText, titleStyle);
        creditsLabel.setPosition(stage.getWidth() / 2 - creditsLabel.getWidth() / 2, -creditsLabel.getHeight());
        creditsLabel.setAlignment(Align.center);
        stage.addActor(creditsLabel);
    }

    @Override
    void loadScreen() {

    }

    private void update(float delta) {
        // Continue credits crawl
        creditsLabel.setY(creditsLabel.getY() + crawlSpeed * delta);

        // Return if credits scroll is finished
        if (creditsLabel.getY() > stage.getHeight()) {
            returnToPreviousScreen();
        }

        // Return to menu if escape key or mouse is pressed
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE) || Gdx.input.isTouched()) {
            returnToPreviousScreen();
        }

        stage.act(delta);
    }

    private void returnToPreviousScreen() {
        game.setScreen(getPreviousScreen());
        dispose();
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
}
