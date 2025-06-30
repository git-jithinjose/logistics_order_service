package com.logistics.order.adapter.inbound.web;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.logistics.order.adapter.inbound.dto.OrderItemRequest;
import com.logistics.order.adapter.inbound.dto.OrderResponse;
import com.logistics.order.adapter.inbound.dto.PlaceOrderRequest;
import com.logistics.order.adapter.inbound.exception.GlobalExceptionHandler;
import com.logistics.order.adapter.inbound.exception.dto.ApiResponseWrapper;
import com.logistics.order.adapter.inbound.mapper.OrderResponseMapper;
import com.logistics.order.adapter.inbound.mapper.PlaceOrderRequestToCommandMapper;
import com.logistics.order.application.port.inbound.*;
import com.logistics.order.application.port.inbound.dto.OrderItemCommand;
import com.logistics.order.application.port.inbound.dto.OrderResponseDto;
import com.logistics.order.application.port.inbound.dto.PlaceOrderCommand;
import com.logistics.order.domain.exception.DomainException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Collections;
import java.util.List;

@WebMvcTest(OrderController.class)
@Import(GlobalExceptionHandler.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PlaceOrderUseCase placeOrderUseCase;

    @MockBean
    private ApproveOrderUseCase approveOrderUseCase;

    @MockBean
    private CancelOrderUseCase cancelOrderUseCase;

    @MockBean
    private QueryOrderUseCase queryOrderUseCase;

    @MockBean
    private PlaceOrderRequestToCommandMapper commandMapper;
    
    @Test()
    @DisplayName("place order should return failure response with an error message 'Number of items in an order must be at least 1'")
    void placeOrderFailure() throws Exception {
     
        List<OrderItemRequest> items = Collections.emptyList(); 
        PlaceOrderRequest request = new PlaceOrderRequest(101L, items, "add_1");
              

        String requestJson = objectMapper.writeValueAsString(request);
        mockMvc.perform(post("/api/orders")
        		.content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].message").value("Number of items in an order must be at least 1"))
                .andExpect(jsonPath("$.success").value(false));
    }
    
    @Test
    @DisplayName("place order should return success")
    void placeOrderSucces() throws Exception {
     
        List<OrderItemRequest> items = List.of(new OrderItemRequest("P_01", 1),new OrderItemRequest("P_02", 1)); 
        List<OrderItemCommand> cmdItems =List.of(new OrderItemCommand("P_01", 1),new OrderItemCommand("P_02", 1)); 
        OrderResponseDto responseDto = new OrderResponseDto(
                "ORD125", 
                "PENDING");
        
        PlaceOrderRequest request = new PlaceOrderRequest(101L, items, "add_1");
        PlaceOrderCommand command = new PlaceOrderCommand(cmdItems,101L,"add_1");
        
        when(commandMapper.map(any(PlaceOrderRequest.class))).thenReturn(command);
        
        when(placeOrderUseCase.placeOrder(command)).thenReturn(responseDto);
        

        String requestJson = objectMapper.writeValueAsString(request);
        mockMvc.perform(post("/api/orders")
        		.content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }
    
    @Test
    @DisplayName("approve order should return success, the status in the response body should be 'APPROVED'")
    void approveOrder() throws Exception {
        String orderNumber = "ORD123";
        OrderResponseDto responseDto = new OrderResponseDto(
            "ORD123", 
            "APPROVED");
        
        when(approveOrderUseCase.approveOrder(orderNumber)).thenReturn(responseDto);

        mockMvc.perform(put("/api/orders/{orderNumber}/approve", orderNumber)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.orderNumber").value(responseDto.orderNumber()))
                .andExpect(jsonPath("$.data.status").value("APPROVED"));
    }
    
    @Test
    @DisplayName("approve order should return failure response with an error message "
    		+ "'Only pending orders can be approved.' when trying to approve already approved order")
    void approveOrder_Failure() throws Exception {
        String orderNumber = "ORD123";
        
        when(approveOrderUseCase.approveOrder(orderNumber))
        .thenThrow(new DomainException("Only pending orders can be approved."));

        mockMvc.perform(put("/api/orders/{orderNumber}/approve", orderNumber)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.errors[0].message").value("Only pending orders can be approved."));
    }

   
    
    @Test
    void testCancelOrder() throws Exception {
        String orderNumber = "ORD124";
        OrderResponseDto responseDto = new OrderResponseDto(
            "ORD124", 
            "CANCELLED");
        
        when(cancelOrderUseCase.cancelOrder(orderNumber)).thenReturn(responseDto);

        mockMvc.perform(put("/api/orders/{orderNumber}/cancel", orderNumber)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.orderNumber").value(responseDto.orderNumber()))
                .andExpect(jsonPath("$.data.status").value("CANCELLED"));
    }
    
    
}