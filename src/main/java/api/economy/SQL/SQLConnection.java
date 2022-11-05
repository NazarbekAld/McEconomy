package api.economy.SQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;

public class SQLConnection {

    private static SQLConnection sql = null;

    public static SQLConnection getIns(){
        if(sql == null)
            sql = new SQLConnection();
        return sql;
    }

    public Connection connection() throws SQLException {
        HashMap<String, String> config = SQLConfig.getInstance().getConfig();
        return DriverManager.getConnection("jdbc:mysql://" +
                        config.get("Host") + ":" + config.get("Port") + "/" + config.get("Database") + "?useSSL=" + config.get("SSL"),
                config.get("User"), config.get("Password"));
    }


}
