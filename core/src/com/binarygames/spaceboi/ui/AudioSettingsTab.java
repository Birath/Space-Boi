package com.binarygames.spaceboi.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.binarygames.spaceboi.Assets;
import com.binarygames.spaceboi.SpaceBoi;

public class AudioSettingsTab {

    private SpaceBoi game;

    private Table table;

    BitmapFont titleFont;
    Label.LabelStyle titleStyle;

    BitmapFont buttonFont;
    TextButton.TextButtonStyle buttonStyle;

    public AudioSettingsTab(SpaceBoi game) {
        this.game = game;

        loadFonts();
        loadStyles();

        table = new Table();
        table.setFillParent(true);

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

        final Label musicVolumeLabel = new Label("Music volume", titleStyle);
        final Label soundVolumeLabel = new Label("Sound volume", titleStyle);
        final Label musicCheckBoxLabel = new Label("Music enabled", titleStyle);
        final Label soundCheckBoxLabel = new Label("Sound enabled", titleStyle);

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

    public Table getTable() {
        return table;
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

}
