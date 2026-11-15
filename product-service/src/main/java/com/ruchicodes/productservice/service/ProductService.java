package com.ruchicodes.productservice.service;

import com.ruchicodes.productservice.dto.ProductRequest;
import com.ruchicodes.productservice.dto.ProductResponse;
import com.ruchicodes.productservice.entity.Product;
import com.ruchicodes.productservice.exception.DuplicateProductSkuException;
import com.ruchicodes.productservice.exception.ProductNotFoundException;
import com.ruchicodes.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository productRepository;

    @Transactional
    public ProductResponse createProduct(ProductRequest request){
        String sku = normalizeSku(request.sku());
        if (productRepository.existsBySku(sku)){
            throw new DuplicateProductSkuException("Product with SKU "+sku+" already exists");
        }
        Product product = new Product();
        product.setName(request.name().trim());
        product.setSku(sku);
        product.setDescription(request.description());
        product.setPrice(request.price());
        product.setStockQuantity(request.stockQuantity());

        Product savedProduct = productRepository.save(product);
        return mapToResponse(savedProduct);
    }

    public List<ProductResponse> getAllProducts(){
        return productRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public ProductResponse getProductById(Long id){
        Product product = productRepository.findById(id)
                .orElseThrow(()->new ProductNotFoundException("Product not found with ID: "+id));
        return mapToResponse(product);
    }

    @Transactional
    public ProductResponse updateProduct(Long id, ProductRequest request){
        Product product = productRepository.findById(id)
                .orElseThrow(()->new ProductNotFoundException("Product not found with ID: "+id));
        String sku = normalizeSku(request.sku());
        if (productRepository.existsBySkuAndIdNot(sku,id)){
            throw new DuplicateProductSkuException("Product with SKU "+sku+" already exists");
        }
        product.setName(request.name().trim());
        product.setSku(request.sku());
        product.setDescription(request.description());
        product.setPrice(request.price());
        product.setStockQuantity(request.stockQuantity());
        Product updatedProduct = productRepository.save(product);
        return mapToResponse(updatedProduct);
    }

    @Transactional
    public void deleteProduct(Long id){
        Product product = productRepository.findById(id)
                .orElseThrow(()->
                        new ProductNotFoundException("Product not found with ID: "+id));
        productRepository.delete(product);
    }

    private String normalizeSku(String  sku){
        return sku.trim().toUpperCase(Locale.ROOT);
    }

    private ProductResponse mapToResponse(Product product){
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getSku(),
                product.getDescription(),
                product.getPrice(),
                product.getStockQuantity()
        );
    }
}
