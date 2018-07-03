package com.binarygames.spaceboi.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.binarygames.spaceboi.Assets;
import com.binarygames.spaceboi.SpaceBoi;
import com.binarygames.spaceboi.ui.AudioSettingsTab;

public class SettingsScreen extends BaseScreen implements Screen {

    public SettingsScreen(SpaceBoi game, Screen previousScreen) {
        super(game, previousScreen);

        stage.clear();

        // Menu background
        Image backgroundImage = new Image(game.getAssetManager().get(Assets.MENU_BACKGROUND_IMAGE, Texture.class));
        backgroundImage.setOrigin(backgroundImage.getWidth() / 2, backgroundImage.getHeight() / 2);
        backgroundImage.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.addActor(backgroundImage);

        AudioSettingsTab audioSettingsTab = new AudioSettingsTab(game);
        stage.addActor(audioSettingsTab.getTable());

        final TextButton backButton = new TextButton("Back", buttonStyle);
        backButton.setPosition(10, 10);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(final ChangeEvent event, final Actor actor) {
                game.setScreen(previousScreen);
                dispose();
            }
        });
        stage.addActor(backButton);

        final TextButton applyButton = new TextButton("Apply", buttonStyle);
        applyButton.setPosition(stage.getWidth() - applyButton.getWidth() - 10, 10);
        applyButton.setVisible(false); // Is shown when settings which need to be applied are changed, ie resolution
        applyButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // TODO
            }
        });
        stage.addActor(applyButton);

        final Label title = new Label("Settings", titleStyle);
        title.setPosition(10, stage.getHeight() - title.getHeight() - 10);
        stage.addActor(title);
    }

    @Override
    public void show() {
        Gdx.app.log("Settings", "Showing settings screen");
    }

    @Override
    public void render(final float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            game.setScreen(getPreviousScreen());
            dispose();
        }
    }

    @Override
    public void resize(final int width, final int height) {
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
        Gdx.app.log("Settings", "Disposing settings");
        stage.dispose();
        titleFont.dispose();
        buttonFont.dispose();
    }


}
