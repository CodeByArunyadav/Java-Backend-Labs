package com.hoxcloud.ecommerce.lab_inventory_service.service;

import com.hoxcloud.ecommerce.lab_inventory_service.DTO.ProductDTO;
import com.hoxcloud.ecommerce.lab_inventory_service.entity.ProductEntity;
import com.hoxcloud.ecommerce.lab_inventory_service.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        return productRepository.findAll().stream().map(p->modelMapper.map(p,ProductDTO.class)).collect(Collectors.toList());
    }
}
