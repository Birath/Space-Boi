package com.binarygames.spaceboi.gameobjects.pickups.WeaponAttachments;

import com.binarygames.spaceboi.Assets;

public enum WeaponAttachmentAsset {
    MechDamage(Assets.ARMOR_PIERCING), BioDamage(Assets.BIO_DAMAGE_ICON), Experience(Assets.XP_BOOST_ICON),
    Slow(Assets.SLOW_ICON), GlassCannon(Assets.GLASS_CANNON_ICON);

    private final String asset;

    WeaponAttachmentAsset(String asset) {
        this.asset = asset;
    }

    public String getAsset() {
        return asset;
    }
}
