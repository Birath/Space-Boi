package com.binarygames.spaceboi.gameobjects.pickups.WeaponAttachments;

import com.binarygames.spaceboi.Assets;
import com.binarygames.spaceboi.gameobjects.GameWorld;
import com.binarygames.spaceboi.gameobjects.entities.Player;
import com.binarygames.spaceboi.gameobjects.entities.weapons.Weapon;

public class GlassCannon extends WeaponAttachment {

    private String name = "Glass Cannon";
    private String description = "Lower player max health and increase damage dealt dramatically";
    private String icon = Assets.GLASS_CANNON_ICON;

    private boolean isEquipped;

    private float maxHealthFactor = 0.4f;
    private float damageFactor = 1.5f;

    private int startMaxHealth;
    private int previousMaxHealth;

    public GlassCannon(GameWorld gameWorld, float x, float y, String path, float mass, float radius) {
        super(gameWorld, x, y, path, mass, radius);
    }

    @Override
    public void applyAttachment(Weapon weapon) {
        Player player = (Player) weapon.getShooter();
        if (player.getHealth() > player.getHealth() * maxHealthFactor) {
            player.setHealth(Math.round(player.getHealth() * maxHealthFactor));
        }
        previousMaxHealth = player.getMaxHealth();
        player.setMaxHealth(Math.round(player.getMaxHealth() * maxHealthFactor));
        startMaxHealth = player.getMaxHealth();

        weapon.setDamage(Math.round(weapon.getDamage() * damageFactor));
    }

    @Override
    public void removeAttachment(Weapon weapon) {
        Player player = (Player) weapon.getShooter();
        player.setMaxHealth(previousMaxHealth + (player.getMaxHealth() - startMaxHealth));

        weapon.setDamage(Math.round(weapon.getDamage() / damageFactor));
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
