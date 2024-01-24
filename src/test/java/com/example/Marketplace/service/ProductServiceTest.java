package com.example.Marketplace.service;


import com.example.Marketplace.model.Product;
import com.example.Marketplace.repository.ProductRepository;
import com.example.Marketplace.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @Mock
    ProductRepository productRepository;

    @InjectMocks
    ProductServiceImpl productService;

    private Product mockProduct;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockProduct = new Product("testProduct", 12.50, 3, 1, 101L, "", "");
        mockProduct.setId(1L);
    }

    @AfterEach
    public void tearDown() {
        Mockito.reset(productRepository);
        mockProduct = null;
    }

    @Test
    public void testGetAllProducts() {
        List<Product> products = new ArrayList<>();
        products.add(mockProduct);
        when(productRepository.findAll()).thenReturn(products);

        List<Product> returnedProducts = productService.getAllProducts();
        assertTrue(returnedProducts.contains(mockProduct), "check if returned list contains mockProduct");
    }

    @Test
    public void testGetAllProductsNoProducts() {

        when(productRepository.findAll()).thenReturn(new ArrayList<Product>());

        List<Product> returnedProducts = productService.getAllProducts();
        assertEquals(0, returnedProducts.size(), "check if returned list is empty");
    }

    @Test
    public void testGetProductById() {
        when(productRepository.findById(mockProduct.getId())).thenReturn(Optional.ofNullable(mockProduct));

        assertEquals(mockProduct, productService.getProductById(1));

    }

    @Test
    public void testUploadProductSuccessful() {
        // mock productRepository to return that the product does not exist in the db
        when(productRepository.checkProductExists(mockProduct.getId())).thenReturn(false);

        assertTrue(productService.uploadProduct(mockProduct));
    }

    @Test
    public void testUploadProductFailed() {
        // mock productRepository to return that the product exists in the db
        when(productRepository.checkProductExists(mockProduct.getId())).thenReturn(true);

        assertFalse(productService.uploadProduct(mockProduct));
    }

    @Test
    public void testRemoveProductSuccessful() {

        when(productRepository.checkProductExists(mockProduct.getId())).thenReturn(true);

        // Act
        productService.removeProduct(mockProduct);

        // Assert
        // Verify that the delete method is called with the expected product
        verify(productRepository, times(1)).delete(mockProduct);
    }

    @Test
    public void testRemoveProductFailed() {

        when(productRepository.checkProductExists(mockProduct.getId())).thenReturn(false);

        // Act
        productService.removeProduct(mockProduct);

        // Assert
        // Verify that the delete method is not called with the expected product
        verify(productRepository, times(0)).delete(mockProduct);
    }

    @Test
    public void testEnoughQuantity() {
        int quantity = 3;
        when(productRepository.availableInDesiredQty(mockProduct.getId(), quantity)).thenReturn(true);

        boolean isAvailable = productService.enoughQuantity(mockProduct.getId(), quantity);

        assertTrue(isAvailable);
    }

    @Test
    public void testNotEnoughQuantity() {
        int quantity = 3;
        when(productRepository.availableInDesiredQty(mockProduct.getId(), quantity)).thenReturn(false);

        boolean isAvailable = productService.enoughQuantity(mockProduct.getId(), quantity);

        assertFalse(isAvailable);
    }


    @Test
    public void testGetProductsBySellerId() {
        ArrayList<Product> userProducts = new ArrayList<>();
        userProducts.add(mockProduct);

        when(productRepository.findBySellerId(mockProduct.getSellerId())).thenReturn(userProducts);

        ArrayList<Product> retrievedProducts = (ArrayList<Product>) productService.getProductsBySellerId(mockProduct.getSellerId());

        assertEquals(userProducts, retrievedProducts);

    }

}
