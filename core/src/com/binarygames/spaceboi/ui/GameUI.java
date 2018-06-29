package com.binarygames.spaceboi.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.binarygames.spaceboi.Assets;
import com.binarygames.spaceboi.SpaceBoi;
import com.binarygames.spaceboi.screens.Fonts;

public class GameUI {

    private Stage stage;

    private LabelStyle labelStyle;

    private TextButtonStyle buttonStyle;

    private Texture texture;
    private Image image;

    private Label label;
    private Integer health;

    private ProgressBar jetPackBar;
    private ProgressBar.ProgressBarStyle jetPackBarStyle;
    private Integer jetPackFuel;

    private ProgressBar healthBar;
    private ProgressBar.ProgressBarStyle healthBarStyle;

    private Fonts fonts;

    public GameUI(SpaceBoi game) {
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
        jetPackFuel = 75; //
        jetPackBar = new ProgressBar(0, 100, 1, true, jetPackBarStyle);
        jetPackBar.setValue(jetPackFuel);
        jetPackBar.setBounds(Gdx.graphics.getWidth() * 39 / 40, Gdx.graphics.getHeight() / 10, 20, 100);
        stage.addActor(jetPackBar);

        // Health bar
        healthBar = new ProgressBar(0, 10, 1, false, healthBarStyle);
        healthBar.setValue(health);
        healthBar.setBounds(Gdx.graphics.getWidth() * 17 / 20, image.getOriginY() - healthBar.getHeight() / 2, 100, 20);
        stage.addActor(healthBar);
    }

    public void act(float delta) {
        stage.act();
    }

    public void draw() {
        stage.draw();
    }

    public Stage getStage() {
        return stage;
    }

    /*private void loadFonts() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Roboto-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        // Button font
        parameter.size = 36;
        parameter.color = Color.WHITE;
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 3;

        buttonFont = generator.generateFont(parameter);
        buttonFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        // Label font
        parameter.size = 24;
        parameter.color = Color.WHITE;
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 2;

        labelFont = generator.generateFont(parameter);
        labelFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        // Dispose of FontGenerator
        generator.dispose();
    }*/

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
        jetPackBarStyle = new ProgressBar.ProgressBarStyle();
        jetPackBarStyle.background = drawable;

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
        jetPackBarStyle.knob = drawable;

        pixmap = new Pixmap(0, 20, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.GREEN);
        pixmap.fill();
        drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();
        healthBarStyle.knob = drawable;

        pixmap = new Pixmap(20, 100, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.ORANGE);
        pixmap.fill();
        drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();
        jetPackBarStyle.knobBefore = drawable;

        pixmap = new Pixmap(100, 20, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.GREEN);
        pixmap.fill();
        drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();
        healthBarStyle.knobBefore = drawable;
    }

    public void updateHealth(Integer health) {
        // TODO update health render
        label.setText(health.toString());
    }

    public void dispose() {
        stage.dispose();
    }

}
