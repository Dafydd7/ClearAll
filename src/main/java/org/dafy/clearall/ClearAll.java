package org.dafy.clearall;

import org.bukkit.plugin.java.JavaPlugin;
import org.dafy.clearall.builder.BuildManager;
import org.dafy.clearall.command.ClearCommand;

public final class ClearAll extends JavaPlugin {
    private BuildManager buildManager;
    @Override
    public void onEnable() {
        //Save the default config
        saveDefaultConfig();
        //Register build manager, and initialise the items from config
        buildManager = new BuildManager(this);
        buildManager.initBuilder();
        //Register & build the command
        final String commandName = "ClearAll";
        getCommand(commandName).setExecutor(new ClearCommand(this));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
    public BuildManager getBuildManager(){
        return this.buildManager;
    }
}
