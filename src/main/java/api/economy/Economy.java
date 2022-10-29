package api.economy;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class Economy extends JavaPlugin {

    private static Plugin plugin;
    @Override
    public void onEnable() {
        // Plugin Setup.
        this.saveDefaultConfig();
    }
    private static FileConfiguration config = plugin.getConfig();
    public static FileConfiguration getConf(){
        return config;
    }
}
