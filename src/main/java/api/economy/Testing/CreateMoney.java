package api.economy.Testing;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import api.economy.Functions.balanceControl;

public class CreateMoney implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player p = (Player) sender;
        p.sendMessage("Your balance: " + balanceControl.getInstance().getBalance(p, "Basic").get(1).toString());

        return true;
    }
}
