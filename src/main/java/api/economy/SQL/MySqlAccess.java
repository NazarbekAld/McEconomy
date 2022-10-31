package api.economy.SQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


// SINGLETON CLASS!
public class MySqlAccess {
    private static MySqlAccess SQL = null;
    public static MySqlAccess getInstance(){
        if (SQL == null)
            SQL = new MySqlAccess();
        return SQL;
    } // Getting instance

    public Connection getConnection() throws SQLException {
        SQLConfig sqlConfig = SQLConfig.getInstance();
        return DriverManager.getConnection("jdbc:mysql://" + sqlConfig.getValue().get("Host") + ":" + sqlConfig.getValue().get("HostPort") + "/" + sqlConfig.getValue().get("Database") + "?useSSL=" + sqlConfig.getValue().get("SSL"), sqlConfig.getValue().get("User"), sqlConfig.getValue().get("Password"));
    } // Getting connection from database
    public void installBase() throws SQLException {
        try {
            Statement s = getConnection().createStatement();
            s.executeUpdate("CREATE TABLE if not exists `Basic` ( `UUID` TEXT NOT NULL COMMENT 'Player`s UUID' , `Balance` INT NOT NULL DEFAULT '0' COMMENT 'Player`s Balance' , `isFrozen` BOOLEAN NOT NULL DEFAULT FALSE COMMENT 'Must have index here. You can froze balance of dupers.' ) ENGINE = InnoDB;");
            s.executeUpdate("CREATE TABLE if not exists `Balances` ( `BalanceName` TEXT NOT NULL COMMENT 'Balance List' ) ENGINE = InnoDB;");
        }catch (SQLException e){
            System.out.printf("[EcoAPI] Database error at MySqlAccess. Output " + String.valueOf(e));
        }
    } // Function creates default "Basic" table if not exist "Basic".


}
