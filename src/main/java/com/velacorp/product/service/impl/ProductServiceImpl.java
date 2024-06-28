package com.velacorp.product.service.impl;

import com.velacorp.product.repository.ProductRepository;
import com.velacorp.product.service.ProductService;
import com.velacorp.product.service.dto.ProductDTO;
import com.velacorp.product.service.mapper.ProductMapper;
import com.velacorp.product.viewmodel.product.ProductGetVM;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link com.velacorp.product.domain.Product}.
 */
@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public Mono<ProductDTO> save(ProductDTO productDTO) {
        log.debug("Request to save Product : {}", productDTO);
        return productRepository.save(productMapper.toEntity(productDTO)).map(productMapper::toDto);
    }

    @Override
    public Mono<ProductDTO> update(ProductDTO productDTO) {
        log.debug("Request to update Product : {}", productDTO);
        return productRepository.save(productMapper.toEntity(productDTO)).map(productMapper::toDto);
    }

    @Override
    public Mono<ProductDTO> partialUpdate(ProductDTO productDTO) {
        log.debug("Request to partially update Product : {}", productDTO);

        return productRepository
            .findById(productDTO.getId())
            .map(existingProduct -> {
                productMapper.partialUpdate(existingProduct, productDTO);

                return existingProduct;
            })
            .flatMap(productRepository::save)
            .map(productMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<ProductDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Products");
        return productRepository.findAllBy(pageable).map(productMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<ProductDTO> findAll(Pageable pageable, ProductGetVM query) {
        log.debug("Request to get all Products");
        if (query != null && StringUtils.isNotEmpty(query.getName())) {
            return productRepository.findAllByNameLike(query.getName(), pageable).map(productMapper::toDto);
        }
        return productRepository.findAllBy(pageable).map(productMapper::toDto);
    }

    public Mono<Long> countAll() {
        return productRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<ProductDTO> findOne(Long id) {
        log.debug("Request to get Product : {}", id);
        return productRepository.findById(id).map(productMapper::toDto);
    }

    @Override
    public Mono<Void> delete(Long id) {
        log.debug("Request to delete Product : {}", id);
        return productRepository.deleteById(id);
    }
}
