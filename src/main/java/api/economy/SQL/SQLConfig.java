package api.economy.SQL;

import api.economy.Economy;

import java.util.HashMap;

public class SQLConfig {

    private static SQLConfig conf = null;

    public static SQLConfig getInstance(){
        if (conf == null)
            conf = new SQLConfig();
        return conf;
    }

    private Economy plugin = Economy.getInstance();

    public HashMap<String, String> getConfig() {
        HashMap<String, String> config = new HashMap<>();
        config.put("Host", plugin.getConfig().getString("SQL.HOST"));
        config.put("Port", plugin.getConfig().getString("SQL.PORT"));
        config.put("Database", plugin.getConfig().getString("SQL.DATABASE"));
        config.put("User", plugin.getConfig().getString("SQL.USER"));
        config.put("Password", plugin.getConfig().getString("SQL.PASSWORD"));
        config.put("SSL", plugin.getConfig().getString("SQL.SSL"));
        return config;
    }
}
