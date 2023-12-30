package com.example.Marketplace.model;


import com.example.Marketplace.service.OrderService;
import com.example.Marketplace.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * ShoppingCart class represents a users shopping cart as a Map of Product and Integer
 * so the Map stores each Product with the selected quantity.
 * This class provides all methods needed for the shopping cart.
 *
 * If the user clicks on checkout the current ShoppingCart is converted to an Order object
 */
@Component
public class ShoppingCart {
    private Map<Product, Integer> products = new HashMap<>();
    // TODO: Think about having the Buyer User instance here
    @Autowired
    OrderService orderService;
    @Autowired
    UserService userService;

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
        // TODO: Authenticate User
        Order order = new Order();

        // iterate over hashmap and create OrderItem for each product and add it to the order
        for (Map.Entry<Product, Integer> item : products.entrySet()) {
            Product currentProduct = item.getKey();
            int currentQty = item.getValue();

            OrderItem orderItem = new OrderItem(order, currentProduct, currentQty);

            order.addProducts(orderItem);

            // notify the sellers
            notifySellers(currentProduct, currentQty);
        }

        // place the order
        orderService.placeOrder(order);



        return order;

    }

    /**
     * This method notifies the seller of the product that a given quantity of a product has been ordered
     * @param currentProduct the ordered product
     * @param currentQty the ordered quantity of the product
     */
    private void notifySellers(Product currentProduct, int currentQty) {
        User seller = userService.getUser(currentProduct.getSellerId());
        // TODO: Notify seller with buyer information and product and quantity.
    }

}
