package model;

public class ClothType {
    private String type;
    private int coupon;

    public ClothType(String type, int coupon) {
        this.type = type;
        this.coupon = coupon;
    }

    public String getType() {
        return type;
    }

    public int getCoupon() {
        return coupon;
    }
}
