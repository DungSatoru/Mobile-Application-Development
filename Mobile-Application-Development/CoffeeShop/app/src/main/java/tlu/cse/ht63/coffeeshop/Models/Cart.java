package tlu.cse.ht63.coffeeshop.Models;

public class Cart {
    private int idCart;
    private int userId;
    private int productId;
    private int quantity;
    private double productPrice; // Thêm trường này để lưu giá sản phẩm cho việc tính tổng

    // Constructor mặc định
    public Cart() {
    }

    // Constructor có tham số
    public Cart(int idCart, int userId, int productId, int quantity, double productPrice) {
        this.idCart = idCart;
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
        this.productPrice = productPrice;
    }

    public Cart(int idCart, int userId, int productId, int quantity) {
        this.idCart = idCart;
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
    }

    // Getter và Setter
    public int getIdCart() {
        return idCart;
    }

    public void setIdCart(int idCart) {
        this.idCart = idCart;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public double getTotalPrice() {
        return this.productPrice * this.quantity;
    }
}
