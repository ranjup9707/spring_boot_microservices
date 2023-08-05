package com.microservices.product.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.microservices.product.dto.ProductRequest;
import com.microservices.product.dto.ProductResponse;
import com.microservices.product.model.Product;
import com.microservices.product.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService 
{
	private final ProductRepository productRepository;
	
	public void createProduct(ProductRequest productRequest) {
		Product product = Product.builder().name(productRequest.getName()).description(productRequest.getDescription()).price(productRequest.getPrice()).build();
		productRepository.save(product);
		log.info("Product is saved");
	}

	public List<ProductResponse> getAllProducts() {
		List<Product> getAllProduct = productRepository.findAll();
		return getAllProduct.stream().map(this::mapToProductResponse).toList();
	}

	private ProductResponse mapToProductResponse(Product product){
		return ProductResponse.builder().id(product.getId()).name(product.getName()).description(product.getDescription()).price(product.getPrice()).build();
	}
	
}
