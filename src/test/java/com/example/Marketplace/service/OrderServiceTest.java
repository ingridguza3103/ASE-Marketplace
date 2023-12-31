package com.example.Marketplace.service;

import com.example.Marketplace.model.Order;
import com.example.Marketplace.model.OrderItem;
import com.example.Marketplace.repository.OrderRepository;
import com.example.Marketplace.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    OrderRepository orderRepository;

    @InjectMocks
    OrderServiceImpl orderService;

    private Order mockOrder;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockOrder = new Order();
        List<OrderItem> products = new ArrayList<>();
        products.add(new OrderItem());
        mockOrder.setProducts(products);
    }

    @AfterEach
    public void tearDown() {
        Mockito.reset(orderRepository);
        mockOrder = null;
    }

    @Test
    public void testPlaceOrder() {
        when(orderRepository.save(mockOrder)).thenReturn(mockOrder);

        Order returnedOrder = orderService.placeOrder(mockOrder);

        assertNotNull(returnedOrder, "Null check returnedOrder");
        assertNotNull(returnedOrder.getOrderPlaced(), "Null check returnedOrder timestamp");
    }

    @Test
    public void testCancelOrderSuccessful() {
        // set timestamp for mockOrder
        mockOrder.setOrderPlaced(LocalDateTime.now());
        assertTrue(orderService.cancelOrder(mockOrder));
    }

    @Test
    public void testCancelOrderFailed() {
        // set timestamp for mockOrder
        LocalDateTime currentTime = LocalDateTime.now();
        mockOrder.setOrderPlaced(currentTime.minusMinutes(15));
        assertFalse(orderService.cancelOrder(mockOrder));
    }

    @Test
    public void testGetAllOrdersNoOrders() {
        when(orderRepository.findAll()).thenReturn(new ArrayList<>());
        List<Order> orders = orderService.getAllOrders();
        assertEquals(0, orders.size(), "No orders in repository check");
    }

    @Test
    public void testGetAllOrders() {
        List<Order> mockOrders = new ArrayList<>();
        mockOrders.add(mockOrder);
        when(orderRepository.findAll()).thenReturn(mockOrders);
        List<Order> orders = orderService.getAllOrders();
        assertTrue(orders.contains(mockOrder), "check mockOrder contained in List");
    }
}
