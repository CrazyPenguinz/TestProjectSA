package model;

public class OrderDetail {
    private int clothTypeID;
    private String type;
    private int quantity;

    public OrderDetail(int clothTypeID, String type, int quantity) {
        this.clothTypeID = clothTypeID;
        this.type = type;
        this.quantity = quantity;
    }

    public int getClothTypeID() {
        return clothTypeID;
    }

    public String getType() {
        return type;
    }

    public int getQuantity() {
        return quantity;
    }
}
