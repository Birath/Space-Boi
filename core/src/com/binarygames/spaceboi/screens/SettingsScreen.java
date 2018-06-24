package com.binarygames.spaceboi.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox.CheckBoxStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Slider.SliderStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.binarygames.spaceboi.GamePreferences;
import com.binarygames.spaceboi.SpaceBoi;

public class SettingsScreen extends BaseScreen implements Screen {


    public SettingsScreen(SpaceBoi game) {
        super(game);

       /*
           UI Elements
        */


    }

    @Override public void show() {

        Table table = new Table();
        table.setFillParent(true);

        stage.addActor(table);

        final Slider musicVolumeSlider = new Slider(0.0f, 1.0f, 0.1f, false, new SliderStyle());
        musicVolumeSlider.setValue(game.getPreferences().getMusicVolume());
        musicVolumeSlider.addListener(event -> {
            game.getPreferences().setMusicVolume(musicVolumeSlider.getValue());
            return false;
        });

        final CheckBox musicEnabledCheckBox = new CheckBox(null, new CheckBoxStyle());
        musicEnabledCheckBox.setChecked(game.getPreferences().isMusicEnabed());
        musicEnabledCheckBox.addListener(event -> {
            game.getPreferences().setMusicEnabled(musicEnabledCheckBox.isChecked());
            return false;
        });

        table.add(musicVolumeSlider);
        table.row().pad(10, 0, 0, 10);
        table.add(musicEnabledCheckBox);
    }

    @Override public void render(final float delta) {

        stage.act(delta);
        stage.draw();

    }

    @Override public void resize(final int width, final int height) {

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
