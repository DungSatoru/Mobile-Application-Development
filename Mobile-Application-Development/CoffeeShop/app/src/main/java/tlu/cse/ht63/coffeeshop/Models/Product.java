package tlu.cse.ht63.coffeeshop.Models;

import android.net.Uri;

public class Product {
    private int Id_product;
    private String Name;
    private double Price;
    private String Description;
    private Uri Image;

    public Product() {
    }

    public Product(int id_product, String name, double price, Uri image) {
        Id_product = id_product;
        Name = name;
        Price = price;
        Image = image;
    }

    public Product(String name, String description, double price, Uri image) {
        Name = name;
        Description = description;
        Price = price;
        Image = image;
    }
    public int getId_product() {
        return Id_product;
    }
    public void setId_product(int id_product) {
        Id_product = id_product;
    }
    public String getName() {
        return Name;
    }
    public void setName(String name) {
        Name = name;
    }
    public double getPrice() {
        return Price;
    }
    public void setPrice(double price) {
        Price = price;
    }
    public String getDescription() {
        return Description;
    }
    public void setDescription(String description) {
        Description = description;
    }
    public Uri getImage() {
        return Image;
    }
    public void setImage(Uri image) {
        Image = image;
    }
}
