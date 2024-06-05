package tlu.cse.ht63.coffeeshop.Models;

import java.util.Date;

public class Order {
    private int id;
    private Date date;
    private int userId;
    private double totalAmount;

    public Order() {
    }
    public Order(Date date, int orderId, double totalAmount) {
        this.date = date;
        this.userId = orderId;
        this.totalAmount = totalAmount;
    }


    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int orderId) {
        this.userId = orderId;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
}