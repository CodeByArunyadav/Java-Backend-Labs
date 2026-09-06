package com.hoxcloud.ecommerce.lab_inventory_service.service;

import com.hoxcloud.ecommerce.lab_inventory_service.DTO.InventoryItemRequest;
import com.hoxcloud.ecommerce.lab_inventory_service.DTO.OrderInventoryRequest;
import com.hoxcloud.ecommerce.lab_inventory_service.DTO.ProductDTO;
import com.hoxcloud.ecommerce.lab_inventory_service.entity.ProductEntity;
import com.hoxcloud.ecommerce.lab_inventory_service.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ProductService {

    @Autowired
    ModelMapper modelMapper;

    final private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void createProducts(ProductDTO productDTO) {
        productRepository.save(modelMapper.map(productDTO, ProductEntity.class));
    }

    public ProductDTO getProductById(Long id) {
        return modelMapper.map(productRepository.getReferenceById(id), ProductDTO.class);
    }

    public List<ProductDTO> getlistOfProducts() {

        return productRepository.findAll().stream().map(p -> modelMapper.map(p, ProductDTO.class)).collect(Collectors.toList());
    }

    @Transactional
    public void reserve(OrderInventoryRequest request) {

        for (InventoryItemRequest item : request.items()) {
            ProductEntity product = productRepository.findById(item.productId()).orElseThrow(() -> new RuntimeException("No such item Found" + item.productId()));

            if (product.getStock() < item.quantity()) {
                throw new RuntimeException("Item Out Of Stock");
            }
            product.setStock(product.getStock() - item.quantity());
        }
    }

    @Transactional
    public void release(OrderInventoryRequest request) {

        for (InventoryItemRequest item : request.items()) {
            ProductEntity product = productRepository.findById(item.productId()).orElseThrow(() -> new RuntimeException("No such item Found" + item.productId()));

            if (product.getStock() < item.quantity()) {
                throw new RuntimeException("Item Out Of Stock");
            }
            product.setStock(product.getStock() + item.quantity());
        }
    }
}
