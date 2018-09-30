package com.binarygames.spaceboi.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.binarygames.spaceboi.Assets;
import com.binarygames.spaceboi.SpaceBoi;
import com.binarygames.spaceboi.background_functions.XPHandler;
import com.binarygames.spaceboi.gameobjects.entities.Player;
import com.binarygames.spaceboi.screens.Fonts;

public class GameUI {

    private Stage stage;

    private LabelStyle labelStyle;

    private TextButtonStyle buttonStyle;

    private Texture texture;
    private Image image;

    private Label label;
    private Integer health;

    private ProgressBar oxygenBar;
    private ProgressBar.ProgressBarStyle oxygenBarStyle;
    private Integer oxygen;

    private ProgressBar healthBar;
    private ProgressBar.ProgressBarStyle healthBarStyle;

    private ProgressBar xpBar;
    private ProgressBar.ProgressBarStyle xpBarStyle;
    private Label currentLevel;
    private Label nextLevel;

    private XPHandler xpHandler;

    private WeaponStats weaponStats1;
    private WeaponStats weaponStats2;
    private WeaponStats weaponStats3;

    private Player player;

    private Fonts fonts;


    public GameUI(SpaceBoi game, Player player, XPHandler xpHandler) {
        this.player = player;
        this.xpHandler = xpHandler;
        stage = new Stage();
        fonts = new Fonts();

        //loadFonts();
        loadStyles();
        health = 5;

        // Health icon
        texture = game.getAssetManager().get(Assets.PICKUP_HEALTH, Texture.class);
        image = new Image(texture);
        image.setSize(50, 50);
        image.setPosition(Gdx.graphics.getWidth() * 16 / 20 - image.getImageWidth(), Gdx.graphics.getHeight() / 20 - image.getImageHeight());
        image.setOrigin(image.getX() - image.getWidth() / 2, image.getY() - image.getHeight() / 2);
        stage.addActor(image);

        // Health indicator
        label = new Label(Float.toString(image.getOriginY()), labelStyle);
        label.setPosition(Gdx.graphics.getWidth() * 18 / 20 - label.getWidth(), Gdx.graphics.getHeight() / 10 - label.getHeight());
        stage.addActor(label);

        // Jet pack fuel bar
        oxygen = 75; //
        oxygenBar = new ProgressBar(0, player.MAX_OXYGEN_LEVEL, 1, true, oxygenBarStyle);
        oxygenBar.setValue(player.MAX_OXYGEN_LEVEL);
        oxygenBar.setBounds(Gdx.graphics.getWidth() * 39 / 40, Gdx.graphics.getHeight() / 10, 20, 100);
        stage.addActor(oxygenBar);

        // Health bar
        healthBar = new ProgressBar(0, player.getMaxHealth(), 1, false, healthBarStyle);
        healthBar.setValue(health);
        healthBar.setBounds(Gdx.graphics.getWidth() * 17 / 20, image.getOriginY() - healthBar.getHeight() / 2 + (Gdx.graphics.getHeight() / 40), 100, 20);
        stage.addActor(healthBar);

        // XP bar
        currentLevel = new Label(String.valueOf(xpHandler.getLevel()), labelStyle);
        nextLevel = new Label(String.valueOf(xpHandler.getCurrentXP()) + " / " + String.valueOf(xpHandler.getNextLevel()), labelStyle);
        currentLevel.setBounds(0, 0, currentLevel.getWidth(), currentLevel.getHeight());
        nextLevel.setFontScale(0.6f);
        nextLevel.setVisible(false);

        xpBar = new ProgressBar(0, 100, 0.1f, false, xpBarStyle);
        xpBar.setValue(0);
        xpBar.setBounds(0 + currentLevel.getWidth(), 0, Gdx.graphics.getWidth() - currentLevel.getWidth(), Gdx.graphics.getHeight() / 50);
        nextLevel.setBounds(Gdx.graphics.getWidth() / 2 - nextLevel.getWidth(), 0, nextLevel.getWidth(), xpBar.getHeight());

        xpBar.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                super.enter(event, x, y, pointer, fromActor);
                nextLevel.setVisible(true);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                super.exit(event, x, y, pointer, toActor);
                nextLevel.setVisible(false);
            }
        });

        nextLevel.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                super.enter(event, x, y, pointer, fromActor);
                nextLevel.setVisible(true);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                super.exit(event, x, y, pointer, toActor);
                nextLevel.setVisible(false);
            }
        });

        stage.addActor(xpBar);
        stage.addActor(currentLevel);
        stage.addActor(nextLevel);

        weaponStats1 = new WeaponStats(stage, stage.getWidth() / 20, stage.getHeight() * 9 / 10, player.getWeaponList().get(0).getMagSize());
        weaponStats2 = new WeaponStats(stage, stage.getWidth() * 3 / 20, stage.getHeight() * 9 / 10, player.getWeaponList().get(1).getMagSize());
        weaponStats3 = new WeaponStats(stage, stage.getWidth() * 5 / 20, stage.getHeight() * 9 / 10, player.getWeaponList().get(2).getMagSize());
    }

    public void act(float delta) {
        healthBar.setRange(0, player.getMaxHealth());
        updateHealth(player.getHealth());
        updateOxygenLevel();
        updateWeaponStats(weaponStats1, 0);
        updateWeaponStats(weaponStats2, 1);
        updateWeaponStats(weaponStats3, 2);
        currentLevel.setText(String.valueOf(xpHandler.getLevel()));
        nextLevel.setText(String.valueOf(xpHandler.getCurrentXP()) + " / " + String.valueOf(xpHandler.getNextLevel()));

        if ((xpBar.getValue() * (Gdx.graphics.getWidth() / xpBar.getMaxValue())) < nextLevel.getWidth()) {
            nextLevel.setPosition(Gdx.graphics.getWidth()/2 - nextLevel.getWidth(), 0);
        }
        else {
            nextLevel.setPosition(((Gdx.graphics.getWidth() / xpBar.getMaxValue()) * xpBar.getValue()) / 2, 0);
        }

        xpBar.setRange(0, xpHandler.getNextLevel());
        xpBar.setValue(xpHandler.getCurrentXP());

        stage.act(delta);
    }

    public void draw() {
        stage.draw();
    }

    public Stage getStage() {
        return stage;
    }

    private void loadStyles() {
        // Button style
        buttonStyle = new TextButtonStyle();
        buttonStyle.font = fonts.getButtonFont();

        // Label style
        labelStyle = new LabelStyle();
        labelStyle.font = fonts.getLabelFont();

        // Progress bar styles
        Pixmap pixmap = new Pixmap(20, 100, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.GRAY);
        pixmap.fill();
        TextureRegionDrawable drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();
        oxygenBarStyle = new ProgressBar.ProgressBarStyle();
        oxygenBarStyle.background = drawable;

        pixmap = new Pixmap(100, 20, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.GRAY);
        pixmap.fill();
        drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();
        healthBarStyle = new ProgressBar.ProgressBarStyle();
        healthBarStyle.background = drawable;

        pixmap = new Pixmap(20, 0, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.ORANGE);
        pixmap.fill();
        drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();
        oxygenBarStyle.knob = drawable;

        pixmap = new Pixmap(0, 20, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.GREEN);
        pixmap.fill();
        drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();
        healthBarStyle.knob = drawable;

        pixmap = new Pixmap(20, 100, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.BLUE);
        pixmap.fill();
        drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();
        oxygenBarStyle.knobBefore = drawable;

        pixmap = new Pixmap(100, 20, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.GREEN);
        pixmap.fill();
        drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();
        healthBarStyle.knobBefore = drawable;

        // xpBarStyle

        pixmap = new Pixmap(Gdx.graphics.getWidth(), Gdx.graphics.getHeight() / 50, Pixmap.Format.RGBA8888);
        pixmap.setColor(0, 0, 0, 0);
        pixmap.fill();
        drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();
        xpBarStyle = new ProgressBar.ProgressBarStyle();
        xpBarStyle.background = drawable;

        pixmap = new Pixmap(0, Gdx.graphics.getHeight() / 50, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.ORANGE);
        pixmap.fill();
        drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();
        xpBarStyle.knob = drawable;

        pixmap = new Pixmap(Gdx.graphics.getWidth(), Gdx.graphics.getHeight() / 50, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.ORANGE);
        pixmap.fill();
        drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();
        xpBarStyle.knobBefore = drawable;
    }

    private void updateHealth(Integer health) {
        // TODO update health render
        healthBar.setValue(health);
        label.setText(health.toString());
    }

    private void updateOxygenLevel() {
        oxygenBar.setValue(player.getOxygenLevel());
    }

    private void updateWeaponStats(WeaponStats weaponStats, int weaponIndex) {
        if (player.getWeaponList().get(weaponIndex).getCurrentReloadTime() == 0)
            weaponStats.getWeaponSlot().setValue(100);
        else weaponStats.getWeaponSlot().setValue((player.getWeaponList().get(weaponIndex).getCurrentReloadTime() /
                player.getWeaponList().get(weaponIndex).getReloadTime()) * 100);

        weaponStats.getAmmoBar().setValue(player.getWeaponList().get(weaponIndex).getCurrentMag());
    }

    public void dispose() {
        stage.dispose();
    }

    public void debugMinimap(Sprite minimap) {
        Image image1 = new Image(minimap);
        //image1.setSize(1000, 500);
        image1.setPosition(0, 0);
        image1.setOrigin(950, 500);
        stage.addActor(image1);
    }

}
