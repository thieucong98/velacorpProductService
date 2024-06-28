package com.velacorp.product.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ProductTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Product getProductSample1() {
        return new Product().id(1L).name("name1").quantity(1L).imageUrl("imageUrl1");
    }

    public static Product getProductSample2() {
        return new Product().id(2L).name("name2").quantity(2L).imageUrl("imageUrl2");
    }

    public static Product getProductRandomSampleGenerator() {
        return new Product()
            .id(longCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .quantity(longCount.incrementAndGet())
            .imageUrl(UUID.randomUUID().toString());
    }
}
