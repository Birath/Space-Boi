package com.binarygames.spaceboi.ui;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.binarygames.spaceboi.Assets;
import com.binarygames.spaceboi.SpaceBoi;
import com.binarygames.spaceboi.screens.SettingsScreen;

public class ControlSettingsTab extends SettingsTab {

    private SpaceBoi game;
    private SettingsScreen settingsScreen;

    public ControlSettingsTab(SpaceBoi game, SettingsScreen settingsScreen) {
        this.game = game;
        this.settingsScreen = settingsScreen;

        table = new Table();

        Skin uiSkin = game.getAssetManager().get(Assets.MENU_UI_SKIN, Skin.class);

        TextButton shootKey = new TextButton("shoot", uiSkin);
        shootKey.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // TODO keyboard pupup thing
            }
        });

        String keyName = getKeyName(game.getPreferences().getKeyShoot(), game.getPreferences().isKeyShootKeyboard());
        final Label shootKeyLabel = new Label(keyName, uiSkin);

        table.add(shootKey).left();
        table.add(shootKeyLabel).right();
    }

    private String getKeyName(int keyCode, boolean isKeyboardKey) {
        if (isKeyboardKey) {
            return Input.Keys.toString(keyCode);
        }
        switch (keyCode) {
            case 0:
                return "Left mouse";
            case 1:
                return "Right mouse";
            case 2:
                return "Middle mouse";
            case 3:
                return "Back mouse";
            case 4:
                return "Forward mouse";
            default:
                return "null";
        }
    }

}
