package api.economy.eco;

import org.bukkit.entity.Player;

public class Balance {

    private final Player Owner;
    private int Amount;
    private String NameOfBalance;
    private boolean isFrozen;

    public Balance(Player owner, int amount, String nameOfBalance, boolean isFrozen) {
        Owner = owner;
        setAmount(amount);
        setNameOfBalance(nameOfBalance);
        setFrozen(isFrozen);
    }

    public Player getOwner() {
        return Owner;
    }

    public int getAmount() {
        return Amount;
    }

    public String getNameOfBalance() {
        return NameOfBalance;
    }

    public boolean isFrozen() {
        return isFrozen;
    }

    public void setAmount(int amount) {
        Amount = amount;
    }

    public void setNameOfBalance(String nameOfBalance) {
        NameOfBalance = nameOfBalance;
    }

    public void setFrozen(boolean frozen) {
        isFrozen = frozen;
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

    public void saveToDatabase(){

    }
}
