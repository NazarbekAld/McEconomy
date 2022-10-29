package api.economy.Functions;
import api.economy.MySqlAccess;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/*
    Multiple balances

    Why we need it:
    With that we can create gems, coins, bank accounts, deposit balance, levels, health, e.t.c.

    Its must have when you developing mmorpg games.

 */

public class MultiBalance {

    public boolean createCurrency(String nameofbalance){
        try {
            Connection c = MySqlAccess.getInstance().getConnection();
            Statement pre = c.prepareStatement("CREATE TABLE if not exists `" + nameofbalance + "` ( `UUID` TEXT NOT NULL COMMENT 'Player`s UUID' , `Balance` INT NOT NULL DEFAULT '0' COMMENT 'Player`s Balance' , `isFrozen` BOOLEAN NOT NULL DEFAULT FALSE COMMENT 'Must have index here. You can froze balance of dupers.' ) ENGINE = InnoDB");
            return true;
        } catch (SQLException e) {
            System.out.println("[EcoAPI] SQL query error: " + String.valueOf(e));
            return false;
        }
    } // Creating new currency.

}
