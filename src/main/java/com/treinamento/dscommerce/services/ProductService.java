package com.treinamento.dscommerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.treinamento.dscommerce.dto.ProductDTO;
import com.treinamento.dscommerce.entities.Product;
import com.treinamento.dscommerce.repositories.ProductRepository;
import com.treinamento.dscommerce.services.exceptions.DatabaseException;
import com.treinamento.dscommerce.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository repository;
	
	@Transactional(readOnly = true)
	public ProductDTO findById(Long id) {
		
		Product product = repository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("Recurso não encontrado!"));
				
		ProductDTO dto = new ProductDTO(product);
		return dto;
	}
	
	@Transactional(readOnly = true)
	public Page<ProductDTO> findAll(Pageable pageable) {
		
		Page<Product> result = repository.findAll(pageable);
		
		return result.map(x -> new ProductDTO(x));
	}
	
	@Transactional
	public ProductDTO insert(ProductDTO dto) {
		
		Product entity = new Product();
		copyDtotoEntity(dto, entity);		
		entity = repository.save(entity);
		
		return new ProductDTO(entity);
	}
	
	@Transactional
	public ProductDTO update(Long id, ProductDTO dto) {
		
		try{			
			Product entity = repository.getReferenceById(id);
			copyDtotoEntity(dto, entity);		
			entity = repository.save(entity);
			
			return new ProductDTO(entity);
			
		}
		catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Recurso nao encontrado!");
		}
		//catch(HttpMessageConversionException e) {
		//	throw new ResourceNotFoundException("Recurso tbm nao encontrado!");
		//}
				
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public void delete(Long id) {
		
		if (!repository.existsById(id)) {
			throw new ResourceNotFoundException("Recurso nao encontrado!");
		}
		
		try {
			repository.deleteById(id);
		}
		catch(DataIntegrityViolationException e) {
			throw new DatabaseException("Falha de integridade referencial!");			
		}
				
	}

	private void copyDtotoEntity(ProductDTO dto, Product entity) {
		// TODO Auto-generated method stub
		entity.setName(dto.getName());
		entity.setDescription(dto.getDescription());
		entity.setPrice(dto.getPrice());
		entity.setImgUrl(dto.getImgUrl());
		
	}
	
}
