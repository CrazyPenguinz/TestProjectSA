package model;

import java.time.LocalDate;

public class Package {
    private int amount;
    private LocalDate expire;
    private int price;
    private String name;

    public Package(int amount, LocalDate expire, int price, String name) {
        this.amount = amount;
        this.expire = expire;
        this.price = price;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getAmount() {
        return amount;
    }

    public LocalDate getExpire() {
        return expire;
    }

    public double getPrice() {
        return price;
    }
}
