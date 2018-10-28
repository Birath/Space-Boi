package com.binarygames.spaceboi.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.binarygames.spaceboi.Assets;
import com.binarygames.spaceboi.SpaceBoi;

public class MainMenuScreen extends BaseScreen {

    public MainMenuScreen(final SpaceBoi game) {
        super(game, null);
    }

    @Override
    void loadScreen() {
        stage.clear();

        Skin uiSkin = getUiSkin();

        // Menu background
        Image backgroundImage = new Image(game.getAssetManager().get(Assets.MENU_BACKGROUND_IMAGE, Texture.class));
        backgroundImage.setOrigin(backgroundImage.getWidth() / 2, backgroundImage.getHeight() / 2);
        backgroundImage.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.addActor(backgroundImage);

        Table table = new Table();
        table.setFillParent(true);

        // Game title
        final Label title = new Label("SpaceBoi", getTitleStyle());


        // Play Button
        TextButton playButton = new TextButton("Play", uiSkin);
        playButton.setPosition(stage.getWidth() / 2 - playButton.getWidth() / 2, stage.getHeight() * 0.4f);
        playButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new LoadingScreen(game));
                dispose();
            }
        });

        TextButton settingsButton = new TextButton("Settings", uiSkin);
        settingsButton.setPosition(stage.getWidth() / 2 - settingsButton.getWidth() / 2, stage.getHeight() * 0.2f);
        settingsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new SettingsScreen(game, MainMenuScreen.this));
            }
        });

        // Quit Button
        TextButton quitButton = new TextButton("Quit", uiSkin);
        quitButton.setPosition(stage.getWidth() / 2 - quitButton.getWidth() / 2, stage.getHeight() * 0.1f);
        quitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

        // Credits Button
        TextButton creditsButton = new TextButton("Credits", uiSkin);
        creditsButton.setPosition(10, 10);
        creditsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new CreditsScreen(game, MainMenuScreen.this));
            }
        });
        table.add(title).align(Align.top).growY().height(Value.percentHeight(5.0f));
        table.row().pad(10, 0, 10, 0);
        table.add(playButton);
        table.row().pad(10, 0, 10, 0);
        table.add(settingsButton);
        table.row().pad(10, 0, 10, 0);
        table.add(quitButton);
        table.row().pad(10, 0, 10, 0);
        table.add(new Container<>(creditsButton));
        stage.addActor(table);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);

        stage.draw();
    }


}
