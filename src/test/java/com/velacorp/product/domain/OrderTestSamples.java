package com.velacorp.product.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class OrderTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Order getOrderSample1() {
        return new Order()
            .id(1L)
            .email("email1")
            .shippingAddressId("shippingAddressId1")
            .billingAddressId("billingAddressId1")
            .note("note1")
            .numberItem(1)
            .couponCode("couponCode1")
            .paymentId(1L)
            .checkoutId("checkoutId1")
            .rejectReason("rejectReason1");
    }

    public static Order getOrderSample2() {
        return new Order()
            .id(2L)
            .email("email2")
            .shippingAddressId("shippingAddressId2")
            .billingAddressId("billingAddressId2")
            .note("note2")
            .numberItem(2)
            .couponCode("couponCode2")
            .paymentId(2L)
            .checkoutId("checkoutId2")
            .rejectReason("rejectReason2");
    }

    public static Order getOrderRandomSampleGenerator() {
        return new Order()
            .id(longCount.incrementAndGet())
            .email(UUID.randomUUID().toString())
            .shippingAddressId(UUID.randomUUID().toString())
            .billingAddressId(UUID.randomUUID().toString())
            .note(UUID.randomUUID().toString())
            .numberItem(intCount.incrementAndGet())
            .couponCode(UUID.randomUUID().toString())
            .paymentId(longCount.incrementAndGet())
            .checkoutId(UUID.randomUUID().toString())
            .rejectReason(UUID.randomUUID().toString());
    }
}
