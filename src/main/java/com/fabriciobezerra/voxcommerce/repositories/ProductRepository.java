package com.fabriciobezerra.voxcommerce.repositories;

import com.fabriciobezerra.voxcommerce.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    //    @Query(nativeQuery = true, value = "SELECT * FROM tb_product WHERE UPPER(tb_product.name) LIKE UPPER(CONCAT('%', :name, '%'))")
    @Query("SELECT obj FROM Product obj WHERE UPPER(obj.name) LIKE UPPER(CONCAT('%', :name, '%'))")
    Page<Product> searchByName(String name, Pageable pageable);
}
