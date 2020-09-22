package com.maximeruys.spels;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public interface Spell {

    String name();
    String description();
    double damage();
    int distance();
    int cost();
    int id();
    void onHit(Player shooter, Entity entity);

}
