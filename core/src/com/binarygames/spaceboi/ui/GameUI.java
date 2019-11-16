package com.binarygames.spaceboi.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.binarygames.spaceboi.Assets;
import com.binarygames.spaceboi.SpaceBoi;
import com.binarygames.spaceboi.background_functions.XPHandler;
import com.binarygames.spaceboi.gameobjects.entities.Player;
import com.binarygames.spaceboi.screens.Fonts;

public class GameUI {

    private static final int MAX_WIDTH = 3840;
    private Stage stage;

    private LabelStyle labelStyle;

    private TextButtonStyle buttonStyle;

    private SpaceBoi game;

    private Texture texture;

    private Image healthImage;
    private Image oxygenImage;

    private int healthBarWidth, healthBarHeight, oxygenBarWidth, oxygenBarHeight;

    private float ui_scale;

    private Label health_status;

    private Label oxygen_status;

    private ProgressBar oxygenBar;
    private ProgressBar.ProgressBarStyle oxygenBarStyle;

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

    private float scaling_factor;

    public GameUI(SpaceBoi game, Player player, XPHandler xpHandler) {
        this.player = player;
        this.xpHandler = xpHandler;
        this.game = game;
        stage = new Stage();
        fonts = new Fonts();

        stage.setDebugAll(false);

        ui_scale = .75F; // Change UI scale with this parameter

        scaling_factor = (stage.getWidth() / MAX_WIDTH) * ui_scale;
        Gdx.app.log("debug scaling", String.valueOf(scaling_factor));

        // Oxygen icon
        texture = game.getAssetManager().get(Assets.UI_OXYGEN_BAR, Texture.class);
        oxygenImage = new Image(texture);
        oxygenImage.setScaling(Scaling.fill);
        oxygenImage.setSize(texture.getWidth() * scaling_factor, texture.getHeight() * scaling_factor);
        oxygenImage.setPosition(stage.getWidth() - (5 * oxygenImage.getWidth() / 4), (oxygenImage.getWidth() / 2));
        oxygenImage.setOrigin(oxygenImage.getX() - oxygenImage.getWidth() / 2, oxygenImage.getY() - oxygenImage.getHeight() / 2);
        oxygenBarWidth = (int) oxygenImage.getWidth() - (int) (oxygenImage.getWidth() / 5);
        oxygenBarHeight = (int) oxygenImage.getHeight() - (int) (oxygenImage.getHeight() / 9);

        // Health bar outline
        texture = game.getAssetManager().get(Assets.UI_HEALTH_BAR, Texture.class);
        healthImage = new Image(texture);
        healthImage.setScaling(Scaling.fill);
        healthImage.setSize(texture.getWidth() * scaling_factor, texture.getHeight() * scaling_factor);
        healthImage.setOrigin(healthImage.getX() - healthImage.getWidth() / 2, healthImage.getY() - healthImage.getHeight() / 2);
        healthImage.setPosition(oxygenImage.getX() - (9 * healthImage.getWidth() / 8), oxygenImage.getY());
        healthBarWidth = (int) healthImage.getWidth() - (int) (healthImage.getWidth() / 10);
        healthBarHeight = (int) healthImage.getHeight() - (int) (healthImage.getHeight() / 5);

        loadStyles();

        // Health bar
        healthBar = new ProgressBar(0, player.getMaxHealth(), 1, false, healthBarStyle);
        healthBar.setOrigin(healthImage.getOriginX(), healthImage.getOriginY());
        healthBar.setBounds(healthImage.getX() + (healthImage.getWidth() - healthBarWidth) / 2, healthImage.getY() + (healthImage.getHeight() - healthBarHeight) / 2, healthBarWidth, healthBarHeight); // Todo

        // Health indicator
        health_status = new Label(player.getHealth() + " / " + player.getMaxHealth(), labelStyle);
        health_status.setPosition(healthImage.getX() + healthImage.getWidth() / 2 - health_status.getWidth() / 3,
                healthImage.getY() + healthImage.getHeight() / 2 - health_status.getHeight() / 2);
        health_status.setScale(0.8f);
        health_status.setVisible(false);

        healthImage.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                super.enter(event, x, y, pointer, fromActor);
                health_status.setVisible(true);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                super.exit(event, x, y, pointer, toActor);
                health_status.setVisible(false);
            }
        });

        health_status.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                super.enter(event, x, y, pointer, fromActor);
                health_status.setVisible(true);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                super.exit(event, x, y, pointer, toActor);
                health_status.setVisible(false);
            }
        });

        stage.addActor(healthBar);
        stage.addActor(healthImage);
        stage.addActor(health_status);

        // Oxygen bar
        oxygenBar = new ProgressBar(0, Player.MAX_OXYGEN_LEVEL, 1, true, oxygenBarStyle);
        oxygenBar.setValue(Player.MAX_OXYGEN_LEVEL);
        oxygenBar.setBounds(oxygenImage.getX() + (oxygenImage.getWidth() - oxygenBarWidth) / 2, oxygenImage.getY() + (oxygenImage.getHeight() - oxygenBarHeight) / 2, oxygenBarWidth, oxygenBarHeight);

        // Oxygen indicator
        oxygen_status = new Label(player.getOxygenLevel() + " / " + Player.MAX_OXYGEN_LEVEL, labelStyle);
        Group oxygen_group = new Group();
        oxygen_group.addActor(oxygen_status);
        oxygen_group.setOrigin(Align.center);
        oxygen_group.setTransform(true);
        oxygen_group.setRotation(90);
        oxygen_group.setPosition(oxygenImage.getX() + oxygenImage.getWidth() / 2 + oxygen_status.getHeight() / 2,
                oxygenImage.getY() + oxygenImage.getHeight() / 2 - 3 * oxygen_status.getWidth() / 4);
        oxygen_status.setScale(0.8f);
        oxygen_group.setVisible(false);

        oxygenImage.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                super.enter(event, x, y, pointer, fromActor);
                oxygen_group.setVisible(true);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                super.exit(event, x, y, pointer, toActor);
                oxygen_group.setVisible(false);
            }
        });

        oxygen_status.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                super.enter(event, x, y, pointer, fromActor);
                oxygen_group.setVisible(true);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                super.exit(event, x, y, pointer, toActor);
                oxygen_group.setVisible(false);
            }
        });

        stage.addActor(oxygenBar);
        stage.addActor(oxygenImage); // oxygen outline
        stage.addActor(oxygen_group); // oxygen status

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

        // Compass stuff

        // some filters for scaling
        Texture compassOutlineTexture = game.getAssetManager().get(Assets.UI_COMPASS_OUTLINE, Texture.class);
        compassOutlineTexture.setFilter(Texture.TextureFilter.MipMapLinearNearest, Texture.TextureFilter.MipMapLinearNearest);
        Texture compassArrowTexture = game.getAssetManager().get(Assets.UI_COMPASS_ARROW, Texture.class);
        compassArrowTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        Image compassOutline = new Image(compassOutlineTexture);
        compassOutline.setScale(0.2f); // scale to match original 133x133px image
        // Upper right corner
        //TODO add padding maybe?
        compassOutline.setPosition(stage.getWidth() - compassOutline.getWidth() * 0.2f, stage.getHeight() - compassOutline.getHeight() * 0.2f);
        stage.addActor(compassOutline);
        Image arrowSize = new Image(compassArrowTexture);
        CompassArrow arrow = new CompassArrow(
                game.getAssetManager().get(Assets.UI_COMPASS_ARROW, Texture.class),
                player,
                compassOutline.getX() + 0.2f * compassOutline.getWidth() / 2 - arrowSize.getWidth() / 2,
                compassOutline.getY() + 0.2f * compassOutline.getHeight() / 2 - arrowSize.getHeight() / 2
        );
        stage.addActor(arrow);


        int padding = 50;
        weaponStats1 = new WeaponStats(stage, (int)(0 + scaling_factor * padding), (int)(stage.getHeight() - scaling_factor * padding),
                player.getWeaponList().get(0).getMagSize(),
                game.getAssetManager().get(Assets.UI_SHOTGUN, Texture.class),
                game.getAssetManager().get(Assets.UI_SHOTGUN_AMMO, Texture.class), scaling_factor);
        weaponStats2 = new WeaponStats(stage, weaponStats1.getRightPositionX() + (int)(scaling_factor * padding), (int)(stage.getHeight() - scaling_factor * padding),
                player.getWeaponList().get(1).getMagSize(),
                game.getAssetManager().get(Assets.UI_ASSAULT_RIFLE, Texture.class),
                game.getAssetManager().get(Assets.UI_ASSAULT_RIFLE_AMMO, Texture.class), scaling_factor);
        weaponStats3 = new WeaponStats(stage, weaponStats2.getRightPositionX() + (int)(scaling_factor * padding), (int)(stage.getHeight() - scaling_factor * padding),
                player.getWeaponList().get(2).getMagSize(),
                game.getAssetManager().get(Assets.UI_GRENADE_LAUNCHER, Texture.class),
                game.getAssetManager().get(Assets.UI_GRENADE_LAUNCHER_AMMO, Texture.class), scaling_factor);
    }

    public void act(float delta) {
        healthBar.setRange(0, player.getMaxHealth());
        updateHealth(player.getHealth());
        updateOxygenLevel();
        updateWeaponStats(weaponStats1, 0, delta);
        updateWeaponStats(weaponStats2, 1, delta);
        updateWeaponStats(weaponStats3, 2, delta);
        currentLevel.setText(String.valueOf(xpHandler.getLevel()));
        nextLevel.setText(xpHandler.getCurrentXP() + " / " + xpHandler.getNextLevel());

        if ((xpBar.getValue() * (Gdx.graphics.getWidth() / xpBar.getMaxValue())) < nextLevel.getWidth()) {
            nextLevel.setPosition(Gdx.graphics.getWidth() / 2 - nextLevel.getWidth(), 0);
        } else {
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
        Pixmap pixmap = new Pixmap(oxygenBarWidth, oxygenBarHeight, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.BLACK);
        pixmap.fill();
        TextureRegionDrawable drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();
        oxygenBarStyle = new ProgressBar.ProgressBarStyle();
        oxygenBarStyle.background = drawable;

        pixmap = new Pixmap(healthBarWidth, healthBarHeight, Pixmap.Format.RGBA8888);
        pixmap.setColor(new Color(0x4a963aff));
        pixmap.fill();
        drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();
        healthBarStyle = new ProgressBar.ProgressBarStyle();
        healthBarStyle.background = drawable;

        pixmap = new Pixmap(oxygenBarWidth, 0, Pixmap.Format.RGBA8888);
        pixmap.setColor(new Color(0x3a9692ff));
        pixmap.fill();
        drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();
        oxygenBarStyle.knob = drawable;

        pixmap = new Pixmap(0, healthBarHeight, Pixmap.Format.RGBA8888);
        pixmap.setColor(new Color(0x4a963aff));
        pixmap.fill();
        drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();
        healthBarStyle.knob = drawable;

        pixmap = new Pixmap(oxygenBarWidth, oxygenBarHeight, Pixmap.Format.RGBA8888);
        pixmap.setColor(new Color(0x3a9692ff));
        pixmap.fill();
        drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();
        oxygenBarStyle.knobBefore = drawable;

        pixmap = new Pixmap(healthBarWidth, healthBarHeight, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.BLACK);
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
        //healthBar.setValue(health);
        //health_status.setText(health + " / " + player.getMaxHealth());
        healthBar.setValue(player.getMaxHealth() - health);
        health_status.setText(health + " / " + player.getMaxHealth());
    }

    private void updateOxygenLevel() {
        oxygenBar.setValue(player.getOxygenLevel());
        oxygen_status.setText((int) player.getOxygenLevel() + " / " + Player.MAX_OXYGEN_LEVEL);
    }

    private void updateWeaponStats(WeaponStats weaponStats, int weaponIndex, float delta) {
        if (player.getWeaponList().get(weaponIndex).getCurrentReloadTime() == 0)
            weaponStats.getWeaponSlot().setValue(100);
        else weaponStats.getWeaponSlot().setValue((player.getWeaponList().get(weaponIndex).getCurrentReloadTime() /
                player.getWeaponList().get(weaponIndex).getReloadTime()) * 100);

        weaponStats.act(player.getWeaponList().get(weaponIndex).getCurrentMag());
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
