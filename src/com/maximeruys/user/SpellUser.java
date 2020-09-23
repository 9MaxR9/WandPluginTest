package com.maximeruys.user;

import com.maximeruys.WandPlugin;
import com.maximeruys.spells.AvadaKedavraSpell;
import com.maximeruys.spells.Spell;
import org.bukkit.*;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SpellUser {

    private final UUID uuid;

    private Spell selectedSpell = new AvadaKedavraSpell();
    private Map<String, Spell> availableSpells = new HashMap<String, Spell>();

    public SpellUser(UUID uuid){
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Spell getSelectedSpell() {
        return selectedSpell;
    }

    public void setSelectedSpell(Spell selectedSpell) {
        this.selectedSpell = selectedSpell;
    }

    public Map<String, Spell> getAvailableSpells() {
        return availableSpells;
    }

    public void executeSpell(){

        Player shooter = Bukkit.getPlayer(uuid);

        final Arrow arrow = (Arrow) shooter.getWorld().spawn(shooter.getLocation(), Arrow.class);
        arrow.setCustomNameVisible(false);
        arrow.setCustomName(ChatColor.RED + getSelectedSpell().getName());

        arrow.setShooter(shooter);
        arrow.setBounce(false);
        arrow.setSilent(true);
        arrow.setDamage(selectedSpell.getDamage());
        arrow.setVelocity(shooter.getLocation().getDirection().multiply(3));

        Location shooterLocation = shooter.getLocation();
        Vector shooterDirection = shooterLocation.getDirection().normalize();

        new BukkitRunnable(){
            public void run(){

                arrow.setVelocity(shooterDirection);
                Particle.DustOptions dust = new Particle.DustOptions(Color.fromRGB(235, 147, 255), 2);
                shooter.getWorld().spawnParticle(Particle.REDSTONE, arrow.getLocation().getX(), arrow.getLocation().getY() + 0.1, arrow.getLocation().getZ(), 0, (float) shooterDirection.getX(), (float) shooterDirection.getY(), (float) shooterDirection.getZ(), 2.5 , dust);

                if(arrow.isOnGround()){
                    this.cancel();
                    arrow.remove();
                }
            }
        }.runTaskTimer(WandPlugin.getPlugin(), 0, 1);

    }

}
