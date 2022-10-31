package api.economy.SQL;

import api.economy.Economy;

import java.util.HashMap;

public class SQLConfig {

    private static SQLConfig config = null;

    public static SQLConfig getInstance(){
        if(config == null)
            config = new SQLConfig();
        return config;
    }
    private final Economy plugin = Economy.getIns();


    public HashMap<String, String> getValue(){
        HashMap<String, String> map = new HashMap<>();
        map.put("Host", plugin.getConfig().getString("SQLHost"));
        map.put("HostPort", plugin.getConfig().getString("SQLPort"));
        map.put("Database", plugin.getConfig().getString("SQLDatabaseName"));
        map.put("User",plugin.getConfig().getString("SQLUserName"));
        map.put("Password", plugin.getConfig().getString("SQLPassword"));
        map.put("SSL", plugin.getConfig().getString("SQLSSL"));
        return map;
    }
}
