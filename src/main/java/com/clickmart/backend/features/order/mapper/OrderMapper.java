package com.clickmart.backend.features.order.mapper;

import com.clickmart.backend.entity.Order;
import com.clickmart.backend.entity.OrderItem;
import com.clickmart.backend.features.order.dto.OrderDTO;
import com.clickmart.backend.features.order.dto.OrderItemDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class OrderMapper {

    public OrderDTO toDTO(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setOrderNumber(order.getOrderNumber());
        dto.setStatus(order.getStatus() != null ? order.getStatus().name().toLowerCase() : "pending");
        dto.setPaymentMethod(order.getPaymentMethod() != null ? order.getPaymentMethod().name().toLowerCase() : "cod");
        if (order.getUser() != null) {
            dto.setCustomerId(order.getUser().getId());
            dto.setCustomerName(order.getUser().getFirstName() + " " + order.getUser().getLastName());
            dto.setCustomerEmail(order.getUser().getEmail());
        }
        dto.setSubtotal(order.getSubtotal());
        dto.setDeliveryCharge(order.getDeliveryCharge());
        dto.setDiscount(order.getDiscount());
        dto.setTotal(order.getTotal());
        dto.setShippingName(order.getShippingName());
        dto.setShippingPhone(order.getShippingPhone());
        dto.setShippingAddr1(order.getShippingAddr1());
        dto.setShippingAddr2(order.getShippingAddr2());
        dto.setShippingCity(order.getShippingCity());
        dto.setShippingState(order.getShippingState());
        dto.setShippingPin(order.getShippingPin());
        dto.setDeliveryType(order.getDeliveryType());
        dto.setCouponCode(order.getCouponCode());
        dto.setCreatedAt(order.getCreatedAt());

        List<OrderItemDTO> itemDTOs = new ArrayList<>();
        if (order.getItems() != null) {
            for (OrderItem item : order.getItems()) {
                itemDTOs.add(toItemDTO(item));
            }
        }
        dto.setItems(itemDTOs);

        return dto;
    }

    private OrderItemDTO toItemDTO(OrderItem oi) {
        OrderItemDTO dto = new OrderItemDTO();
        dto.setId(oi.getId());
        if (oi.getProduct() != null) dto.setProductId(oi.getProduct().getId());
        dto.setProductName(oi.getProductName());
        dto.setProductImage(oi.getProductImage());
        dto.setUnitPrice(oi.getUnitPrice());
        dto.setQuantity(oi.getQuantity());
        dto.setLineTotal(oi.getUnitPrice() != null && oi.getQuantity() != null
                ? oi.getUnitPrice() * oi.getQuantity() : null);
        return dto;
    }
}
