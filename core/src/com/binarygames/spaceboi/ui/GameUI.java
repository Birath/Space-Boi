package com.binarygames.spaceboi.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.binarygames.spaceboi.Assets;
import com.binarygames.spaceboi.SpaceBoi;
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

    private WeaponStats weaponStats1;
    private WeaponStats weaponStats2;
    private WeaponStats weaponStats3;

    private Player player;

    private Fonts fonts;


    public GameUI(SpaceBoi game, Player player) {
        this.player = player;
        stage = new Stage();
        fonts = new Fonts();

        //loadFonts();
        loadStyles();
        health = 5;

        // Health icon
        texture = game.getAssetManager().get(Assets.UI_HEALTH_ICON, Texture.class);
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
        oxygenBar = new ProgressBar(0, 100, 1, true, oxygenBarStyle);
        oxygenBar.setValue(oxygen);
        oxygenBar.setBounds(Gdx.graphics.getWidth() * 39 / 40, Gdx.graphics.getHeight() / 10, 20, 100);
        stage.addActor(oxygenBar);

        // Health bar
        healthBar = new ProgressBar(0, 100, 1, false, healthBarStyle);
        healthBar.setValue(health);
        healthBar.setBounds(Gdx.graphics.getWidth() * 17 / 20, image.getOriginY() - healthBar.getHeight() / 2, 100, 20);
        stage.addActor(healthBar);


        weaponStats1 = new WeaponStats(stage,stage.getWidth()/20, stage.getHeight()*9/10, player.getWeaponList().get(0).getMagSize());
        weaponStats2 = new WeaponStats(stage,stage.getWidth()*3/20, stage.getHeight()*9/10, player.getWeaponList().get(1).getMagSize());
        weaponStats3 = new WeaponStats(stage,stage.getWidth()*5/20, stage.getHeight()*9/10, player.getWeaponList().get(2).getMagSize());

    }

    public void act(float delta) {
        updateHealth(player.getHealth());
        updateWeaponStats(weaponStats1, 0);
        updateWeaponStats(weaponStats2, 1);
        updateWeaponStats(weaponStats3, 2);

        stage.act();
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
    }

    private void updateHealth(Integer health) {
        // TODO update health render
        healthBar.setValue(health);
        label.setText(health.toString());
    }

    private void updateWeaponStats(WeaponStats weaponStats, int weaponIndex) {
            if(player.getWeaponList().get(weaponIndex).getCurrentReloadTime() == 0) weaponStats.getWeaponSlot().setValue(100);
            else weaponStats.getWeaponSlot().setValue((player.getWeaponList().get(weaponIndex).getCurrentReloadTime()/
                    player.getWeaponList().get(weaponIndex).getReloadTime())*100);

            weaponStats.getAmmoBar().setValue(player.getWeaponList().get(weaponIndex).getCurrentMag());
    }

    public void dispose() {
        stage.dispose();
    }

}
