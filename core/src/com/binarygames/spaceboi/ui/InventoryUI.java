package com.binarygames.spaceboi.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.binarygames.spaceboi.gameobjects.GameWorld;
import com.binarygames.spaceboi.gameobjects.entities.weapons.Weapon;
import com.binarygames.spaceboi.screens.Fonts;
import com.binarygames.spaceboi.screens.GameScreen;

public class InventoryUI {

    private Stage stage;

    private GameScreen gamesScreen;
    private GameWorld gameWorld;

    private LabelStyle labelStyle;
    private Button.ButtonStyle buttonStyle;

    private Fonts fonts;

    private boolean isVisible;

    public InventoryUI(GameScreen gamesScreen, GameWorld gameWorld) {
        this.gamesScreen = gamesScreen;
        this.gameWorld = gameWorld;

        stage = new Stage();
        fonts = new Fonts();
        loadStyles();

         /*
            Current inventory
          */
        Table currentInventory = new Table();
        stage.addActor(currentInventory);

         /*
            Weapons
          */
        Table weaponsTable = new Table();
        for (Weapon weapon : gameWorld.getPlayer().getWeaponList()) {
            Label weaponLabel = new Label(weapon.getName(), labelStyle);
            Label ammoLabel = new Label(weapon.getMagSize() + "/∞", labelStyle);

            weaponsTable.add(weaponLabel);
            weaponsTable.row();
            weaponsTable.add(ammoLabel);
            weaponsTable.row();
        }
        stage.addActor(weaponsTable);

        stage.setDebugAll(true);
    }

    public void update(float delta) {
        stage.act(delta);

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            hide();
        }
    }

    public void render() {
        stage.draw();
    }

    public void show() {
        Gdx.input.setInputProcessor(stage);
        isVisible = true;
    }

    private void hide() {
        Gdx.input.setInputProcessor(gamesScreen.getMultiplexer());
        isVisible = false;
    }

    public void dispose() {
        stage.dispose();
    }

    private void loadStyles() {
        labelStyle = new LabelStyle();
        labelStyle.font = fonts.getLabelFont();

        buttonStyle = new Button.ButtonStyle();
        // TODO add TextDrawable to hover
    }

    public boolean isVisible() {
        return isVisible;
    }

}
