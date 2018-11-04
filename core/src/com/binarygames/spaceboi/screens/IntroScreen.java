package com.binarygames.spaceboi.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.binarygames.spaceboi.Assets;
import com.binarygames.spaceboi.SpaceBoi;


public class IntroScreen extends BaseScreen {
    public IntroScreen(SpaceBoi game, Screen previousScreen) {
        super(game, previousScreen);
    }

    @Override
    void loadScreen() {
        stage.clear();

        // Menu background
        Image backgroundImage = new Image(game.getAssetManager().get(Assets.MENU_BACKGROUND_IMAGE, Texture.class));
        backgroundImage.setOrigin(backgroundImage.getWidth() / 2, backgroundImage.getHeight() / 2);
        //backgroundImage.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.addActor(backgroundImage);

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Label infoLabel = new Label("A - Move left\n" +
            "D - Move right\n" +
            "Space - Jump\n" +
            "Left Mouse - Shoot\n" +
            "1 - Equip Shotgun\n" +
            "2 - Equip Machinegun\n" +
            "3 - Equip Grenade launcher\n" +
            "R - Reload your current weapon\n" +
            "I - Open weapon attachment menu\n" +
            "\n" +
            "Space pirates have just stolen your beloved dog and brought him\n" +
            "to the middle of the galaxy. Make your way there by slaying enemies\n" +
            "and jumping off the launchpads that are located on each planet.\n" +
            "Hurry up before they eat him!", getUiSkin());

        TextButton playButton = new TextButton("Start", getUiSkin());
        playButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new GameScreen(game));
                dispose();
            }
        });

        table.add(infoLabel);
        table.row().pad(10, 0, 0, 10);
        table.add(playButton);
    }
}
