package com.example.Marketplace.model;

import jakarta.persistence.*;

@Entity
@Table(name="products")
public class Product {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productName;
    private double price;
    private int quantity;
    private int categoryId;
    private int sellerId;
    private String pictureUrl;

    public Product(){}

    public Product(Long id, String productName, double price, int quantity, int categoryId, int sellerId, String pictureUrl) {
        this.id = id;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.categoryId = categoryId;
        this.sellerId = sellerId;
    }



    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * This method is used to check if a product is in stock
     * @return true if quantity > 0, false otherwise
     */
    public boolean isInStock() {
        return quantity > 0;
    }


    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

}
