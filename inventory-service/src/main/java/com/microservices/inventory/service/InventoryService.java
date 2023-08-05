package com.microservices.inventory.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.microservices.inventory.repository.InventoryRepository;

@Service
public class InventoryService 
{
	@Autowired
	private InventoryRepository inventoryRepository;
	
	@Transactional
	public boolean isInStock(String skuCode) {
		return inventoryRepository.findBySkuCode(skuCode).isPresent();
	}
}
