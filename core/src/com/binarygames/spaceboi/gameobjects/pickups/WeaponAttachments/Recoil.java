package com.binarygames.spaceboi.gameobjects.pickups.WeaponAttachments;

import com.binarygames.spaceboi.Assets;
import com.binarygames.spaceboi.gameobjects.GameWorld;
import com.binarygames.spaceboi.gameobjects.entities.weapons.Weapon;

public class Recoil extends WeaponAttachment {

    private String name = "Recoil";
    private String description = "Increase weapon recoil";
    private String icon = Assets.RECOIL_ICON;

    private boolean isEquipped;

    private float recoilFactor = 1.5f;

    public Recoil(GameWorld gameWorld, float x, float y, String path, float mass, float radius) {
        super(gameWorld, x, y, path, mass, radius);
    }

    @Override
    public void applyAttachment(Weapon weapon) {
        weapon.setRecoil(weapon.getRecoil() * recoilFactor);
    }

    @Override
    public void removeAttachment(Weapon weapon) {
        weapon.setRecoil(weapon.getRecoil() / recoilFactor);
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
