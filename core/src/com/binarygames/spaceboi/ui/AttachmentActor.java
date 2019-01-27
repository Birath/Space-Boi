package com.binarygames.spaceboi.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.binarygames.spaceboi.gameobjects.entities.weapons.Weapon;
import com.binarygames.spaceboi.gameobjects.pickups.WeaponAttachments.WeaponAttachment;

public class AttachmentActor extends Button {

    private WeaponAttachment attachment;
    private Weapon weapon;

    private float defaultSize = 100.0f;

    // This class should probably extend image but that
    // does not work with tables for some reason ¯\_(ツ)_/¯

    public AttachmentActor(Drawable drawable) {
        super(drawable);

        setSize(defaultSize, defaultSize);
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

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public Weapon getWeapon() {
        return weapon;
    }
}
