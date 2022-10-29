package api.economy;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import static api.economy.Economy.getConf;


public class SQLinfo {

    public static FileConfiguration getConf() {
        return conf;
    }
    private static FileConfiguration conf = getConf();

    private static String Host = conf.getString("SQLHost");
    private static String Port = conf.getString("SQLPort");
    private static String Name = conf.getString("SQLDatabaseName");
    private static String User = conf.getString("SQLUserName");
    private static String Password = conf.getString("SQLPassword");

    public static String getHost() {
        return Host;
    }

    public static String getPort() {
        return Port;
    }

    public static String getName() {
        return Name;
    }

    public static String getUser() {
        return User;
    }

    public static String getPassword() {
        return Password;
    }


}
