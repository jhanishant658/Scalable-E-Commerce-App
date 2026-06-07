package com.ecommerce.productcatalogservice.repository;

import com.ecommerce.productcatalogservice.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
