package com.binarygames.spaceboi.gameobjects.pickups.WeaponAttachments;

import com.binarygames.spaceboi.Assets;
import com.binarygames.spaceboi.gameobjects.GameWorld;
import com.binarygames.spaceboi.gameobjects.entities.Player;
import com.binarygames.spaceboi.gameobjects.entities.weapons.Weapon;
import com.binarygames.spaceboi.gameobjects.pickups.Pickup;

import java.util.Arrays;
import java.util.List;

public abstract class WeaponAttachment extends Pickup {

    public static final List<Class<? extends WeaponAttachment>> WEAPON_ATTACHMENTS = Arrays.asList(
        BioDamage.class,
        Experience.class,
        GlassCannon.class,
        LifeSteal.class,
        MechDamage.class,
        Recoil.class,
        Silencer.class,
        Slow.class
    );


    public WeaponAttachment(GameWorld gameWorld, float x, float y, String path, float mass, float radius) {
        super(gameWorld, x, y, path, mass, radius);
    }

    @Override
    public void onRemove() {
        
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void onHit(Player player) {
        player.addToInventory(this);
        gameWorld.getGame().getSoundManager().play(Assets.ATTACHMENT_PICK_1);
        //gameWorld.getGame().getSoundManager().play(Assets.ATTACHMENT_PICK_2);
        remove = true;
    }

    public abstract void applyAttachment(Weapon weapon);

    public abstract void removeAttachment(Weapon weapon);

    public abstract boolean isEquipped();

    public abstract void setEquipped(boolean equipped);

    public abstract String getIcon();

    public abstract String getName();

    public abstract String getDescription();

}
