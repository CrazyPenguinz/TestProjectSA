package model;

public class ClothType {
    private int id;
    private String type;
    private int coupon;

    public ClothType(int id, String type, int coupon) {
        this.id = id;
        this.type = type;
        this.coupon = coupon;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public int getCoupon() {
        return coupon;
    }
}
