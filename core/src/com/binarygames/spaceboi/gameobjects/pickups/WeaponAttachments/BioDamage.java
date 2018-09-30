package com.binarygames.spaceboi.gameobjects.pickups.WeaponAttachments;

import com.binarygames.spaceboi.Assets;
import com.binarygames.spaceboi.gameobjects.GameWorld;
import com.binarygames.spaceboi.gameobjects.entities.weapons.Weapon;

public class BioDamage extends WeaponAttachment {
    private String name = "Bio Damage";
    private String description = "Deal extra damage to biological enemies";
    private String icon = Assets.BIO_DAMAGE_ICON;

    private boolean isEquipped;

    private int bioDamageAmount = 2;

    public BioDamage(GameWorld gameWorld, float x, float y, String path, float mass, float radius) {
        super(gameWorld, x, y, path, mass, radius);
    }
    @Override
    public void applyAttachment(Weapon weapon) {
        weapon.setBioDamage(weapon.getBioDamage() + bioDamageAmount);
    }

    @Override
    public void removeAttachment(Weapon weapon) {
        weapon.setBioDamage(weapon.getBioDamage() - bioDamageAmount);
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
