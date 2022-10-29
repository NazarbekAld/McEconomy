package api.economy.Testing;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import api.economy.Functions.balanceControl;

public class Setup implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        balanceControl.getInstance().registerPlayer(p);
    }
}
