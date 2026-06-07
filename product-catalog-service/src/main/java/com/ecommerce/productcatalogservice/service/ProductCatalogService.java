package com.ecommerce.productcatalogservice.service;

import com.ecommerce.productcatalogservice.dto.ProductDtos;
import com.ecommerce.productcatalogservice.entity.Category;
import com.ecommerce.productcatalogservice.entity.Product;
import com.ecommerce.productcatalogservice.repository.CategoryRepository;
import com.ecommerce.productcatalogservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductCatalogService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductDtos.CategoryResponse createCategory(ProductDtos.CategoryRequest request) {
        Category category = categoryRepository.save(Category.builder().name(request.name()).description(request.description()).build());
        return toCategoryResponse(category);
    }

    public List<ProductDtos.CategoryResponse> categories() {
        return categoryRepository.findAll().stream().map(this::toCategoryResponse).toList();
    }

    public ProductDtos.ProductResponse createProduct(ProductDtos.ProductRequest request) {
        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));
        Product product = Product.builder()
                .name(request.name())
                .description(request.description())
                .price(request.price())
                .inventoryQuantity(request.inventoryQuantity())
                .imageUrl(request.imageUrl())
                .active(true)
                .category(category)
                .build();
        return toProductResponse(productRepository.save(product));
    }

    public List<ProductDtos.ProductResponse> products(Long categoryId) {
        List<Product> products = categoryId == null
                ? productRepository.findByActiveTrue()
                : productRepository.findByCategory_IdAndActiveTrue(categoryId);
        return products.stream().map(this::toProductResponse).toList();
    }

    public ProductDtos.ProductResponse product(Long productId) {
        return productRepository.findById(productId).map(this::toProductResponse)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
    }

    @Transactional
    public ProductDtos.ProductResponse updateInventory(Long productId, ProductDtos.InventoryRequest request) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        product.setInventoryQuantity(request.quantity());
        return toProductResponse(productRepository.save(product));
    }

    @Transactional
    public ProductDtos.ProductResponse reduceInventory(Long productId, ProductDtos.InventoryRequest request) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        if (product.getInventoryQuantity() < request.quantity()) {
            throw new IllegalArgumentException("Insufficient inventory");
        }
        product.setInventoryQuantity(product.getInventoryQuantity() - request.quantity());
        return toProductResponse(productRepository.save(product));
    }

    public ProductDtos.AvailabilityResponse availability(Long productId, Integer quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        return new ProductDtos.AvailabilityResponse(productId, product.isActive() && product.getInventoryQuantity() >= quantity,
                product.getInventoryQuantity(), product.getPrice());
    }

    private ProductDtos.ProductResponse toProductResponse(Product product) {
        return new ProductDtos.ProductResponse(product.getId(), product.getName(), product.getDescription(), product.getPrice(),
                product.getInventoryQuantity(), product.getImageUrl(), product.isActive(), toCategoryResponse(product.getCategory()));
    }

    private ProductDtos.CategoryResponse toCategoryResponse(Category category) {
        return new ProductDtos.CategoryResponse(category.getId(), category.getName(), category.getDescription());
    }
}
