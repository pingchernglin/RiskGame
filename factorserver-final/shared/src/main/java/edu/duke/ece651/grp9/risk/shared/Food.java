package edu.duke.ece651.grp9.risk.shared;

public class Food implements Resource {
    private int quantity;

    public Food(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public int getQuantity() {
        return this.quantity;
    }

    @Override
    public void addResource(int addFoodQuantity){
        this.quantity = this.quantity + addFoodQuantity;
    }

    @Override
    public void setResource(int foodQuantity) {
        this.quantity = foodQuantity;
    }
}
