package com.binarygames.spaceboi.ui;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.binarygames.spaceboi.Assets;
import com.binarygames.spaceboi.SpaceBoi;
import com.binarygames.spaceboi.screens.SettingsScreen;

public class AudioSettingsTab extends SettingsTab {

    private SpaceBoi game;
    private SettingsScreen settingsScreen;

    public AudioSettingsTab(SpaceBoi game, SettingsScreen settingsScreen) {
        this.game = game;
        this.settingsScreen = settingsScreen;

        table = new Table();

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

        final Label musicVolumeLabel = new Label("Music volume", uiSkin);
        final Label soundVolumeLabel = new Label("Sound volume", uiSkin);
        final Label musicCheckBoxLabel = new Label("Music enabled", uiSkin);
        final Label soundCheckBoxLabel = new Label("Sound enabled", uiSkin);

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
    }

}
