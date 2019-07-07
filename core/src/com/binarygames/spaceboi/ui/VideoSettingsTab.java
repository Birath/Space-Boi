package com.binarygames.spaceboi.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.binarygames.spaceboi.Assets;
import com.binarygames.spaceboi.SpaceBoi;
import com.binarygames.spaceboi.screens.SettingsScreen;

public class VideoSettingsTab extends SettingsTab {

    private SpaceBoi game;
    private SettingsScreen settingsScreen;

    public VideoSettingsTab(SpaceBoi game, SettingsScreen settingsScreen) {
        this.game = game;
        this.settingsScreen = settingsScreen;

        table = new Table();

        Skin uiSkin = game.getAssetManager().get(Assets.MENU_UI_SKIN, Skin.class);

        final CheckBox fullscreenCheckBox = new CheckBox(null, uiSkin);
        fullscreenCheckBox.pad(32);
        fullscreenCheckBox.setChecked(game.getPreferences().isFullscreenEnabled());
        fullscreenCheckBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.getPreferences().setFullscreenEnable(fullscreenCheckBox.isChecked());
                // TODO fix
                Graphics.Monitor currMonitor = Gdx.graphics.getMonitor();
                Graphics.DisplayMode displayMode = Gdx.graphics.getDisplayMode(currMonitor);
                if (fullscreenCheckBox.isChecked()) {
                    if (!Gdx.graphics.setFullscreenMode(displayMode)) {
                        // switching to full-screen mode failed
                    }
                } else {
                    Gdx.graphics.setWindowedMode(1280, 720);
                }
            }
        });

        final Label fullscreenLabel = new Label("Fullscreen", uiSkin);

        table.add(fullscreenLabel).left();
        table.add(fullscreenCheckBox).center();
    }

}
