package com.microservices.order.service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import com.microservices.order.dto.InventoryResponse;
import com.microservices.order.dto.OrderLineItemsDto;
import com.microservices.order.dto.OrderRequest;
import com.microservices.order.model.Order;
import com.microservices.order.model.OrderLineItems;
import com.microservices.order.repository.OrderRepository;

@Service
@Transactional
public class OrderService 
{
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private WebClient.Builder webClientBuilder;

	public String placeOrder(OrderRequest orderRequest) throws IllegalAccessException {
		Order order = new Order();
		order.setOrderNo(UUID.randomUUID().toString());
		List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList().stream().map(this::mapToDto).toList();
		order.setOrderLineItemsList(orderLineItems);
		
		List<String> skuCodes = order.getOrderLineItemsList().stream().map(OrderLineItems::getSkuCode).toList();
		//Call inventory service and place order if product is in stock
		//Step 1: Fine for local machine
		//InventoryResponse[] inventoryResponses = webClient.get()
		//		.uri("http://localhost:9080/api/inventory", uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build()).retrieve().bodyToMono(InventoryResponse[].class).block(); //block() -> Sync Communication
		
		//Step2: In microservice environment everything will be deployed in Cloud environment.
		//In Cloud environment there is no dedicated IP and it is dynamic and runs on different port as well
		//& can have multiple instances. These multiple instance has different IP address like 
		//10.10.20.1, 10.10.20.2 and 10.10.20.3.
		//In this example OrderService calls Inventory Service and Inventory service has multiple instances 
		//and to which Inventory service to called is decided by Service Discovery. 
		InventoryResponse[] inventoryResponses = webClientBuilder.build().get()
				.uri("http://inventory-service/api/inventory", uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build()).retrieve().bodyToMono(InventoryResponse[].class).block(); //block() -> Sync Communication
		boolean allProductInStock = Arrays.stream(inventoryResponses).allMatch(InventoryResponse::isInStock);
		if(allProductInStock)
		{
			orderRepository.save(order);
			return "Order placed successfully.";
		}
		else
		{
			throw new IllegalAccessException("Product not in stock");
		}
	}

	private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
		OrderLineItems orderLineItems = new OrderLineItems();
		orderLineItems.setPrice(orderLineItemsDto.getPrice());
		orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
		orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
		return orderLineItems;
	}

}
