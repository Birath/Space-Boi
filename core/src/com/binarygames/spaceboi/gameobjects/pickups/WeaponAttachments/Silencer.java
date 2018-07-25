package com.binarygames.spaceboi.gameobjects.pickups.WeaponAttachments;

import com.binarygames.spaceboi.Assets;
import com.binarygames.spaceboi.gameobjects.GameWorld;
import com.binarygames.spaceboi.gameobjects.entities.weapons.Weapon;

public class Silencer extends WeaponAttachment {

    private String name = "Silencer";
    private String description = "Makes things go pew pew";
    private String icon = Assets.UI_SILENCER_ICON;

    private boolean isEquipped;

    private int bulletSpeedChange = 1;
    private int recoilChange = 2;

    public Silencer(GameWorld gameWorld, float x, float y, String path, float mass, float radius) {
        super(gameWorld, x, y, path, mass, radius);
    }


    @Override
    public void applyAttachment(Weapon weapon) {
        if (weapon.getRecoil() > recoilChange) {
            weapon.setRecoil(weapon.getRecoil() - recoilChange);
        }
        weapon.setBulletSpeed(weapon.getBulletSpeed() + bulletSpeedChange);
    }

    @Override
    public void removeAttachment(Weapon weapon) {
        weapon.setRecoil(weapon.getRecoil() + recoilChange);

        weapon.setBulletSpeed(weapon.getBulletSpeed() + bulletSpeedChange);
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
