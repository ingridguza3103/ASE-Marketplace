package com.example.Marketplace.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.Valid;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import java.util.List;

/**
 * This class represents an Order with the list of all items that the user bought and the total price
 */
@Entity
@Table(name="orders")
public class Order {
    @Id
    private Long id;
    @JsonManagedReference
    @OneToMany(mappedBy = "orderItemPK.order")
    @Valid
    private List<OrderItem> products = new ArrayList<>();

    //TODO: maybe somehow get buyer user
    private LocalDateTime orderPlaced;


    public Order(){}


    /**
     * calculate the total price of the order
     * @return the total price
     */
    public double calculateOrderTotal() {
        double sum = 0.0;

        for (OrderItem product : products) {
            sum += product.getTotal();
        }

        return sum;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public List<OrderItem> getProducts() {
        return products;
    }

    public void setProducts(List<OrderItem> products) {
        this.products = products;
    }

    public void addProducts(OrderItem orderItem) {
        this.products.add(orderItem);
    }

    public LocalDateTime getOrderPlaced() {
        return orderPlaced;
    }

    public void setOrderPlaced(LocalDateTime orderPlaced) {
        this.orderPlaced = orderPlaced;
    }
}
