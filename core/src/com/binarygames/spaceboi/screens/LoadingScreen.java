package com.binarygames.spaceboi.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.binarygames.spaceboi.Assets;
import com.binarygames.spaceboi.SpaceBoi;

public class LoadingScreen extends BaseScreen {

    private static final float PADDING_PERCENT = 0.25f;
    private TextButton playButton = null;
    private Label progressLabel = null;

    protected LoadingScreen(SpaceBoi game) {
        super(game, null);

        // Add game assets to AssetManager queue
        game.getAssets().loadGameAssets();


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
        stage.clear();

        /*
            UI Elements
         */

        // Menu background
        Image backgroundImage = new Image(game.getAssetManager().get(Assets.MENU_BACKGROUND_IMAGE, Texture.class));
        backgroundImage.setOrigin(backgroundImage.getWidth() / 2, backgroundImage.getHeight() / 2);
        backgroundImage.setSize(stage.getWidth(), stage.getHeight());
        stage.addActor(backgroundImage);
        Table table = new Table();
        table.setFillParent(true);
        // Loading label
        progressLabel = new Label("Loading stuff: 0%", getTitleStyle());
        table.add(new Container<>(progressLabel)).expandY().center().padTop(Value.percentHeight(PADDING_PERCENT, table));

        // Play button
        playButton = new TextButton("Play!", getUiSkin());
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
        table.row();
        table.add(playButton).bottom().padBottom(Value.percentHeight(PADDING_PERCENT, table));
        stage.addActor(table);
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