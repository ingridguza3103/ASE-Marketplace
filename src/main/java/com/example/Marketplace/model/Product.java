package com.example.Marketplace.model;

import jakarta.persistence.*;

/**
 * This class is used to represent a product in the web-shop with all the relevant information
 **/
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
    private Long sellerId;
    private String pictureUrl;

    public Product(){}

    public Product(String productName, double price, int quantity, int categoryId, Long sellerId, String pictureUrl) {
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.categoryId = categoryId;
        this.sellerId = sellerId;
        this.pictureUrl = pictureUrl;
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

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

}
