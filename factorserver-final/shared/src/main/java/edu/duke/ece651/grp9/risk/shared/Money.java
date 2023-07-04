package edu.duke.ece651.grp9.risk.shared;

public class Money implements Resource{
    private int quantity;

    public Money(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public int getQuantity() {
        return this.quantity;
    }

    @Override
    public void addResource(int addMoneyQuantity){
        this.quantity = this.quantity + addMoneyQuantity;
    }

    @Override
    public void setResource(int moneyQuantity) {
        this.quantity = moneyQuantity;
    }

}
