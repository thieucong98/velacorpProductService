package com.velacorp.product.service.validator;

import com.velacorp.product.repository.ProductRepository;
import com.velacorp.product.service.dto.OrderDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderValidator {

    private final ProductRepository productRepository;

    public boolean validate(OrderDTO orderDTO) {
        orderDTO
            .getOrderItems()
            .forEach(orderItemDTO -> {
                this.productRepository.findById(orderItemDTO.getProductId()).subscribe(product -> {
                        if (product == null) {
                            throw new IllegalArgumentException("Product with id " + orderItemDTO.getProductId() + " does not exist");
                        }
                        if (orderItemDTO.getQuantity() < orderItemDTO.getQuantity()) {
                            throw new IllegalArgumentException(
                                "Product with id " + orderItemDTO.getProductId() + " does not have enough quantity"
                            );
                        }
                    });
            });

        return true;
    }
}
