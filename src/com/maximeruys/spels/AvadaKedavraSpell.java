package com.maximeruys.spels;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class AvadaKedavraSpell implements Spell {

    private final String name;
    private final int id;
    private final int distance;
    private final String description;
    private final int cost;
    private final double damage;

    public AvadaKedavraSpell(){
        this.name = "AvadaKedavra";
        this.id = 0;
        this.description = "This spell instantly kills the opponent when hit.";
        this.distance = 20;
        this.cost = 10;
        this.damage = 20;
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public String description() {
        return this.description;
    }

    @Override
    public double damage() {
        return this.damage;
    }

    @Override
    public int distance() {
        return this.distance;
    }

    @Override
    public int cost() {
        return this.cost;
    }

    @Override
    public int id() {
        return this.id;
    }

    @Override
    public void onHit(Player shooter, Entity entity) {
        shooter.setExp(shooter.getExp() - cost());
        if (entity instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity) entity;
            livingEntity.setHealth(livingEntity.getHealth() - damage());
        }
    }

}
