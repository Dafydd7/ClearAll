package org.dafy.clearall.command;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.dafy.clearall.ClearAll;
import org.dafy.clearall.builder.BuildManager;

public class ClearCommand implements CommandExecutor {
    private final BuildManager buildManager;
    private final String commandPermission;
    public ClearCommand(ClearAll plugin){
        commandPermission = plugin.getConfig().getString("command-permission","admin.clean");
        buildManager = plugin.getBuildManager();
    }
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        //Check to see whether the sender was a player
        if(!(commandSender instanceof Player)) return true;
        //Player was a sender, let's get the Player obj
        Player player = (Player) commandSender;
        //Check their permission
        if(!player.hasPermission(commandPermission)) return true;
        //Validate that the player specified a target
        if(args.length != 1){
            player.sendMessage(command.getUsage());
            return true;
        }
        //Let's find the player they are trying to wipe
        Player target = Bukkit.getPlayer(args[0]);
        if(target == null){
            player.sendMessage("Error: Player was not found.");
            return true;
        }
        if(!isInSameWorld(player.getWorld(),target.getWorld())){
            player.sendMessage("You must be in the same world to wipe this player!");
            return true;
        }
        //Everything is fine, let's clear  the player
        clearPlayer(target);
        buildManager.givePlayerItems(target);
        return false;
    }
    private boolean isInSameWorld(World playerWorld, World targetWorld){
        if(playerWorld == null || targetWorld == null) return false;
        return playerWorld == targetWorld;
    }
    private void clearPlayer(Player target) {
        PlayerInventory inventory = target.getInventory();
        inventory.clear();
        inventory.setArmorContents(null);
        target.setFireTicks(0);
        for (PotionEffect effect : target.getActivePotionEffects()) {
            target.removePotionEffect(effect.getType());
        }
        target.updateInventory();
    }
}
