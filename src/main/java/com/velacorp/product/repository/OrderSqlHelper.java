package com.velacorp.product.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Table;

public class OrderSqlHelper {

    public static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();
        columns.add(Column.aliased("id", table, columnPrefix + "_id"));
        columns.add(Column.aliased("email", table, columnPrefix + "_email"));
        columns.add(Column.aliased("shipping_address_id", table, columnPrefix + "_shipping_address_id"));
        columns.add(Column.aliased("billing_address_id", table, columnPrefix + "_billing_address_id"));
        columns.add(Column.aliased("note", table, columnPrefix + "_note"));
        columns.add(Column.aliased("tax", table, columnPrefix + "_tax"));
        columns.add(Column.aliased("discount", table, columnPrefix + "_discount"));
        columns.add(Column.aliased("number_item", table, columnPrefix + "_number_item"));
        columns.add(Column.aliased("coupon_code", table, columnPrefix + "_coupon_code"));
        columns.add(Column.aliased("total_price", table, columnPrefix + "_total_price"));
        columns.add(Column.aliased("delivery_fee", table, columnPrefix + "_delivery_fee"));
        columns.add(Column.aliased("order_status", table, columnPrefix + "_order_status"));
        columns.add(Column.aliased("delivery_method", table, columnPrefix + "_delivery_method"));
        columns.add(Column.aliased("delivery_status", table, columnPrefix + "_delivery_status"));
        columns.add(Column.aliased("payment_status", table, columnPrefix + "_payment_status"));
        columns.add(Column.aliased("payment_id", table, columnPrefix + "_payment_id"));
        columns.add(Column.aliased("checkout_id", table, columnPrefix + "_checkout_id"));
        columns.add(Column.aliased("reject_reason", table, columnPrefix + "_reject_reason"));

        return columns;
    }
}
