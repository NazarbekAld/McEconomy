package api.economy;

import api.economy.Testing.Setup;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import api.economy.MySqlAccess;

import java.sql.SQLException;

public final class Economy extends JavaPlugin {

    private static Plugin plugin;
    @Override
    public void onEnable() {
        // Plugin Setup.

        getServer().getPluginManager().registerEvents(new Setup(), this);

        this.saveDefaultConfig();
        try{
            MySqlAccess.getInstance().installBase();
        } catch (SQLException e) {
            System.out.println("[EcoAPI] Error: " + String.valueOf(e));
        }

    }
    private static FileConfiguration config = plugin.getConfig();
    public static FileConfiguration getConf(){
        return config;
    }
}
