package com.binarygames.spaceboi.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Payload;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Source;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Target;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.binarygames.spaceboi.Assets;
import com.binarygames.spaceboi.gameobjects.GameWorld;
import com.binarygames.spaceboi.gameobjects.entities.weapons.Weapon;
import com.binarygames.spaceboi.gameobjects.pickups.WeaponAttachments.WeaponAttachment;
import com.binarygames.spaceboi.screens.GameScreen;

import java.util.ArrayList;

public class InventoryUI {

    private Stage stage;

    private GameScreen gamesScreen;
    private GameWorld gameWorld;

    private Skin uiSkin;

    private BitmapFont labelFont;
    private LabelStyle labelStyle;

    private boolean isVisible;

    private Table currentInventory;
    private Table weaponsTable;

    private Label helpLabel;

    private WeaponAttachment selectedAttachment;

    private ArrayList<AttachmentActor> weaponAttachmentSlots;

    /*
        Hovra över vapen, attachments för att visa info
        markera blir gul, alla ställen den passar i blir gröna
        andra blir röda
     */

    public InventoryUI(GameScreen gameScreen, GameWorld gameWorld) {
        this.gamesScreen = gameScreen;
        this.gameWorld = gameWorld;

        stage = new Stage();

        weaponAttachmentSlots = new ArrayList<>();

        uiSkin = gameWorld.getGame().getAssetManager().get(Assets.MENU_UI_SKIN, Skin.class);
        labelFont = gameWorld.getGame().getAssetManager().get(Assets.LABEL_FONT, BitmapFont.class);
        uiSkin.get("default", LabelStyle.class).font = labelFont;

        labelStyle = uiSkin.get("title-1", LabelStyle.class);

        // Advices
        helpLabel = new Label("Click and hold to drag an attachment", uiSkin);
        helpLabel.setPosition(20, 20);
        stage.addActor(helpLabel);

        DragAndDrop dragAndDrop = new DragAndDrop();
        //dragAndDrop.addTarget(DragAndDrop.Target);

         /*
            Current inventory
          */
        currentInventory = new Table();
        currentInventory.setPosition(800, 450);
        stage.addActor(currentInventory);

        Label inventoryLabel = new Label("Inventory", uiSkin);
        currentInventory.add(inventoryLabel);

         /*
            Weapons
          */
        weaponsTable = new Table();
        weaponsTable.setPosition(300, 450);
        stage.addActor(weaponsTable);

        for (Weapon weapon : gameWorld.getPlayer().getWeaponList()) {
            Label weaponLabel = new Label(weapon.getName(), uiSkin);
            weaponsTable.add(weaponLabel).colspan(3);
            weaponsTable.row();

            // Add attachment
            for (int i = 0; i < 3; i++) {
                Sprite attachmentSprite = new Sprite((gameWorld.getGame().getAssetManager().get(Assets.UI_EMPTY_ATTACHMENT, Texture.class)));
                //Sprite attachmentSprite = new Sprite((gameWorld.getGame().getAssetManager().get(Assets.UI_GRENADE_LAUNCHER_AMMO, Texture.class)));
                attachmentSprite.setSize(50, 50);

                AttachmentActor attachmentActor = new AttachmentActor(new SpriteDrawable(attachmentSprite));
                attachmentActor.setWeapon(weapon);
                attachmentActor.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        if (attachmentActor.hasAttachment()) {
                            weapon.removeAttachment(attachmentActor.getAttachment());
                            attachmentActor.setAttachment(null);
                            attachmentActor.setSprite(new SpriteDrawable(attachmentSprite));
                            updateInventory();
                        }
                    }
                });
                weaponAttachmentSlots.add(attachmentActor);
                weaponsTable.add(attachmentActor);
            }
            weaponsTable.row();
        }

        stage.setDebugAll(true);
    }

    private void updateInventory() {
        /*
            Current inventory
          */
        int columns = 3;

        currentInventory = new Table();
        currentInventory.setPosition(800, 450);
        stage.addActor(currentInventory);

        Label inventoryLabel = new Label("Inventory", uiSkin);
        currentInventory.add(inventoryLabel).colspan(columns);

        for (WeaponAttachment attachment : gameWorld.getPlayer().getInventory()) {
            if (!attachment.isEquipped()) {
                currentInventory.row();

                Sprite attachmentSprite = new Sprite((gameWorld.getGame().getAssetManager().get(attachment.getIcon(), Texture.class)));
                attachmentSprite.setSize(50, 50);
                AttachmentActor attachmentActor = new AttachmentActor(new SpriteDrawable(attachmentSprite));
                attachmentActor.setAttachment(attachment);
                currentInventory.add(attachmentActor);

                DragAndDrop dragAndDrop = new DragAndDrop();
                dragAndDrop.addSource(new Source(attachmentActor) {
                    @Override
                    public Payload dragStart(InputEvent event, float x, float y, int pointer) {
                        Payload payload = new Payload();
                        payload.setObject(attachmentActor);
                        payload.setDragActor(attachmentActor);

                        Label validLabel = new Label("Some payload!", uiSkin);
                        validLabel.setColor(0, 1, 0, 1);
                        payload.setValidDragActor(attachmentActor);

                        Label invalidLabel = new Label("Some payload!", uiSkin);
                        invalidLabel.setColor(1, 0, 0, 1);
                        payload.setInvalidDragActor(attachmentActor);

                        return payload;
                    }

                    @Override
                    public void dragStop(InputEvent event, float x, float y, int pointer, Payload payload, Target target) {
                        if (target == null) {

                        }
                    }
                });

                for (AttachmentActor validTarget : getValidTargets()) {
                    dragAndDrop.addTarget(new Target(validTarget) {
                        public boolean drag(Source source, Payload payload, float x, float y, int pointer) {
                            getActor().setColor(Color.GREEN);
                            return true;
                        }

                        public void reset(Source source, Payload payload) {
                            getActor().setColor(Color.WHITE);
                        }

                        public void drop(Source source, Payload payload, float x, float y, int pointer) {
                            //System.out.println("Accepted: " + payload.getObject() + " " + x + ", " + y);
                            Gdx.app.log("InventoryUI", "Payload " + payload.getObject());

                            WeaponAttachment attachment = ((AttachmentActor) payload.getObject()).getAttachment();
                            validTarget.setAttachment(attachment);
                            Sprite attachmentSprite = new Sprite((gameWorld.getGame().getAssetManager().get(attachment.getIcon(), Texture.class)));
                            attachmentSprite.setSize(50, 50);
                            validTarget.setSprite(new SpriteDrawable(attachmentSprite));

                            attachment.setEquipped(true);
                            validTarget.getWeapon().addAttachment(attachment);
                        }
                    });
                }

                for (AttachmentActor invalidTarget : getInvalidTargets()) {
                    dragAndDrop.addTarget(new Target(invalidTarget) {
                        public boolean drag(Source source, Payload payload, float x, float y, int pointer) {
                            getActor().setColor(Color.RED);
                            return false;
                        }

                        public void reset(Source source, Payload payload) {
                            getActor().setColor(Color.WHITE);
                        }

                        public void drop(Source source, Payload payload, float x, float y, int pointer) {
                        }
                    });
                }

                currentInventory.row();
                Label attachmentDesc = new Label(attachment.getName() + " - " + attachment.getDescription(), uiSkin);
                //currentInventory.add(attachmentDesc);
            }
        }
    }

    private ArrayList<AttachmentActor> getValidTargets() {
        ArrayList<AttachmentActor> validTargets = new ArrayList<>();
        for (AttachmentActor target : weaponAttachmentSlots) {
            if (!target.hasAttachment()) {
                validTargets.add(target);
            }
        }
        return validTargets;
    }

    private ArrayList<AttachmentActor> getInvalidTargets() {
        ArrayList<AttachmentActor> invalidTargets = new ArrayList<>();
        for (AttachmentActor target : weaponAttachmentSlots) {
            if (target.hasAttachment()) {
                invalidTargets.add(target);
            }
        }
        return invalidTargets;
    }

    public void update(float delta) {
        stage.act(delta);

        if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
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

    public boolean isVisible() {
        return isVisible;
    }

    public Stage getStage() {
        return stage;
    }
}
