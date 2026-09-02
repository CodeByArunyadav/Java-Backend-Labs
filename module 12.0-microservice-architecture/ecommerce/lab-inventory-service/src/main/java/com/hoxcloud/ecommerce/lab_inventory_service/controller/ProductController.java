package com.hoxcloud.ecommerce.lab_inventory_service.controller;

import com.hoxcloud.ecommerce.lab_inventory_service.DTO.ProductDTO;
import com.hoxcloud.ecommerce.lab_inventory_service.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @PostMapping
    public void createProducts(ProductDTO productDTO)
    {
        productService.createProducts(productDTO);
    }

    @GetMapping(path = "{id}")
    public ProductDTO getProductByID(@PathVariable Long id)
    {
        return productService.getProductById(id);
    }

    @GetMapping()
    public List<ProductDTO> getlistOfProducts()
    {
        return productService.getlistOfProducts();
    }
}
