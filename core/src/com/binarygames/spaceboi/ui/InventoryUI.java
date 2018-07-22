package com.binarygames.spaceboi.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.binarygames.spaceboi.Assets;
import com.binarygames.spaceboi.gameobjects.GameWorld;
import com.binarygames.spaceboi.gameobjects.entities.weapons.Weapon;
import com.binarygames.spaceboi.gameobjects.pickups.WeaponAttachments.WeaponAttachment;
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

    private Table currentInventory;
    private Table weaponsTable;

    public InventoryUI(GameScreen gamesScreen, GameWorld gameWorld) {
        this.gamesScreen = gamesScreen;
        this.gameWorld = gameWorld;

        stage = new Stage();
        fonts = new Fonts();
        loadStyles();

         /*
            Current inventory
          */
        currentInventory = new Table();
        currentInventory.setPosition(800, 450);
        stage.addActor(currentInventory);

         /*
            Weapons
          */
        weaponsTable = new Table();
        weaponsTable.setPosition(300, 450);
        stage.addActor(weaponsTable);

        stage.setDebugAll(true);
    }

    private void updateInventory() {
        currentInventory.clear();
        currentInventory.setPosition(800, 450);
        Label inventoryLabel = new Label("Inventory", labelStyle);
        currentInventory.add(inventoryLabel).align(Align.left);
        currentInventory.row();
        // Add current inventory
        int columns = 4;
        for (WeaponAttachment attachment : gameWorld.getPlayer().getInventory()) {
            currentInventory.row();
            Button attachmentButton = new Button(new SpriteDrawable(new Sprite((gameWorld.getGame().getAssetManager().get(Assets.UI_SILENCER_ICON, Texture.class)))));
            attachmentButton.setScale(0.5f);
            attachmentButton.setSize(75, 75);

            currentInventory.add(attachmentButton);

            //Label attachmentName = new Label(attachment.getName(), labelStyle);
            //currentInventory.add(attachmentName);

            currentInventory.row();
            Label attachmentDesc = new Label(attachment.getDescription(), labelStyle);
            currentInventory.add(attachmentDesc);
        }

        weaponsTable.clear();
        weaponsTable.setPosition(300, 450);
        Label weaponsLabel = new Label("Weapons", labelStyle);
        weaponsTable.add(weaponsLabel).align(Align.left);
        for (Weapon weapon : gameWorld.getPlayer().getWeaponList()) {
            Label weaponLabel = new Label(weapon.getName(), labelStyle);
            Label ammoLabel = new Label(weapon.getMagSize() + "/∞", labelStyle);

            weaponsTable.row();
            weaponsTable.add(weaponLabel);
            weaponsTable.row();
            weaponsTable.add(ammoLabel);
        }
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
        updateInventory();
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
