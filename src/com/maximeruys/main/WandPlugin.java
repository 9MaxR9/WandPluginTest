package com.maximeruys.main;

import com.maximeruys.commands.GiveWandCommand;
import com.maximeruys.handler.EventHandler;
import com.maximeruys.spells.SpellManager;
import org.bukkit.plugin.java.JavaPlugin;

public class WandPlugin extends JavaPlugin {

    public static WandPlugin plugin;
    private EventHandler eventHandler;
    private SpellManager spellManager = new SpellManager();

    @Override
    public void onEnable() {
        super.onEnable();

        plugin = this;

        GiveWandCommand giveWandCommand = new GiveWandCommand();
        this.eventHandler = new EventHandler();
        this.getServer().getPluginManager().registerEvents(eventHandler, this);
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    public SpellManager getSpellManager() {
        return spellManager;
    }

    public static WandPlugin getPlugin() {
        return plugin;
    }

}
