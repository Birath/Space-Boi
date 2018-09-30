package com.binarygames.spaceboi.gameobjects.pickups.WeaponAttachments;

import com.binarygames.spaceboi.Assets;
import com.binarygames.spaceboi.gameobjects.GameWorld;
import com.binarygames.spaceboi.gameobjects.entities.weapons.Weapon;

public class MechDamage extends WeaponAttachment {
    private String name = "Mech Damage";
    private String description = "Deal extra damage to mechanical enemies";
    private String icon = Assets.ARMOR_PIERCING;

    private boolean isEquipped;

    private int mechDamageAmount = 2;

    public MechDamage(GameWorld gameWorld, float x, float y, String path, float mass, float radius) {
        super(gameWorld, x, y, path, mass, radius);
    }

    @Override
    public void applyAttachment(Weapon weapon) {
        weapon.setMechDamage(weapon.getMechDamage() + mechDamageAmount);
    }

    @Override
    public void removeAttachment(Weapon weapon) {
        weapon.setMechDamage(weapon.getMechDamage() - mechDamageAmount);
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
