package com.binarygames.spaceboi.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.binarygames.spaceboi.Assets;
import com.binarygames.spaceboi.SpaceBoi;
import com.binarygames.spaceboi.gameobjects.pickups.WeaponAttachments.WeaponAttachment;

public class AttachmentButton extends Button {

    private WeaponAttachment attachment;

    private float defaultSide = 75;

    public AttachmentButton(SpaceBoi game, Drawable drawable) {
        super(drawable);

        Sprite background = new Sprite(game.getAssetManager().get(Assets.UI_EMPTY_ATTACHMENT, Texture.class));
        background.setSize(defaultSide, defaultSide);
        //setBackground(new SpriteDrawable(background));
    }

    public void setAttachment(WeaponAttachment attachment) {
        this.attachment = attachment;
    }

    public WeaponAttachment getAttachment() {
        return attachment;
    }

    public boolean hasAttachment() {
        return attachment != null;
    }
}
