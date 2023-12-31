package com.example.Marketplace.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Transient;

/**
 * This class represents a specific product of an Order and it's quantity
 */
@Entity
public class OrderItem {

    @EmbeddedId
    @JsonIgnore
    OrderItemPK orderItemPK;
    int quantity;

    public OrderItem(){}

    public OrderItem(Order order, Product product, int quantity){
        orderItemPK = new OrderItemPK();
        orderItemPK.setOrder(order);
        orderItemPK.setProduct(product);
        this.quantity = quantity;
    }


    public double getTotal() {
        return (this.orderItemPK.getProduct().getPrice() * this.quantity);
    }

    public Product getProduct() {
        return this.orderItemPK.getProduct();
    }

    public Order getOrder() {
        return this.orderItemPK.getOrder();
    }



}
