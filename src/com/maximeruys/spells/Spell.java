package com.maximeruys.spells;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class Spell {

    private String name;
    private String description;
    private double damage;
    private int distance;
    private int cost;
    private int id;

    public Spell(String name, String description, double damage, int distance, int cost, int id){
        this.name = name;
        this.description = description;
        this.damage = damage;
        this.distance = distance;
        this.cost = cost;
        this.id = id;
    }

    public void onHit(Player shooter, Entity entity){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
