package com.microservices.order.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

	public void placeOrder(OrderRequest orderRequest) {
		Order order = new Order();
		order.setOrderNo(UUID.randomUUID().toString());
		List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList().stream().map(this::mapToDto).toList();
		order.setOrderLineItemsList(orderLineItems);
		orderRepository.save(order);
	}

	private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
		OrderLineItems orderLineItems = new OrderLineItems();
		orderLineItems.setPrice(orderLineItemsDto.getPrice());
		orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
		orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
		return orderLineItems;
	}

}
