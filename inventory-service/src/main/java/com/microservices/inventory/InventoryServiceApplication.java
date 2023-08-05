package com.microservices.inventory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.microservices.inventory.model.Inventory;
import com.microservices.inventory.repository.InventoryRepository;

@SpringBootApplication
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner loadData(InventoryRepository inventoryRepository) {
		return args -> {
			Inventory inventory1 = new Inventory();
			inventory1.setSkuCode("iPhone");
			inventory1.setQuantity(12);
			
			Inventory inventory2 = new Inventory();
			inventory2.setSkuCode("Nokia");
			inventory2.setQuantity(10);
			
			inventoryRepository.save(inventory1);
			inventoryRepository.save(inventory2);
		};
	}
}
