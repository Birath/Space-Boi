package com.binarygames.spaceboi.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.binarygames.spaceboi.SpaceBoi;
import com.binarygames.spaceboi.ui.BlurUtils;



public class InGameMenuScreen {

    private SpaceBoi game;

    private Stage stage;

    private TextButton.TextButtonStyle textButtonStyle;

    private GameScreen gameScreen;

    private Pixmap pixmap;

    private Texture frameTex;

    public InGameMenuScreen(GameScreen gameScreen, SpaceBoi game) {
        this.game = game;
        this.gameScreen = gameScreen;

        stage = new Stage();
        Fonts fonts = new Fonts();
        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = fonts.getButtonFont();
    }

    public void createBlurredBackground(){
        byte[] pixels = ScreenUtils.getFrameBufferPixels(0, 0, Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight(), true);

        // this loop makes sure the whole screenshot is opaque and looks exactly like what the user is seeing
        for (int i = 4; i < pixels.length; i += 4) {
            pixels[i - 1] = (byte) 255;
        }

        pixmap = new Pixmap(Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight(), Pixmap.Format.RGBA8888);
        BufferUtils.copy(pixels, 0, pixmap.getPixels(), pixels.length);
        frameTex = new Texture(BlurUtils.blur(pixmap, 3, 3, true));

        Image image = new Image(frameTex);
        image.setPosition(0,0);
        stage.addActor(image);

        createButtons();


    }

    private void createButtons(){
        TextButton resumeButton = new TextButton("Resume", textButtonStyle);
        resumeButton.setPosition((stage.getWidth()/2)-(resumeButton.getWidth()/2), stage.getHeight()*7/10-resumeButton.getHeight()/2);
        resumeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameScreen.resume();
            }
        });

        stage.addActor(resumeButton);

        TextButton optionsButton = new TextButton("Options", textButtonStyle);
        optionsButton.setPosition((stage.getWidth()/2)-(optionsButton.getWidth()/2), stage.getHeight()*5/10-optionsButton.getHeight()/2);
        optionsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new SettingsScreen(game, gameScreen));
            }
        });
        stage.addActor(optionsButton);

        TextButton quitButton = new TextButton("Quit", textButtonStyle);
        quitButton.setPosition((stage.getWidth()/2)-(quitButton.getWidth()/2), stage.getHeight()*3/10-quitButton.getHeight()/2);
        quitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MainMenuScreen(game));
                //gameScreen.dispose();
                dispose();
            }
        });
        stage.addActor(quitButton);
    }

    public void act(float delta){
        stage.act(delta);
    }

    public void draw(){
        stage.draw();
    }

    public Stage getStage() {
        return stage;
    }

    public void dispose(){
        stage.dispose();
    }
}
