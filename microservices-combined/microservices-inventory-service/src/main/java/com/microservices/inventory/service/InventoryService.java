package com.microservices.inventory.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.microservices.inventory.dto.InventoryResponse;
import com.microservices.inventory.repository.InventoryRepository;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class InventoryService 
{
	@Autowired
	private InventoryRepository inventoryRepository;
	
	@Transactional(readOnly = true)
	@SneakyThrows
	public List<InventoryResponse> isInStock(List<String> skuCode) throws InterruptedException {
		log.info("Wait started....");
		Thread.sleep(10000);
		log.info("Wait ended....");
		return inventoryRepository.findBySkuCodeIn(skuCode).stream().map(inventory ->
			InventoryResponse.builder().skuCode(inventory.getSkuCode())
			.isInStock(inventory.getQuantity() > 0).build()).toList();
	}
}
