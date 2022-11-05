package api.economy.eco;
import api.economy.SQL.SQLConnection;

import java.sql.*;
import java.util.regex.Pattern;

/*
    Multiple balances

    Why we need it:
    With that we can create gems, coins, bank accounts, deposit balance, levels, health, e.t.c.

    Its must have when you developing mmorpg games.

 */

// Singleton class
public class MultiBalance {
    private static MultiBalance multibal = null;
    public static MultiBalance getInstance(){
        if (multibal == null)
            multibal = new MultiBalance();
        return multibal;
    }
    public boolean createCurrency(String nameofbalance) throws SQLException{
            Connection c = SQLConnection.getIns().connection();
            Pattern pattern = Pattern.compile("[^A-Za-z0-9]");
            if (!pattern.matcher(nameofbalance).find())
                return false;
            PreparedStatement pre = c.prepareStatement("SELECT * FROM `Balances` WHERE ?");
            pre.setString(1, nameofbalance);
            ResultSet result = pre.executeQuery();
            if (!result.next())
                return false;
            pre = c.prepareStatement("INSERT INTO `Balances`(`BalanceName`) VALUES (?);");
            pre.setString(1, nameofbalance);
            pre.executeQuery();
            pre = c.prepareStatement("CREATE TABLE if not exists `%s` ( `UUID` TEXT NOT NULL COMMENT 'Player`s UUID' , `Balance` INT NOT NULL DEFAULT '0' COMMENT 'Player`s Balance' , `isFrozen` BOOLEAN NOT NULL DEFAULT FALSE COMMENT 'Must have index here. You can froze balance of dupers.' ) ENGINE = InnoDB;");
            pre.setString(1, nameofbalance);
            return true;
    } // Creating new currency.

    public boolean saveBalance(Balance balance) throws SQLException {
        Connection connection = SQLConnection.getIns().connection();
        PreparedStatement pre = connection.prepareStatement("SELECT * FROM `%s` WHERE `UUID`=?");
        pre.setString(1, balance.getNameOfBalance());
        pre.setString(2, balance.getOwner().getUniqueId().toString());
        ResultSet result = pre.executeQuery();
        if (!result.next()) {
            pre = connection.prepareStatement("INSERT INTO `%s`(`UUID`, `Balance`, `isFrozen`) VALUES (?,?,?)");
            pre.setString(1, balance.getNameOfBalance());
            pre.setString(2, balance.getOwner().getUniqueId().toString());
            pre.setInt(3, balance.getAmount());
            pre.setBoolean(4, balance.isFrozen());
            pre.executeUpdate();
            return true;
        }
        pre = connection.prepareStatement("UPDATE `%s` SET `UUID`=?,`Balance`=?,`isFrozen`=? WHERE `UUID`=?");
        pre.setString(1, balance.getNameOfBalance());
        pre.setString(2, balance.getOwner().getUniqueId().toString());
        pre.setInt(3, balance.getAmount());
        pre.setBoolean(4, balance.isFrozen());
        pre.setString(5, balance.getOwner().getUniqueId().toString());

        return true;
    }

}
