package api.economy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

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
    } // Getting connection from database
    public void installBase() throws SQLException {
        Statement s = getConnection().createStatement();
        s.executeQuery("CREATE TABLE if not exists `Basic` ( `UUID` TEXT NOT NULL COMMENT 'Player`s UUID' , `Balance` INT NOT NULL DEFAULT '0' COMMENT 'Player`s Balance' , `isFrozen` BOOLEAN NOT NULL DEFAULT FALSE COMMENT 'Must have index here. You can froze balance of dupers.' ) ENGINE = InnoDB;");
    } // Function creates default "Basic" table if not exist "Basic".


}
