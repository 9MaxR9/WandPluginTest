package com.maximeruys.commands;

import com.maximeruys.main.WandPlugin;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GiveWandCommand implements CommandExecutor {

    public GiveWandCommand(){
        WandPlugin.getPlugin().getCommand("wand").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String cmd, String[] args) {
        if(commandSender instanceof Player){
            Player player = (Player) commandSender;
            player.getInventory().addItem(new ItemStack(Material.STICK, 1));
        }
        return false;
    }

}
