package org.dafy.clearall.builder;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.dafy.clearall.ClearAll;

import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BuildManager {
    private final FileConfiguration config;
    private final Logger logger;
    public BuildManager(ClearAll plugin){
        config = plugin.getConfig();
        logger = plugin.getLogger();
    }
    private final HashSet<ItemStack> itemsToGive = new HashSet<>();

    public void initBuilder() {
        //Get the config section for the items.
        ConfigurationSection itemSection = config.getConfigurationSection("items.");
        //Clear the maps, in case someone has reloaded.
        itemsToGive.clear();
        //Return early if null.
        if (itemSection == null) {
            logger.log(Level.WARNING, "Unable to initialize items - Check your config.yml");
            return;
        }
        //Initialise all the possible items, and put them inside a set.
        for (String key:itemSection.getKeys(false)) {
            System.out.println(key);
            ItemStack item = new ItemBuilder(itemSection.getConfigurationSection(key)).buildItem();
            if(item == null) continue;
            itemsToGive.add(item);
        }
    }
    public void givePlayerItems(Player target){
        itemsToGive.forEach(itemStack -> target.getInventory().addItem(itemStack));
    }
}
