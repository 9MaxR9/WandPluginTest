package com.maximeruys.handler;

import com.maximeruys.main.WandPlugin;
import com.maximeruys.spels.Spell;
import com.maximeruys.user.SpellUser;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EventHandler implements Listener {

    public static Map<UUID, SpellUser> SPELL_USERS = new HashMap<UUID, SpellUser>();

    @org.bukkit.event.EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        SPELL_USERS.put(player.getUniqueId(), new SpellUser(player.getUniqueId()));
    }

    @org.bukkit.event.EventHandler
    public void onLeave(PlayerQuitEvent event){

        Player player = event.getPlayer();
        SPELL_USERS.remove(player.getUniqueId());

    }

    @org.bukkit.event.EventHandler
    public void onInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if ((event.getAction() == Action.LEFT_CLICK_BLOCK || event.getAction() == Action.LEFT_CLICK_AIR) && event.getMaterial() == Material.STICK) {
            SPELL_USERS.get(player.getUniqueId()).executeSpell();
        }
    }


    @org.bukkit.event.EventHandler
    public void onSpellHitPlayer(ProjectileHitEvent event){
        Entity entity = event.getEntity();
        if (entity instanceof Snowball) {
            Snowball snowball = (Snowball) entity;
            if(snowball.getShooter() instanceof Player){
                Player shooter = (Player) snowball.getShooter();
                String spellName = ChatColor.stripColor(snowball.getCustomName());
                Spell spell = WandPlugin.getPlugin().getSpellManager().getSpellByName(spellName);
                if(spell != null){
                    Entity hitEntity = event.getHitEntity();
                    Block hitBlock = event.getHitBlock();

                    if (hitEntity != null) {
                        spell.onHit(shooter, hitEntity);
                    }

                    if (hitBlock != null) {
                        spell.onHit(shooter, null);
                    }

                }
            }
        }
    }

    @org.bukkit.event.EventHandler
    public void onSpellDamageNotPlayer(EntityDamageByEntityEvent event){
        if(event.getDamager() instanceof Snowball){
            Snowball snowball = (Snowball) event.getDamager();
            if(snowball.getShooter() instanceof Player){
                String spellName = ChatColor.stripColor(snowball.getCustomName());
                Spell spell = WandPlugin.getPlugin().getSpellManager().getSpellByName(spellName);

                if (spell != null) {

                    if (spell.damage() <= 0) {
                        event.setCancelled(true);
                        return;
                    }

                    event.setDamage(spell.damage());
                }
            }
        }
    }

}
