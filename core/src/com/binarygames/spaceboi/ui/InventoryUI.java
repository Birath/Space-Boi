package com.binarygames.spaceboi.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.*;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Payload;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Source;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Target;
import com.badlogic.gdx.utils.Align;
import com.binarygames.spaceboi.Assets;
import com.binarygames.spaceboi.gameobjects.GameWorld;
import com.binarygames.spaceboi.gameobjects.entities.weapons.Weapon;
import com.binarygames.spaceboi.gameobjects.pickups.WeaponAttachments.WeaponAttachment;
import com.binarygames.spaceboi.screens.GameScreen;

import java.util.ArrayList;

public class InventoryUI {

    public static final int ATTACHMENT_WIDTH = 50;
    public static final int ATTACHMENT_HEIGHT = 50;
    public static final int LABEL_PADDING = 10;
    public static final int CELL_PADDING = 5;
    public static final int HELP_LABEL_WIDTH = 700;
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

    private static final int INVENTORY_SIZE = 9;
    private static final int COLUMN_COUNT = 3;

    private Sprite emptySprite;

    /*
        Hovra över vapen, attachments för att visa info
        markera blir gul, alla ställen den passar i blir gröna
        andra blir röda
     */

    public InventoryUI(GameScreen gameScreen, GameWorld gameWorld) {
        this.gamesScreen = gameScreen;
        this.gameWorld = gameWorld;

        emptySprite = new Sprite((gameWorld.getGame().getAssetManager().get(Assets.UI_EMPTY_ATTACHMENT, Texture.class)), ATTACHMENT_WIDTH, ATTACHMENT_HEIGHT);

        stage = new Stage();

        weaponAttachmentSlots = new ArrayList<>();

        uiSkin = gameWorld.getGame().getAssetManager().get(Assets.MENU_UI_SKIN, Skin.class);
        labelFont = gameWorld.getGame().getAssetManager().get(Assets.LABEL_FONT, BitmapFont.class);
        uiSkin.get("default", LabelStyle.class).font = labelFont;

        labelStyle = uiSkin.get("title-1", LabelStyle.class);

        // Advices
        helpLabel = new Label("Click and hold to drag an attachment", uiSkin);
        helpLabel.setPosition(20, 20);
        helpLabel.setWrap(true);
        helpLabel.setWidth(HELP_LABEL_WIDTH);
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
                AttachmentActor attachmentActor = new AttachmentActor(new SpriteDrawable(emptySprite));
                attachmentActor.setWeapon(weapon);
                attachmentActor.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        Gdx.app.log("InventoryUI", "Changed!");

                        if (attachmentActor.hasAttachment()) {
                            weapon.removeAttachment(attachmentActor.getAttachment());
                            attachmentActor.setAttachment(null);
                            attachmentActor.setDrawable(new SpriteDrawable(emptySprite));
                            updateInventory();
                        }
                    }
                });
                attachmentActor.addListener(
                    new ClickListener() {
                        @Override
                        public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                            if (attachmentActor.getAttachment() != null) {
                                helpLabel.setText(attachmentActor.getAttachment().getName() + " - " + attachmentActor.getAttachment().getDescription());
                            }
                        }

                        @Override
                        public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                            if (!(toActor instanceof AttachmentActor)) {
                                helpLabel.setText("Click and hold to drag an attachment");
                            }
                        }
                    }
                );
                weaponAttachmentSlots.add(attachmentActor);
                weaponsTable.add(attachmentActor);
            }
            weaponsTable.row();
        }
    }

    private void updateInventory() {
        /*
            Current inventory
          */

        currentInventory.reset();
        // TODO Fix borders and padding to all cells
        Label inventoryLabel = new Label("Inventory", uiSkin);
        currentInventory.padBottom(LABEL_PADDING);
        currentInventory.add(inventoryLabel).colspan(COLUMN_COUNT);

        for (int i = 0; i < INVENTORY_SIZE; i++) {
            if (i % COLUMN_COUNT == 0) {
                currentInventory.row();
            }
            WeaponAttachment attachment = gameWorld.getPlayer().getInventory().get(i);
            if (!attachment.isEquipped()) {
                Sprite attachmentSprite = new Sprite((gameWorld.getGame().getAssetManager().get(attachment.getIcon(), Texture.class)));
                attachmentSprite.setSize(ATTACHMENT_WIDTH, ATTACHMENT_HEIGHT);
                AttachmentActor attachmentActor = new AttachmentActor(new SpriteDrawable(attachmentSprite));
                attachmentActor.setAttachment(attachment);
                attachmentActor.addListener(
                    new ClickListener() {
                        @Override
                        public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                            helpLabel.setText(attachment.getName() + " - " + attachment.getDescription());
                        }

                        @Override
                        public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                            if (!(toActor instanceof AttachmentActor)) {
                                helpLabel.setText("Click and hold to drag an attachment");
                            }
                        }
                    }
                );
                currentInventory.pad(CELL_PADDING);
                currentInventory.add(attachmentActor);

                DragAndDrop dragAndDrop = new DragAndDrop();
                Cell<AttachmentActor> sourceCell = currentInventory.getCell(attachmentActor);

                dragAndDrop.addSource(new Source(attachmentActor) {
                    @Override
                    public Payload dragStart(InputEvent event, float x, float y, int pointer) {
                        //Cell<AttachmentActor> sourceCell =  currentInventory.getCell(attachmentActor);
                        Payload payload = new Payload();
                        payload.setObject(attachmentActor);
                        payload.setDragActor(attachmentActor);
                        payload.setValidDragActor(attachmentActor);
                        payload.setInvalidDragActor(attachmentActor);

                        Group placeholder = new Group();
                        placeholder.addActor(attachmentActor);

                        AttachmentActor blank = new AttachmentActor(new SpriteDrawable(emptySprite));
                        blank.setSize(ATTACHMENT_WIDTH, ATTACHMENT_HEIGHT);
                        placeholder.addActor(blank);
                        placeholder.sizeBy(ATTACHMENT_WIDTH, ATTACHMENT_HEIGHT);
                        sourceCell.setActor(placeholder);

                        return payload;
                    }

                    @Override
                    public void dragStop(InputEvent event, float x, float y, int pointer, Payload payload, Target target) {
                        // Dropped on invalid target - Reset the original cell
                        if (target == null) {
                            sourceCell.setActor(payload.getDragActor());
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
                            WeaponAttachment attachment = ((AttachmentActor) payload.getObject()).getAttachment();
                            validTarget.setAttachment(attachment);
                            Sprite attachmentSprite = new Sprite((gameWorld.getGame().getAssetManager().get(attachment.getIcon(), Texture.class)));
                            attachmentSprite.setSize(ATTACHMENT_WIDTH, ATTACHMENT_HEIGHT);
                            validTarget.setDrawable(new SpriteDrawable(attachmentSprite));

                            attachment.setEquipped(true);
                            validTarget.getWeapon().addAttachment(attachment);
                            updateInventory();
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
            } else {
                currentInventory.add(new AttachmentActor(new SpriteDrawable(emptySprite)));
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
