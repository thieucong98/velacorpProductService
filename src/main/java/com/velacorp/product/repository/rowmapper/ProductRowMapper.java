package com.velacorp.product.repository.rowmapper;

import com.velacorp.product.domain.Product;
import io.r2dbc.spi.Row;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link Product}, with proper type conversions.
 */
@Service
public class ProductRowMapper implements BiFunction<Row, String, Product> {

    private final ColumnConverter converter;

    public ProductRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link Product} stored in the database.
     */
    @Override
    public Product apply(Row row, String prefix) {
        Product entity = new Product();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setName(converter.fromRow(row, prefix + "_name", String.class));
        entity.setPrice(converter.fromRow(row, prefix + "_price", Double.class));
        entity.setActive(converter.fromRow(row, prefix + "_active", Boolean.class));
        entity.setImageUrl(converter.fromRow(row, prefix + "_image_url", String.class));
        return entity;
    }
}
