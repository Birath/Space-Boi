package com.binarygames.spaceboi.gameobjects.pickups.WeaponAttachments;

import com.binarygames.spaceboi.Assets;
import com.binarygames.spaceboi.gameobjects.GameWorld;
import com.binarygames.spaceboi.gameobjects.entities.weapons.Weapon;

public class Slow extends WeaponAttachment {
    private String name = "Slow";
    private String description = "Slows enemies when you shoot them";
    private String icon = Assets.UI_SLOW_ICON;

    private boolean isEquipped;

    private int slowAmount = 1;

    public Slow(GameWorld gameWorld, float x, float y, String path, float mass, float radius) {
        super(gameWorld, x, y, path, mass, radius);
    }

    @Override
    public void applyAttachment(Weapon weapon) {
        weapon.setSlow(weapon.getSlow() + slowAmount);
    }

    @Override
    public void removeAttachment(Weapon weapon) {
        weapon.setSlow(weapon.getSlow() - slowAmount);
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
