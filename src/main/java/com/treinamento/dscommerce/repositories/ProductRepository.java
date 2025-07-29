package com.treinamento.dscommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.treinamento.dscommerce.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

}
