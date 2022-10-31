package api.economy;

import api.economy.SQL.MySqlAccess;
import api.economy.Testing.Setup;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public final class Economy extends JavaPlugin {

    private static Economy plugin;
    @Override
    public void onEnable() {
        // Plugin Setup.
        plugin = this;
        getServer().getPluginManager().registerEvents(new Setup(), this);

        this.saveDefaultConfig();
        try{
            MySqlAccess.getInstance().installBase();
        } catch (SQLException e) {
            System.out.println("[EcoAPI] Error: " + String.valueOf(e));
        }
    }

    public static Economy getIns(){ return plugin; }
}
