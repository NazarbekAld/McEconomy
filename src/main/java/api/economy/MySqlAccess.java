package api.economy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static api.economy.SQLinfo.*;

// SINGLETON CLASS!
public class MySqlAccess {
    private static MySqlAccess SQL = null;

    public static MySqlAccess getInstance(){
        if (SQL == null)
            SQL = new MySqlAccess();
        return SQL;
    } // Getting instance

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://" + getHost() + ":" + getPort() + "/" + getName(), getUser(), getPassword());
    }


}
