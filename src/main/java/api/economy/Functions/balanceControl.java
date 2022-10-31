package api.economy.Functions;


import api.economy.SQL.MySqlAccess;

import java.sql.*;

import org.bukkit.entity.Player;

import api.economy.Functions.Object.Result;

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
            System.out.println("[EcoAPI] SQL query error: " + String.valueOf(e));
        }
    } // Register player on every balance.

    public Result getBalance(Player player, String nameofbalance){
        try {
            Connection connection = MySqlAccess.getInstance().getConnection(); // Getting connection.
            Pattern pattern = Pattern.compile("[^A-Za-z0-9]");
            if (!pattern.matcher(nameofbalance).find())
                return new Result(false, "Cannot use symbols!");
            // Checks if string not had any symbols. (We needed for avoid sql injections.)

            PreparedStatement pre = connection.prepareStatement("SELECT * FROM " + nameofbalance + " WHERE ?;");
            pre.setString(1, player.getUniqueId().toString());
            ResultSet result = pre.executeQuery();
            if (!result.next())
                return new Result(false, "Unknown result.");
            // Checks if ResultSet won`t null.

            return new Result(true, result.getInt("Balance"));
        } catch (SQLException e) {
            System.out.println("[EcoAPI] SQL query error: " + String.valueOf(e));
            return new Result(false, "Connection error");
        }
    } // Getting balance of player.

    public Result setBalance(Player player, String nameofbalance, int amount){
        try {
            Connection c = MySqlAccess.getInstance().getConnection(); // Getting connection
            Pattern pattern = Pattern.compile("[^A-Za-z0-9]");
            if(!pattern.matcher(nameofbalance).find()) // Checks if string not have symbols. (We needed for avoid sql injections.)
                return new Result(false, "Cannot use symbols!");
            PreparedStatement pre = c.prepareStatement("UPDATE `" + nameofbalance + "` SET `UUID`=?,`Balance`=? WHERE ?;");
            pre.setString(1, player.getUniqueId().toString());
            pre.setInt(2, amount);
            ResultSet result = pre.executeQuery();
            if (!result.next())
                return new Result(false, "Unknown result.");
            if (result.getBoolean("isFrozen"))
                return new Result(false, "Balance is frozen.");

            return new Result(true, "Success!");
        } catch (SQLException e) {
            System.out.println("[EcoAPI] SQL query error: " + String.valueOf(e));
            return new Result(false, "Connection error!");
        }
    } // Set balance

    public Result safeTransfer(Player player_payer, Player player_payee, String nameofbalance, int amount){
        Result player_payer_result = getBalance(player_payer, nameofbalance);
        Result player_payee_result = getBalance(player_payee, nameofbalance);
        if (!player_payer_result.getStatus() || !player_payee_result.getStatus())
            return new Result(false, player_payer_result.getValue() + "\n" + player_payee_result.getValue());

        if ((Integer) player_payer_result.getValue() < amount)
            return new Result(false, "Not enough balance.");

        int payerset = (Integer) player_payer_result.getValue() - amount;
        int payeeset = (Integer) player_payee_result.getValue() + amount;
        setBalance(player_payer, nameofbalance, payerset);
        setBalance(player_payee, nameofbalance, payeeset);
        return new Result(true, "Success.");
    } // Transfer balance to another



}
