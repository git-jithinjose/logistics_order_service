package com.logistics.order.adapter.inbound.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.logistics.order.adapter.inbound.dto.OrderResponse;
import com.logistics.order.adapter.inbound.dto.PlaceOrderRequest;
import com.logistics.order.adapter.inbound.exception.dto.ApiResponseWrapper;
import com.logistics.order.adapter.inbound.mapper.OrderResponseMapper;
import com.logistics.order.adapter.inbound.mapper.PlaceOrderRequestToCommandMapper;
import com.logistics.order.application.port.inbound.ApproveOrderUseCase;
import com.logistics.order.application.port.inbound.CancelOrderUseCase;
import com.logistics.order.application.port.inbound.PlaceOrderUseCase;
import com.logistics.order.application.port.inbound.QueryOrderUseCase;
import com.logistics.order.application.port.inbound.dto.OrderResponseDto;
import com.logistics.order.application.port.inbound.dto.PlaceOrderCommand;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/orders")
public class OrderController {
	
	private static final Logger LOG = LoggerFactory.getLogger(OrderController.class);

	private final PlaceOrderUseCase placeOrderUseCase;
	private final ApproveOrderUseCase approveOrderUseCase;
	private final CancelOrderUseCase cancelOrderUseCase;
	private final QueryOrderUseCase queryOrderUseCase;

	private final PlaceOrderRequestToCommandMapper commandMapper;

	public OrderController(PlaceOrderRequestToCommandMapper commandMapper, PlaceOrderUseCase placeOrderUseCase,
			ApproveOrderUseCase approveOrderUseCase, CancelOrderUseCase cancelOrderUseCase,
			QueryOrderUseCase queryOrderUseCase) {
		this.placeOrderUseCase = placeOrderUseCase;
		this.approveOrderUseCase = approveOrderUseCase;
		this.cancelOrderUseCase = cancelOrderUseCase;
		this.commandMapper = commandMapper;
		this.queryOrderUseCase = queryOrderUseCase;
	}

	@PostMapping
	public ResponseEntity<ApiResponseWrapper<OrderResponse>> placeOrder(@RequestBody @Valid PlaceOrderRequest request) {
		LOG.info("Received request to place a new order");
		PlaceOrderCommand command = commandMapper.map(request);
		OrderResponseDto order = placeOrderUseCase.placeOrder(command);
		return ResponseEntity.status(HttpStatus.OK)
				.body(ApiResponseWrapper.success(OrderResponseMapper.toOrderResponse(order)));

	}

	@PutMapping("/{orderNumber}/approve")
	public ResponseEntity<ApiResponseWrapper<OrderResponse>> approveOrder(@PathVariable String orderNumber) {
		LOG.info("Received request to approve order");
		OrderResponseDto order = approveOrderUseCase.approveOrder(orderNumber);
		return ResponseEntity.status(HttpStatus.OK)
				.body(ApiResponseWrapper.success(OrderResponseMapper.toOrderResponse(order)));

	}

	@PutMapping("/{orderNumber}/cancel")
	public ResponseEntity<ApiResponseWrapper<OrderResponse>> cancelOrder(@PathVariable String orderNumber) {
		LOG.info("Received request to cancel order");
		OrderResponseDto orderResponseDto = cancelOrderUseCase.cancelOrder(orderNumber);
		OrderResponse orderResponseToApi = OrderResponseMapper.toOrderResponse(orderResponseDto);
		ApiResponseWrapper<OrderResponse> genericResponse = ApiResponseWrapper.success(orderResponseToApi);

		return ResponseEntity.status(HttpStatus.OK).body(genericResponse);

	}

	@GetMapping
	public ResponseEntity<ApiResponseWrapper<OrderResponse>> getOrder(@RequestParam("orderNumber") String orderNumber) {
		LOG.info("Received request to get details of the order");
		OrderResponseDto orderResponseDto = queryOrderUseCase.getOrderStatus(orderNumber);
		OrderResponse orderResponseToApi = OrderResponseMapper.toOrderResponse(orderResponseDto);
		ApiResponseWrapper<OrderResponse> genericResponse = ApiResponseWrapper.success(orderResponseToApi);

		return ResponseEntity.status(HttpStatus.OK).body(genericResponse);
	}

}
