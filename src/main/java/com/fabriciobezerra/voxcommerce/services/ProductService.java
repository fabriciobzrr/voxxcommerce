package com.fabriciobezerra.voxcommerce.services;

import com.fabriciobezerra.voxcommerce.dto.CategoryDto;
import com.fabriciobezerra.voxcommerce.dto.ProductDto;
import com.fabriciobezerra.voxcommerce.dto.ProductMinDto;
import com.fabriciobezerra.voxcommerce.entities.Category;
import com.fabriciobezerra.voxcommerce.entities.Product;
import com.fabriciobezerra.voxcommerce.repositories.CategoryRepository;
import com.fabriciobezerra.voxcommerce.repositories.ProductRepository;
import com.fabriciobezerra.voxcommerce.services.exceptions.DatabaseException;
import com.fabriciobezerra.voxcommerce.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;
    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public Page<ProductMinDto> findAll(String name, Pageable pageable) {
        Page<Product> result = repository.searchByName(name, pageable);
        return result.map(x -> new ProductMinDto(x));
    }

    @Transactional(readOnly = true)
    public ProductDto findById(Long id) {
        Product product = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado! Id: " + id + "."));
        return new ProductDto(product);
    }

    @Transactional
    public ProductDto insert(ProductDto dto) {
        Product entity = new Product();
        copyDtoToEntity(dto, entity);
        entity = repository.save(entity);
        return new ProductDto(entity);
    }

    @Transactional
    public ProductDto update(Long id, ProductDto dto) {
        try {
            Product entity = repository.getReferenceById(id);
            copyDtoToEntity(dto, entity);
            entity = repository.save(entity);
            return new ProductDto(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Recurso não encontrado! Id: " + id + ".");
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Recurso não encontrado! Id: " + id + ".");
        }
        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Falha de integridade referencial.");
        }
    }

    private void copyDtoToEntity(ProductDto dto, Product entity) {
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setImgUrl(dto.getImgUrl());
        entity.setPrice(dto.getPrice());

        entity.getCategories().clear();
        for (CategoryDto catDto : dto.getCategories()) {
            Category category = categoryRepository.getReferenceById(catDto.getId());
            entity.getCategories().add(category);
        }
    }

}
