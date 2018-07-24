package com.binarygames.spaceboi.gameobjects.pickups.WeaponAttachments;

import com.binarygames.spaceboi.gameobjects.GameWorld;
import com.binarygames.spaceboi.gameobjects.entities.weapons.Weapon;

public class Silencer extends WeaponAttachment {

    private String name = "Silencer";
    private String description = "Makes things go pew pew";

    private int bulletSpeedChange = 1;
    private int recoilChange = 2;

    public Silencer(GameWorld gameWorld, float x, float y, String path, float mass, float radius) {
        super(gameWorld, x, y, path, mass, radius);
    }


    @Override
    protected void applyAttachment(Weapon weapon) {
        if (weapon.getRecoil() > recoilChange) {
            weapon.setRecoil(weapon.getRecoil() - recoilChange);
        }
        weapon.setBulletSpeed(weapon.getBulletSpeed() + bulletSpeedChange);
    }

    @Override
    protected void removeAttachment(Weapon weapon) {
        weapon.setRecoil(weapon.getRecoil() + recoilChange);

        weapon.setBulletSpeed(weapon.getBulletSpeed() + bulletSpeedChange);
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
