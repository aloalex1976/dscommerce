package com.treinamento.dscommerce.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.treinamento.dscommerce.dto.ProductDTO;
import com.treinamento.dscommerce.entities.Product;
import com.treinamento.dscommerce.repositories.ProductRepository;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository repository;
	
	@Transactional(readOnly = true)
	public ProductDTO findById(Long id) {
		Optional<Product> result = repository.findById(id);
		Product product = result.get();
		
		ProductDTO dto = new ProductDTO(product);
		return dto;
	}
	
	
	 

}
