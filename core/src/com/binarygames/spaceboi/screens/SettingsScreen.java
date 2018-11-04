package com.binarygames.spaceboi.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.binarygames.spaceboi.Assets;
import com.binarygames.spaceboi.SpaceBoi;
import com.binarygames.spaceboi.ui.AudioSettingsTab;
import com.binarygames.spaceboi.ui.ControlSettingsTab;
import com.binarygames.spaceboi.ui.SettingsTab;
import com.binarygames.spaceboi.ui.VideoSettingsTab;

public class SettingsScreen extends BaseScreen {

    private SettingsTab currentSettingsTab = null;
    private Cell settingsCell = null;

    private Screen previousScreen;

    public SettingsScreen(SpaceBoi game, Screen previousScreen) {
        super(game, previousScreen);
        this.previousScreen = previousScreen;


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


        AudioSettingsTab audioSettingsTab = new AudioSettingsTab(game, this);
        audioSettingsTab.hide();

        VideoSettingsTab videoSettingsTab = new VideoSettingsTab(game, this);
        videoSettingsTab.hide();

        ControlSettingsTab controlSettingsTab = new ControlSettingsTab(game, this);
        controlSettingsTab.hide();


        final TextButton audioButton = new TextButton("Audio", uiSkin);
        audioButton.addListener(new ChangeListener() {
            @Override
            public void changed(final ChangeEvent event, final Actor actor) {
                setCurrentTab(audioSettingsTab);
            }
        });

        final TextButton videoButton = new TextButton("Video", uiSkin);
        videoButton.addListener(new ChangeListener() {
            @Override
            public void changed(final ChangeEvent event, final Actor actor) {
                setCurrentTab(videoSettingsTab);
            }
        });

        final TextButton controlsButton = new TextButton("Controls", uiSkin);
        controlsButton.addListener(new ChangeListener() {
            @Override
            public void changed(final ChangeEvent event, final Actor actor) {
                setCurrentTab(controlSettingsTab);
            }
        });

        final TextButton backButton = new TextButton("Back", uiSkin);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(final ChangeEvent event, final Actor actor) {
                game.setScreen(previousScreen);
                dispose();
            }
        });

        final TextButton applyButton = new TextButton("Apply", uiSkin);
        applyButton.setVisible(false); // Is shown when settings which need to be applied are changed, ie resolution
        applyButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // TODO
            }
        });


        final Label title = new Label("Settings", getTitleStyle());

        Table tabButtons = new Table();
        tabButtons.add(audioButton).expand().left();
        tabButtons.add(videoButton).center().expand();
        tabButtons.add(controlsButton).right().expand();
        setCurrentTab(audioSettingsTab);

        table.add(title).align(Align.center).colspan(3).growY();
        table.row().pad(10, 0, 10,0);

        table.add(tabButtons).fill(0.5f, 1f).colspan(3).align(Align.center).expandX();
        table.row();
        settingsCell = table.add(currentSettingsTab.getTable()).colspan(3);

        Table bottomButtons = new Table();
        table.row().pad(10, 0, 10, 0);
        bottomButtons.add(backButton).left().expand().top();
        bottomButtons.add(applyButton).right().expand().top();
        table.add(bottomButtons).fill(0.5f, 1f).colspan(3).align(Align.center).expandX().growY();

        stage.addActor(table);
    }

    private void setCurrentTab(SettingsTab tab) {
        if (currentSettingsTab != null) {
            currentSettingsTab.hide();
            currentSettingsTab = tab;
            settingsCell.setActor(tab.getTable());
        } else {
            currentSettingsTab = tab;
        }
        currentSettingsTab.show();
    }

    @Override
    public void render(final float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            game.setScreen(getPreviousScreen());
            dispose();
        }
    }




}
