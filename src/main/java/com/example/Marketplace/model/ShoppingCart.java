package com.example.Marketplace.model;


import java.util.HashMap;
import java.util.Map;

/**
 * ShoppingCart class represents a users shopping cart as a Map of Product and Integer
 * so the Map stores each Product with the selected quantity.
 * This class provides all methods needed for the shopping cart.
 *
 * If the user clicks on checkout the current ShoppingCart is converted to an Order object
 */
public class ShoppingCart {
    private Map<Product, Integer> products = new HashMap<>();

    public ShoppingCart(){}

    public void addItem(Product product, int quantity) {
        products.put(product, products.getOrDefault(product, 0) + quantity);
    }

    public void removeItem(Product product, int quantity) {
        int currentQuantity = products.getOrDefault(product, 0);

        if ((currentQuantity - quantity) <= 0) {
            products.remove(product);
        } else {
            products.put(product, (currentQuantity - quantity));
        }
    }

    public Order onCheckOut() {
        Order order = new Order();

        // iterate over hashmap and create OrderItem for each product and add it to the order
        for (Map.Entry<Product, Integer> item : products.entrySet()) {
            Product currentProduct = item.getKey();
            int currentQty = item.getValue();

            OrderItem orderItem = new OrderItem(order, currentProduct, currentQty);

            order.addProducts(orderItem);
        }

        return order;

    }

}
