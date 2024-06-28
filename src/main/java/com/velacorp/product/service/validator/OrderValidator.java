package com.velacorp.product.service.validator;

import com.velacorp.product.domain.Product;
import com.velacorp.product.repository.ProductRepository;
import com.velacorp.product.service.dto.OrderDTO;
import com.velacorp.product.service.dto.OrderItemDTO;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
@AllArgsConstructor
public class OrderValidator {

    private final ProductRepository productRepository;

    public Mono<Boolean> validate(OrderDTO orderDTO) {
        Flux<OrderItemDTO> orderItemDTOFlux = Flux.fromIterable(orderDTO.getOrderItems());
        return orderItemDTOFlux
            .map(orderItemDTO -> {
                this.productRepository.existsById(orderItemDTO.getProductId())
                    .map(exists -> {
                        if (!exists) {
                            throw new IllegalArgumentException("Product with id " + orderItemDTO.getProductId() + " does not exist");
                        }
                        return exists;
                    })
                    .block();

                this.productRepository.findById(orderItemDTO.getProductId())
                    .map(product -> {
                        if (product.getQuantity() < orderItemDTO.getQuantity()) {
                            throw new IllegalArgumentException(
                                "Product with id " + orderItemDTO.getProductId() + " does not have enough quantity"
                            );
                        }
                        orderItemDTO.setProductPrice(BigDecimal.valueOf(product.getPrice()));
                        return product;
                    })
                    .block();

                return orderItemDTO;
            })
            .subscribeOn(Schedulers.boundedElastic())
            .then(Mono.just(true));
    }
}
