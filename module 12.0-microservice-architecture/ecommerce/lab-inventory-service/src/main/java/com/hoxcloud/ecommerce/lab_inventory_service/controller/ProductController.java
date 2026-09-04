package com.hoxcloud.ecommerce.lab_inventory_service.controller;

import com.hoxcloud.ecommerce.lab_inventory_service.DTO.ProductDTO;
import com.hoxcloud.ecommerce.lab_inventory_service.service.ProductService;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.util.List;

@RestController
@RequestMapping("products")
public class ProductController {

    private final ProductService productService;
    private final DiscoveryClient discoveryClient;
    private final RestClient restClient;

    public ProductController(ProductService productService, DiscoveryClient discoveryClient, RestClient restClient) {
        this.productService = productService;
        this.discoveryClient = discoveryClient;
        this.restClient = restClient;
    }


    @GetMapping("/orderFetch")
    public String  getOrderService()
    {
        ServiceInstance orderServiceInstance=discoveryClient.getInstances("lab-order-service").getFirst();
       String messageReceive= restClient.get()
                .uri(orderServiceInstance.getUri()+"/api/v1/orders/helloOrder")
                .retrieve()
                .body(String.class);

        return "We Recive Message as : " + messageReceive;
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
