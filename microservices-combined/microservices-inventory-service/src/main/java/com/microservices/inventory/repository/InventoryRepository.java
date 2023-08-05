package com.microservices.inventory.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservices.inventory.model.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory, Long>{

	public List<Inventory> findBySkuCodeIn(List<String> skuCode);

}
