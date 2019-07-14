package com.binarygames.spaceboi.screens;

import com.badlogic.gdx.Gdx;
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


public class VictoryScreen extends BaseScreen {
    public VictoryScreen(SpaceBoi game, Screen previousScreen, int playerXP) {
        super(game, previousScreen);

        stage.clear();

        // Menu background
        Image backgroundImage = new Image(game.getAssetManager().get(Assets.WIN_BACKGROUND, Texture.class));
        backgroundImage.setOrigin(backgroundImage.getWidth() / 2, backgroundImage.getHeight() / 2);
        backgroundImage.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.addActor(backgroundImage);

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Label infoLabel = new Label("You won!", getTitleStyle());
        Label xpLabel = new Label("Final XP: " + Integer.toString(playerXP), getTitleStyle());

        TextButton restartButton = new TextButton("Restart", getUiSkin());
        restartButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new LoadingScreen(game));
                dispose();
            }
        });


        TextButton quitButton = new TextButton("Quit", getUiSkin());
        quitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

        table.add(infoLabel);
        table.row().pad(10, 0, 10, 0);
        table.add(xpLabel);
        table.row().pad(10, 0, 10, 0);
        table.add(restartButton);
        table.row().pad(10, 0, 10, 0);
        table.add(quitButton);

    }

    @Override
    void loadScreen() {

    }
}
