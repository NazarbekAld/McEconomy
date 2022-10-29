package api.economy.Functions;


import api.economy.MySqlAccess;

import java.sql.*;
import java.util.List;
import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.regex.Pattern;

// Single ton class
public class balanceControl {

    private static balanceControl balancecontrol = null;
    public static balanceControl getInstance(){
        if (balancecontrol == null)
            balancecontrol = new balanceControl();
        return balancecontrol;
    }

    public void registerPlayer(Player player){
        try {
            Connection c = MySqlAccess.getInstance().getConnection();
            Statement s = c.createStatement();

            ResultSet result = s.executeQuery("SELECT `BalanceName` FROM `Balances`;");
            while (result.next()){
                ResultSet exist = s.executeQuery("SELECT `UUID` FROM `" + result.getString("BalanceName") + "`" + " WHERE '" + player.getUniqueId().toString() + "';");
                if (exist.next())
                    continue;
                PreparedStatement pre = c.prepareStatement("INSERT INTO `" + result.getString("BalanceName") + "`(`UUID`, `Balance`, `isFrozen`) VALUES (?, ?, ?);");
                pre.setString(1, player.getUniqueId().toString());
                pre.executeQuery();
            }
        }
        catch (SQLException e){
        }
    }

    public List<Object> getBalance(Player player, String nameofbalance){
        try {
            Connection connection = MySqlAccess.getInstance().getConnection(); // Getting connection.
            Pattern pattern = Pattern.compile("[^A-Za-z0-9]");
            if (!pattern.matcher(nameofbalance).find())
                return Arrays.asList(false, "Cannot use symbols!");
            // Checks if string not had any symbols. (We needed for avoid sql injections.)

            PreparedStatement pre = connection.prepareStatement("SELECT * FROM " + nameofbalance + " WHERE ?;");
            pre.setString(1, player.getUniqueId().toString());
            ResultSet result = pre.executeQuery();
            if (!result.next())
                return Arrays.asList(false, "Don`t exist.");
            // Checks if ResultSet won`t null.

            return Arrays.asList(true, result.getInt("Balance"));
        } catch (SQLException e) {
            System.out.println("[EcoAPI] SQL query error: " + String.valueOf(e));
            return Arrays.asList(false, "Connection error.");
        }
    } // Getting balance of player.

    public List<Object> setBalance(Player player, String nameofbalance, int amount){
        try {
            Connection c = MySqlAccess.getInstance().getConnection(); // Getting connection
            Pattern pattern = Pattern.compile("[^A-Za-z0-9]");
            if(!pattern.matcher(nameofbalance).find()) // Checks if string not have symbols. (We needed for avoid sql injections.)
                return Arrays.asList(false, "Cannot use symbols!");
            PreparedStatement pre = c.prepareStatement("UPDATE `" + nameofbalance + "` SET `UUID`=?,`Balance`=? WHERE ?;");
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
    } // Set balance

    public List<Object> safeTransfer(Player player_payer, Player player_payee, String nameofbalance, int amount){
        List<Object> player_payer_result = getBalance(player_payer, nameofbalance);
        List<Object> player_payee_result = getBalance(player_payee, nameofbalance);
        if (!Boolean.parseBoolean(player_payer_result.get(0).toString()) || !Boolean.parseBoolean(player_payee_result.get(0).toString()))
            return Arrays.asList(false, player_payer_result.get(1).toString(), player_payee_result.get(1).toString());

        if (Integer.parseInt(player_payer_result.get(1).toString()) < amount)
            return Arrays.asList(false, "Not enough.");

        int payerset = Integer.parseInt(getBalance(player_payer, nameofbalance).get(1).toString()) - amount;
        int payeeset = Integer.parseInt(getBalance(player_payee, nameofbalance).get(1).toString()) + amount;
        setBalance(player_payer, nameofbalance, payerset);
        setBalance(player_payee, nameofbalance, payeeset);
        return Arrays.asList(true, "Success.");
    } // Transfer balance to another



}
