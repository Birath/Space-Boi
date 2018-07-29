package com.binarygames.spaceboi.gameobjects.pickups.WeaponAttachments;

import com.binarygames.spaceboi.Assets;
import com.binarygames.spaceboi.gameobjects.GameWorld;
import com.binarygames.spaceboi.gameobjects.entities.weapons.Weapon;

public class LifeSteal extends WeaponAttachment {
    private String name = "Lifesteal";
    private String description = "Heals you when you damage enemies";
    private String icon = Assets.UI_LIFESTEAL_ICON;

    private boolean isEquipped;

    private int lifeStealAmount = 2;

    public LifeSteal(GameWorld gameWorld, float x, float y, String path, float mass, float radius) {
        super(gameWorld, x, y, path, mass, radius);
    }

    @Override
    public void applyAttachment(Weapon weapon) {
        weapon.setLifeSteal(weapon.getLifeSteal() + lifeStealAmount);
    }

    @Override
    public void removeAttachment(Weapon weapon) {
        weapon.setLifeSteal(weapon.getLifeSteal() - lifeStealAmount);
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
