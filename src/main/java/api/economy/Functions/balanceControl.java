package api.economy.Functions;


import api.economy.MySqlAccess;
import java.util.List;
import java.util.Arrays;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class balanceControl {

    public List<Object> getBalance(Player player, String nameofbalance){
        try {
            Connection connection = MySqlAccess.getInstance().getConnection(); // Getting connection.
            Pattern pattern = Pattern.compile("[^A-Za-z0-9]");
            if (!pattern.matcher(nameofbalance).find())
                return Arrays.asList(false, "Cannot use symbols!");
            // Checks if string not had any symbols. (We needed for avoid sql injections.)

            PreparedStatement pre = connection.prepareStatement("SELECT * FROM " + nameofbalance + " WHERE ?");
            pre.setString(1, player.getUniqueId().toString());
            ResultSet result = pre.executeQuery();
            if (!result.next())
                return Arrays.asList(false, "Don`t exist.");
            // Checks if ResultSet won`t null.

            return Arrays.asList(true, result.getInt("Bal"));
        } catch (SQLException e) {
            System.out.println("[EcoAPI] SQL query error: " + String.valueOf(e));
            return Arrays.asList(false, "Connection error.");
        }
    } // Getting balance of player.

    public List<Object> setBalance(Player player, String nameofbalance, int amount){
        try {
            Connection c = MySqlAccess.getInstance().getConnection();
            Pattern pattern = Pattern.compile("[^A-Za-z0-9]");
            if(!pattern.matcher(nameofbalance).find())
                return Arrays.asList(false, "Cannot use symbols!");
            PreparedStatement pre = c.prepareStatement("UPDATE `" + nameofbalance + "` SET `UUID`=?,`Balance`=? WHERE ?");
            pre.setString(1, player.getUniqueId().toString());
            pre.setInt(2, amount);
            ResultSet result = pre.executeQuery();
            if (!result.next())
                return Arrays.asList(false, "Don`t exist.");
            if (result.getBoolean("isFrozen"))
                return Arrays.asList(false, "Account is frozen.");

            return Arrays.asList(true);
        } catch (SQLException e) {
            System.out.println("[EcoAPI] SQL query error: " + e.toString());
            return Arrays.asList(false, "Connection error.");
        }
    }



}
