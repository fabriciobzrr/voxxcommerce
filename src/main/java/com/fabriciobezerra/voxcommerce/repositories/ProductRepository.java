package com.fabriciobezerra.voxcommerce.repositories;

import com.fabriciobezerra.voxcommerce.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
