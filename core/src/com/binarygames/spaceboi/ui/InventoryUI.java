package com.binarygames.spaceboi.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
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
    private TextButton.TextButtonStyle textButtonStyle;

    private Fonts fonts;

    private boolean isVisible;

    private Table currentInventory;
    private Table weaponsTable;

    private Label helpLabel;

    private WeaponAttachment selectedAttachment;

    public InventoryUI(GameScreen gamesScreen, GameWorld gameWorld) {
        this.gamesScreen = gamesScreen;
        this.gameWorld = gameWorld;

        stage = new Stage();
        fonts = new Fonts();
        loadStyles();

        helpLabel = new Label("Click on an attachment", labelStyle);
        helpLabel.setPosition(20, 20);
        stage.addActor(helpLabel);

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
        /*
            Current inventory
          */
        currentInventory.clear();
        currentInventory.setPosition(800, 450);
        Label inventoryLabel = new Label("Inventory", labelStyle);
        currentInventory.add(inventoryLabel).align(Align.left);
        currentInventory.row();
        // Add current inventory
        int columns = 4;

        for (WeaponAttachment attachment : gameWorld.getPlayer().getInventory()) {
            if (!attachment.isEquipped()) {
                currentInventory.row();
                Sprite attachmentSprite = new Sprite((gameWorld.getGame().getAssetManager().get(attachment.getIcon(), Texture.class)));
                attachmentSprite.setSize(75, 75);
                Button attachmentButton = new Button(new SpriteDrawable(attachmentSprite));
                attachmentButton.addCaptureListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        selectedAttachment = attachment;
                        helpLabel.setText("Click on a weapon to add attachment to weapon");
                    }
                });

                currentInventory.add(attachmentButton);

                currentInventory.row();
                Label attachmentDesc = new Label(attachment.getName() + " - " + attachment.getDescription(), labelStyle);
                currentInventory.add(attachmentDesc);
            }
        }

        /*
            Weapons
          */
        weaponsTable.clear();
        weaponsTable.setPosition(300, 450);
        Label weaponsLabel = new Label("Weapons", labelStyle);
        weaponsTable.add(weaponsLabel).align(Align.center);
        for (Weapon weapon : gameWorld.getPlayer().getWeaponList()) {
            TextButton weaponButton = new TextButton(weapon.getName(), textButtonStyle);
            weaponButton.addCaptureListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {

                }
            });

            weaponsTable.row();
            weaponsTable.add(weaponButton);
            weaponsTable.row();

            for (int i = 0; i < 3; i++) {
                WeaponAttachment attachment = null;
                Sprite attachmentSprite;
                if (weapon.getAttachments().size() > i) {
                    attachment = weapon.getAttachments().get(i);
                    attachmentSprite = new Sprite((gameWorld.getGame().getAssetManager().get(attachment.getIcon(), Texture.class)));
                } else {
                    attachmentSprite = new Sprite((gameWorld.getGame().getAssetManager().get(Assets.UI_EMPTY_ATTACHMENT, Texture.class)));
                }
                attachmentSprite.setSize(75, 75);
                AttachmentButton attachmentButton = new AttachmentButton(gameWorld.getGame(), new SpriteDrawable(attachmentSprite));
                if (attachment != null) {
                    attachmentButton.setAttachment(attachment);
                }
                attachmentButton.addCaptureListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        // Remove attachment from weapon
                        if (selectedAttachment != null) {
                            // TODO FIX ALL THIS SHIT
                            if (weapon.addAttachment(selectedAttachment)) {
                                helpLabel.setText(selectedAttachment.getName() + " added to weapon " + weapon.getName());
                            } else {
                                helpLabel.setText("NO! yuou cant have many attachments!");
                            }
                            selectedAttachment = null;
                            updateInventory();
                        } else {
                            weapon.removeAttachment(attachmentButton.getAttachment());
                            attachmentButton.setAttachment(null);
                        }

                        if (attachmentButton.getAttachment() != null) {
                            weapon.removeAttachment(attachmentButton.getAttachment());
                        }
                        updateInventory();
                    }
                });
                weaponsTable.add(attachmentButton);
            }

            /*
            for (WeaponAttachment attachment : weapon.getAttachments()) {
                //Sprite attachmentSprite = new Sprite((gameWorld.getGame().getAssetManager().get(attachment.getIcon(), Texture.class)));
                Sprite attachmentSprite = new Sprite((gameWorld.getGame().getAssetManager().get(Assets.UI_EMPTY_ATTACHMENT, Texture.class)));
                attachmentSprite.setSize(75, 75);
                Button attachmentButton = new Button(new SpriteDrawable(attachmentSprite));
                attachmentButton.addCaptureListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        // Remove attachment from weapon
                        weapon.removeAttachment(attachment);
                        updateInventory();
                    }
                });
                weaponsTable.add(attachmentButton);
            }
            */
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

        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = fonts.getButtonFont();
    }

    public boolean isVisible() {
        return isVisible;
    }

}
