package com.binarygames.spaceboi.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox.CheckBoxStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Slider.SliderStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.binarygames.spaceboi.GamePreferences;
import com.binarygames.spaceboi.SpaceBoi;

public class SettingsScreen extends BaseScreen implements Screen {


    public SettingsScreen(SpaceBoi game) {
        super(game);
    }

    @Override public void show() {
        stage.clear();

        Table table = new Table();
        table.setFillParent(true);

        stage.addActor(table);
        Skin uiSkin = new Skin(Gdx.files.internal("uiskin.json"));
        final Slider musicVolumeSlider = new Slider(0.0f, 1.0f, 0.1f, false, uiSkin);
        musicVolumeSlider.setValue(game.getPreferences().getMusicVolume());
        musicVolumeSlider.addListener(event -> {
            game.getPreferences().setMusicVolume(musicVolumeSlider.getValue());
            return false;
        });

        final CheckBox musicEnabledCheckBox = new CheckBox(null, uiSkin);
        musicEnabledCheckBox.setChecked(game.getPreferences().isMusicEnabed());
        musicEnabledCheckBox.addListener(event -> {
            game.getPreferences().setMusicEnabled(musicEnabledCheckBox.isChecked());
            return false;
        });

        final Label title = new Label("Settings", titleStyle);
        final Label musicVolumeLabel = new Label("Music volume", titleStyle);
        final Label musicCheckBoxLabel = new Label("Music enabled", titleStyle);

        table.add(title);
        table.row().pad(10, 0, 0, 10);
        table.add(musicVolumeLabel).left();
        table.add(musicVolumeSlider);
        table.row().pad(10, 0, 0, 10);
        table.add(musicCheckBoxLabel).left();
        table.add(musicEnabledCheckBox);
    }

    @Override public void render(final float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();

        game.getBatch().begin();
        game.debugFont.draw(game.getBatch(), "Settings", 5, 20);
        game.getBatch().end();

    }

    @Override public void resize(final int width, final int height) {
        stage.getViewport().update(width, height, true);

    }

    @Override public void pause() {

    }

    @Override public void resume() {

    }

    @Override public void hide() {

    }

    @Override public void dispose() {

    }


}
