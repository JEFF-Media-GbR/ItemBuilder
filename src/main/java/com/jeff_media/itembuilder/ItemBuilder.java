package com.jeff_media.itembuilder;

import org.bukkit.plugin.java.JavaPluginLoader;
import org.bukkit.plugin.PluginDescriptionFile;
import java.io.File;
import org.bukkit.plugin.java.JavaPlugin;

public class ItemBuilder extends JavaPlugin {

    /**
     * Required for MockBukkit
     */
    public MyPlugin() {}

    /**
    * Required for MockBukkit
    */
    protected MyPlugin(JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file) {
        super(loader, description, dataFolder, file);
    }

    @Override
    public void onEnable() {

    }

}
