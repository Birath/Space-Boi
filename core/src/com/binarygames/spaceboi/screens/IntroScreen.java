package com.binarygames.spaceboi.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.binarygames.spaceboi.Assets;
import com.binarygames.spaceboi.SpaceBoi;

import java.awt.*;


public class IntroScreen extends BaseScreen {
    public IntroScreen(SpaceBoi game, Screen previousScreen) {
        super(game, previousScreen);

        stage.clear();

        // Menu background
        Image backgroundImage = new Image(game.getAssetManager().get(Assets.MENU_BACKGROUND_IMAGE, Texture.class));
        backgroundImage.setOrigin(backgroundImage.getWidth() / 2, backgroundImage.getHeight() / 2);
        backgroundImage.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.addActor(backgroundImage);

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Roboto-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter.size = 26;
        parameter.color = Color.WHITE;
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 2;

        BitmapFont infoFont = generator.generateFont(parameter);
        infoFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        Label.LabelStyle infoStyle = new Label.LabelStyle();
        infoStyle.font = infoFont;

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
            "Space pirates have just stolen beloved your dog and brought him\n" +
            "to the middle of the galaxy. Make your way there by slaying enemies\n" +
            "and jumping off the launchpads that are located on each planet.\n" +
            "Hurry up before they eat him!", infoStyle);

        TextButton playButton = new TextButton("Start", buttonStyle);
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
