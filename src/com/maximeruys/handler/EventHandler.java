package com.maximeruys.handler;

import com.maximeruys.WandPlugin;
import com.maximeruys.spells.Spell;
import com.maximeruys.user.SpellUser;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
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
        if (entity instanceof Arrow) {
            Arrow arrow = (Arrow) entity;
            if(arrow.getShooter() instanceof Player){
                Player shooter = (Player) arrow.getShooter();
                String spellName = ChatColor.stripColor(arrow.getCustomName());
                Spell spell = WandPlugin.getPlugin().getSpellManager().getSpellByName(spellName);
                if(spell != null){
                    Entity hitEntity = event.getHitEntity();
                    if (hitEntity != null) {
                        spell.onHit(shooter, hitEntity);
                    }
                }
            }
        }
    }

    @org.bukkit.event.EventHandler
    public void onSpellDamageNotPlayer(EntityDamageByEntityEvent event){
        if(event.getDamager() instanceof Arrow){
            Arrow arrow = (Arrow) event.getDamager();
            if(arrow.getShooter() instanceof Player){
                String spellName = ChatColor.stripColor(arrow.getCustomName());
                Spell spell = WandPlugin.getPlugin().getSpellManager().getSpellByName(spellName);

                if (spell != null) {

                    if (spell.getDamage() <= 0) {
                        event.setCancelled(true);
                        return;
                    }

                    event.setDamage(spell.getDamage());
                }
            }
        }
    }

}
