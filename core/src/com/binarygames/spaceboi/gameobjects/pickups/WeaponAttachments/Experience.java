package com.binarygames.spaceboi.gameobjects.pickups.WeaponAttachments;

import com.binarygames.spaceboi.Assets;
import com.binarygames.spaceboi.gameobjects.GameWorld;
import com.binarygames.spaceboi.gameobjects.entities.weapons.Weapon;

public class Experience extends WeaponAttachment {

    private String name = "Experience";
    private String description = "Gain more XP";
    private String icon = Assets.XP_BOOST_ICON;

    private boolean isEquipped;

    private float xpFactor = 0.5f;

    public Experience(GameWorld gameWorld, float x, float y, String path, float mass, float radius) {
        super(gameWorld, x, y, path, mass, radius);
    }

    @Override
    public void applyAttachment(Weapon weapon) {
        weapon.setXpFactor(weapon.getXpFactor() + xpFactor);
    }

    @Override
    public void removeAttachment(Weapon weapon) {
        weapon.setXpFactor(weapon.getXpFactor() - xpFactor);
    }

    @Override
    public boolean isEquipped() {
        return isEquipped;
    }

    @Override
    public void setEquipped(boolean equipped) {
        isEquipped = equipped;
    }

    @Override
    public String getIcon() {
        return icon;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
