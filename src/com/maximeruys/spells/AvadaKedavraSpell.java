package com.maximeruys.spells;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class AvadaKedavraSpell extends Spell {

    public AvadaKedavraSpell() {
        super("AvadaKedavra", "This spell kills anyone immediately when hit.", 20, 80, 10, 0);
    }

    @Override
    public void onHit(Player shooter, Entity entity) {
        shooter.setExp(shooter.getExp() - getCost());
        if (entity instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity) entity;
            livingEntity.setHealth(livingEntity.getHealth() - getDamage());
        }
    }

}
