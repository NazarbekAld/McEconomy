package api.economy.Functions;
import api.economy.SQL.MySqlAccess;

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
    public boolean createCurrency(String nameofbalance){
        try {
            Connection c = MySqlAccess.getInstance().getConnection();
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
            return true;
        } catch (SQLException e) {
            System.out.println("[EcoAPI] SQL query error at MutiBalance. Output: " + String.valueOf(e));
            return false;
        }
    } // Creating new currency.

}
