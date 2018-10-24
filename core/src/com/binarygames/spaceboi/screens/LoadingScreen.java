package com.binarygames.spaceboi.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.binarygames.spaceboi.Assets;
import com.binarygames.spaceboi.SpaceBoi;

public class LoadingScreen extends BaseScreen {

    private TextButton playButton;
    private Label progressLabel;

    protected LoadingScreen(SpaceBoi game) {
        super(game, null);

        // Add game assets to AssetManager queue
        game.getAssets().loadGameAssets();

        stage.clear();

        /*
            UI Elements
         */

        // Menu background
        Image backgroundImage = new Image(game.getAssetManager().get(Assets.MENU_BACKGROUND_IMAGE, Texture.class));
        backgroundImage.setOrigin(backgroundImage.getWidth() / 2, backgroundImage.getHeight() / 2);
        backgroundImage.setSize(stage.getWidth(), stage.getHeight());
        stage.addActor(backgroundImage);

        // Loading label
        progressLabel = new Label("Loading stuff: 0%", titleStyle);
        progressLabel.setPosition(stage.getWidth() / 2 - progressLabel.getWidth() / 2, stage.getHeight() / 2);
        stage.addActor(progressLabel);

        // Play button
        playButton = new TextButton("Play!", buttonStyle);
        playButton.setX(stage.getWidth() / 2 - playButton.getWidth() / 2);
        playButton.setY(stage.getHeight() / 8);
        playButton.setVisible(false);
        playButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (game.getPreferences().isTutorialEnabled()) {
                    game.setScreen(new IntroScreen(game, LoadingScreen.this));
                } else {
                    game.setScreen(new GameScreen(game));
                }
                dispose();
            }
        });
        stage.addActor(playButton);
    }

    private void update(float delta) {
        progressLabel.setText(((double) Math.round((game.getAssetManager().getProgress() * 100) * 1d) / 1d) + "%");

        if (game.getAssetManager().update()) {
            playButton.setVisible(true);
        }

        stage.act(delta);
    }

    @Override
    void loadScreen() {

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

        game.getBatch().begin();
        game.debugFont.draw(game.getBatch(), "LOADING_SCREEN", 5, 20);
        game.getBatch().end();
    }
}