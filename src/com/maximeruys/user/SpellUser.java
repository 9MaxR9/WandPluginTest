package com.maximeruys.user;

import com.maximeruys.main.WandPlugin;
import com.maximeruys.spels.AvadaKedavraSpell;
import com.maximeruys.spels.Spell;
import com.maximeruys.spels.SpellManager;
import com.maximeruys.util.ReflectionUtil;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
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

        Player caster = Bukkit.getPlayer(uuid);

        if (caster == null || !caster.isOnline()) return;

        Location location = caster.getLocation();
        Vector direction = location.getDirection().normalize();

         final Snowball snowball = caster.getWorld().spawn(caster.getEyeLocation().subtract(0, 0.1, 0), Snowball.class);

        try {

            Class<?> packetPlayOutEntityDestroy = ReflectionUtil.PackageType.MINECRAFT_SERVER.getClass("PacketPlayOutEntityDestroy");
            Constructor<?> packetPlayOutEntityDestroyConstructor = packetPlayOutEntityDestroy.getConstructor(int[].class);

            Object packetPlayOutEntityDestroyObj = packetPlayOutEntityDestroyConstructor.newInstance((Object) new int[] { snowball.getEntityId() });

            Bukkit.getOnlinePlayers().forEach(player -> ReflectionUtil.sendPacket(player, packetPlayOutEntityDestroyObj));
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }

        snowball.setCustomNameVisible(false);
        snowball.setCustomName(ChatColor.RED + getSelectedSpell().name());

        snowball.setShooter(caster);
        snowball.setBounce(false);
        snowball.setSilent(true);

        new BukkitRunnable() {
            public void run() {
                snowball.setVelocity(direction);
                Particle.DustOptions dust = new Particle.DustOptions(Color.fromRGB(235, 147, 255), 2);
                caster.getWorld().spawnParticle(Particle.REDSTONE, snowball.getLocation().getX(), snowball.getLocation().getY() + 0.1, snowball.getLocation().getZ(), 0, (float) direction.getX(), (float) direction.getY(), (float) direction.getZ(), 2.5 , dust);

                double distance = snowball.getLocation().distance(caster.getLocation());

                if ((snowball.isOnGround() || snowball.isDead()) || distance > getSelectedSpell().distance()) {
                    this.cancel();
                    snowball.remove();
                }

            }
        }.runTaskTimer(WandPlugin.getPlugin(), 0, 1);
    }

}
