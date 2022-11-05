package api.economy.eco;

import org.bukkit.entity.Player;

import java.sql.SQLException;

public class Balance {

    private int amount;

    public Player getOwner() {
        return this.Owner;
    }

    public int getAmount() {
        return this.Amount;
    }

    public String getNameOfBalance() {
        return this.NameOfBalance;
    }

    public boolean isFrozen() {
        return this.isFrozen;
    }

    public void setAmount(int amount) {
        this.Amount = amount;
    }

    public void setNameOfBalance(String nameOfBalance) {
        this.NameOfBalance = nameOfBalance;
    }

    public void setFrozen(boolean frozen) {
        this.isFrozen = frozen;
    }

    private final Player Owner;
    private int Amount;
    private String NameOfBalance;
    private boolean isFrozen;

    public Balance(Player owner, int amount, String nameOfBalance, boolean isFrozen) {
        this.Owner = owner;
        this.Amount = amount;
        this.NameOfBalance = nameOfBalance;
        this.isFrozen = isFrozen;
    }

    public boolean Transfer(Balance balanceofpayee, int amount)
    {
        if (getAmount() < amount)
            return false;
        if (isFrozen)
            return false;
        setAmount(getAmount() - amount);
        balanceofpayee.setAmount(balanceofpayee.getAmount() - amount);
        return true;
    }

    public boolean saveToDatabase(){
        try {
            MultiBalance.getInstance().saveBalance(this);
        } catch (SQLException e) {
            return false;
        }
        return true;
    }
}
