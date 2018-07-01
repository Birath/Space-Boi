package com.binarygames.spaceboi.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.binarygames.spaceboi.Assets;
import com.binarygames.spaceboi.SpaceBoi;

public class SettingsScreen extends BaseScreen implements Screen {


    public SettingsScreen(SpaceBoi game, Screen previousScreen) {
        super(game, previousScreen);

        stage.clear();

        Table table = new Table();
        table.setFillParent(true);

        stage.addActor(table);
        Skin uiSkin = game.getAssetManager().get(Assets.MENU_UI_SKIN, Skin.class);
        final Slider musicVolumeSlider = new Slider(0.0f, 1.0f, 0.1f, false, uiSkin);
        musicVolumeSlider.setValue(game.getPreferences().getMusicVolume());
        musicVolumeSlider.addListener(event -> {
            game.getPreferences().setMusicVolume(musicVolumeSlider.getValue());
            return false;
        });

        final CheckBox musicEnabledCheckBox = new CheckBox(null, uiSkin);
        musicEnabledCheckBox.setChecked(game.getPreferences().isMusicEnabled());
        musicEnabledCheckBox.addListener(event -> {
            game.getPreferences().setMusicEnabled(musicEnabledCheckBox.isChecked());
            return false;
        });

        final Slider soundVolumeSlider = new Slider(0.0f, 1.0f, 0.1f, false, uiSkin);
        soundVolumeSlider.setValue(game.getPreferences().getSoundVolume());
        soundVolumeSlider.addListener(event -> {
            game.getPreferences().setSoundVolume(soundVolumeSlider.getValue());
            return false;
        });

        final CheckBox soundEnabledCheckBox = new CheckBox(null, uiSkin);
        soundEnabledCheckBox.setChecked(game.getPreferences().isSoundEnabled());
        soundEnabledCheckBox.addListener(event -> {
            game.getPreferences().setSoundEnabled(soundEnabledCheckBox.isChecked());
            return false;
        });

        final TextButton quitButton = new TextButton("Quit", uiSkin);
        quitButton.addListener(new ChangeListener() {
            @Override
            public void changed(final ChangeEvent event, final Actor actor) {
                game.setScreen(previousScreen);
                dispose();
            }
        });

        final Label title = new Label("Settings", titleStyle);
        final Label musicVolumeLabel = new Label("Music volume", titleStyle);
        final Label soundVolumeLabel = new Label("Sound volume", titleStyle);
        final Label musicCheckBoxLabel = new Label("Music enabled", titleStyle);
        final Label soundCheckBoxLabel = new Label("Sound enabled", titleStyle);

        table.add(title);
        table.row().pad(10, 0, 10, 0);
        table.add(soundVolumeLabel).left();
        table.add(soundVolumeSlider);
        table.row().pad(10, 0, 10, 0);
        table.add(soundCheckBoxLabel).left();
        table.add(soundEnabledCheckBox);
        table.row().pad(10, 0, 10, 0);
        table.add(musicVolumeLabel).left();
        table.add(musicVolumeSlider);
        table.row().pad(10, 0, 10, 0);
        table.add(musicCheckBoxLabel).left();
        table.add(musicEnabledCheckBox);
        table.row().pad(10, 0, 10, 0);
        table.add(quitButton);
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

        game.getBatch().begin();
        game.debugFont.draw(game.getBatch(), "Settings", 5, 20);
        game.getBatch().end();

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
