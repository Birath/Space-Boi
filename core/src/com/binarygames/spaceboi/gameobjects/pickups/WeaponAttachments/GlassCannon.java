package com.binarygames.spaceboi.gameobjects.pickups.WeaponAttachments;

import com.binarygames.spaceboi.Assets;
import com.binarygames.spaceboi.gameobjects.GameWorld;
import com.binarygames.spaceboi.gameobjects.entities.weapons.Weapon;

public class GlassCannon extends WeaponAttachment {

    private String name = "Glass Cannon";
    private String description = "Increase both damage dealt and taken dramatically";
    private String icon = Assets.GLASS_CANNON_ICON;

    private boolean isEquipped;

    public static final float damageTakenFactor = 1.5f;
    private static final float damageGivenFactor = 2f;

    public GlassCannon(GameWorld gameWorld, float x, float y, String path, float mass, float radius) {
        super(gameWorld, x, y, path, mass, radius);
    }

    @Override
    public void applyAttachment(Weapon weapon) {
        weapon.setDamage(Math.round(weapon.getDamage() * damageGivenFactor));
    }

    @Override
    public void removeAttachment(Weapon weapon) {
        weapon.setDamage(Math.round(weapon.getDamage() / damageGivenFactor));
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
