package api.economy;

import org.bukkit.plugin.java.JavaPlugin;

public class Economy extends JavaPlugin {

    private static Economy plugin;

    @Override
    public void onEnable() {
        plugin = this;
        this.saveDefaultConfig();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    public static Economy getInstance(){
        return plugin;
    }
}
