package org.dafy.clearall.builder;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.ListIterator;

public class ItemBuilder {
    private final Material genMaterial;
    private String genName;
    private List<String> genLore;

    public ItemBuilder(ConfigurationSection section) {
        this.genMaterial = getMaterial(section.getString("material", "stone"));
        if(section.contains(ConfigKeys.DISPLAY_NAME))
            setName(section.getString(ConfigKeys.DISPLAY_NAME));
        if(section.contains(ConfigKeys.DISPLAY_LORE))
            setLore(section.getStringList(ConfigKeys.DISPLAY_LORE));
    }

    public ItemStack buildItem() {
        ItemStack item = new ItemStack(genMaterial);
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return item;
        if (genName != null)
            meta.setDisplayName(genName);
        if (genLore != null)
            meta.setLore(genLore);
        item.setItemMeta(meta);
        return item;
    }

    public Material getMaterial(String materialName) {
        try {
            return Material.valueOf(materialName.toUpperCase());
        } catch (IllegalArgumentException e) {
            return Material.STONE;
        }
    }

    public void setName(String name) {
        if (name != null) {
            this.genName = ChatColor.translateAlternateColorCodes('&', name);
        }
    }

    public void setLore(List<String> lore) {
        if (lore != null) {
            ListIterator<String> iterator = lore.listIterator();
            while (iterator.hasNext()) {
                iterator.set(ChatColor.translateAlternateColorCodes('&', iterator.next()));
            }
            this.genLore = lore;
        }
    }
}
