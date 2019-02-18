package com.binarygames.spaceboi.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.binarygames.spaceboi.Assets;
import com.binarygames.spaceboi.SpaceBoi;
import com.binarygames.spaceboi.gameobjects.GameWorld;
import com.binarygames.spaceboi.ui.BlurUtils;


public class InGameMenuScreen{

    private SpaceBoi game;

    private Stage stage;

    private GameScreen gameScreen;

    private Texture frameTex;

    private Table imageTable;

    private Image image;

    private TextButton resumeButton;
    private TextButton optionsButton;
    private TextButton quitButton;

    private Skin uiSkin;

    public InGameMenuScreen(GameScreen gameScreen, SpaceBoi game) {
        this.game = game;
        this.gameScreen = gameScreen;

        FillViewport viewport = new FillViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        stage = new Stage(viewport);

        uiSkin = game.getAssetManager().get(Assets.MENU_UI_SKIN, Skin.class);
        BitmapFont labelFont = game.getAssetManager().get(Assets.LABEL_FONT, BitmapFont.class);
        uiSkin.get("default", Label.LabelStyle.class).font = labelFont;
        //stage.setDebugAll(true);
    }

    public void createBlurredBackground() {
        byte[] pixels = ScreenUtils.getFrameBufferPixels(0, 0, Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight(), true);
        // this loop makes sure the whole screenshot is opaque and looks exactly like what the user is seeing
        for (int i = 4; i < pixels.length; i += 4) {
            pixels[i - 1] = (byte) 255;
        }

        Pixmap pixmap = new Pixmap(Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight(), Pixmap.Format.RGBA8888);
        BufferUtils.copy(pixels, 0, pixmap.getPixels(), pixels.length);
        frameTex = new Texture(BlurUtils.blur(pixmap, 3, 3, true));

        image = new Image(frameTex);
        image.setPosition(0, 0);
        image.setScaling(Scaling.fill);
        image.setFillParent(true);

        imageTable = new Table();
        imageTable.setFillParent(true);
        imageTable.left();
        imageTable.bottom();

        imageTable.add(image).center().fill(true);
        stage.addActor(imageTable);

        createButtons();
    }

    private void createButtons() {
        resumeButton = new TextButton("Resume", uiSkin);
        resumeButton.setPosition((stage.getWidth() / 2) - (resumeButton.getWidth() / 2), stage.getHeight() * 7 / 10 - resumeButton.getHeight() / 2);
        resumeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameScreen.resume();
            }
        });
        stage.addActor(resumeButton);

        optionsButton = new TextButton("Options", uiSkin);
        optionsButton.setPosition((stage.getWidth() / 2) - (optionsButton.getWidth() / 2), stage.getHeight() * 5 / 10 - optionsButton.getHeight() / 2);
        optionsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new SettingsScreen(game, gameScreen));
            }
        });
        stage.addActor(optionsButton);

        quitButton = new TextButton("Quit", uiSkin);
        quitButton.setPosition((stage.getWidth() / 2) - (quitButton.getWidth() / 2), stage.getHeight() * 3 / 10 - quitButton.getHeight() / 2);
        quitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MainMenuScreen(game));
                gameScreen.dispose();
                //dispose();
            }
        });
        stage.addActor(quitButton);
    }

    public void act(float delta) {
        stage.act(delta);
    }

    public void draw() {
        stage.draw();
    }

    public Stage getStage() {
        return stage;
    }

    public void dispose() {
        stage.dispose();
        if (frameTex != null) { frameTex.dispose(); }
    }

    public void reset(){
        stage.clear();
        frameTex.dispose();
    }

    public void resize(int width, int height) {
        imageTable.setSize(width, height);
        image.setSize(width, height);
        resumeButton.addAction(Actions.removeActor());
        optionsButton.addAction(Actions.removeActor());
        quitButton.addAction(Actions.removeActor());
        createButtons();
    }
}