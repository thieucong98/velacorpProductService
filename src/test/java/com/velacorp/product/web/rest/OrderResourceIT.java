package com.velacorp.product.web.rest;

import static com.velacorp.product.domain.OrderAsserts.*;
import static com.velacorp.product.web.rest.TestUtil.createUpdateProxyForBean;
import static com.velacorp.product.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.velacorp.product.IntegrationTest;
import com.velacorp.product.domain.Order;
import com.velacorp.product.domain.enumeration.DeliveryMethod;
import com.velacorp.product.domain.enumeration.DeliveryStatus;
import com.velacorp.product.domain.enumeration.OrderStatus;
import com.velacorp.product.domain.enumeration.PaymentStatus;
import com.velacorp.product.repository.EntityManager;
import com.velacorp.product.repository.OrderRepository;
import java.math.BigDecimal;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Integration tests for the {@link OrderResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class OrderResourceIT {

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_SHIPPING_ADDRESS_ID = "AAAAAAAAAA";
    private static final String UPDATED_SHIPPING_ADDRESS_ID = "BBBBBBBBBB";

    private static final String DEFAULT_BILLING_ADDRESS_ID = "AAAAAAAAAA";
    private static final String UPDATED_BILLING_ADDRESS_ID = "BBBBBBBBBB";

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    private static final Float DEFAULT_TAX = 1F;
    private static final Float UPDATED_TAX = 2F;

    private static final Float DEFAULT_DISCOUNT = 1F;
    private static final Float UPDATED_DISCOUNT = 2F;

    private static final Integer DEFAULT_NUMBER_ITEM = 1;
    private static final Integer UPDATED_NUMBER_ITEM = 2;

    private static final String DEFAULT_COUPON_CODE = "AAAAAAAAAA";
    private static final String UPDATED_COUPON_CODE = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_TOTAL_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_PRICE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_DELIVERY_FEE = new BigDecimal(1);
    private static final BigDecimal UPDATED_DELIVERY_FEE = new BigDecimal(2);

    private static final OrderStatus DEFAULT_ORDER_STATUS = OrderStatus.PENDING;
    private static final OrderStatus UPDATED_ORDER_STATUS = OrderStatus.ACCEPTED;

    private static final DeliveryMethod DEFAULT_DELIVERY_METHOD = DeliveryMethod.VIETTEL_POST;
    private static final DeliveryMethod UPDATED_DELIVERY_METHOD = DeliveryMethod.GRAB_EXPRESS;

    private static final DeliveryStatus DEFAULT_DELIVERY_STATUS = DeliveryStatus.PREPARING;
    private static final DeliveryStatus UPDATED_DELIVERY_STATUS = DeliveryStatus.DELIVERING;

    private static final PaymentStatus DEFAULT_PAYMENT_STATUS = PaymentStatus.PENDING;
    private static final PaymentStatus UPDATED_PAYMENT_STATUS = PaymentStatus.COMPLETED;

    private static final Long DEFAULT_PAYMENT_ID = 1L;
    private static final Long UPDATED_PAYMENT_ID = 2L;

    private static final String DEFAULT_CHECKOUT_ID = "AAAAAAAAAA";
    private static final String UPDATED_CHECKOUT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_REJECT_REASON = "AAAAAAAAAA";
    private static final String UPDATED_REJECT_REASON = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/orders";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private Order order;

    private Order insertedOrder;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Order createEntity(EntityManager em) {
        Order order = new Order()
            .email(DEFAULT_EMAIL)
            .shippingAddressId(DEFAULT_SHIPPING_ADDRESS_ID)
            .billingAddressId(DEFAULT_BILLING_ADDRESS_ID)
            .note(DEFAULT_NOTE)
            .tax(DEFAULT_TAX)
            .discount(DEFAULT_DISCOUNT)
            .numberItem(DEFAULT_NUMBER_ITEM)
            .couponCode(DEFAULT_COUPON_CODE)
            .totalPrice(DEFAULT_TOTAL_PRICE)
            .deliveryFee(DEFAULT_DELIVERY_FEE)
            .orderStatus(DEFAULT_ORDER_STATUS)
            .deliveryMethod(DEFAULT_DELIVERY_METHOD)
            .deliveryStatus(DEFAULT_DELIVERY_STATUS)
            .paymentStatus(DEFAULT_PAYMENT_STATUS)
            .paymentId(DEFAULT_PAYMENT_ID)
            .checkoutId(DEFAULT_CHECKOUT_ID)
            .rejectReason(DEFAULT_REJECT_REASON);
        return order;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Order createUpdatedEntity(EntityManager em) {
        Order order = new Order()
            .email(UPDATED_EMAIL)
            .shippingAddressId(UPDATED_SHIPPING_ADDRESS_ID)
            .billingAddressId(UPDATED_BILLING_ADDRESS_ID)
            .note(UPDATED_NOTE)
            .tax(UPDATED_TAX)
            .discount(UPDATED_DISCOUNT)
            .numberItem(UPDATED_NUMBER_ITEM)
            .couponCode(UPDATED_COUPON_CODE)
            .totalPrice(UPDATED_TOTAL_PRICE)
            .deliveryFee(UPDATED_DELIVERY_FEE)
            .orderStatus(UPDATED_ORDER_STATUS)
            .deliveryMethod(UPDATED_DELIVERY_METHOD)
            .deliveryStatus(UPDATED_DELIVERY_STATUS)
            .paymentStatus(UPDATED_PAYMENT_STATUS)
            .paymentId(UPDATED_PAYMENT_ID)
            .checkoutId(UPDATED_CHECKOUT_ID)
            .rejectReason(UPDATED_REJECT_REASON);
        return order;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(Order.class).block();
        } catch (Exception e) {
            // It can fail, if other entities are still referring this - it will be removed later.
        }
    }

    @BeforeEach
    public void initTest() {
        order = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedOrder != null) {
            orderRepository.delete(insertedOrder).block();
            insertedOrder = null;
        }
        deleteEntities(em);
    }

    @Test
    void createOrder() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Order
        var returnedOrder = webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(order))
            .exchange()
            .expectStatus()
            .isCreated()
            .expectBody(Order.class)
            .returnResult()
            .getResponseBody();

        // Validate the Order in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertOrderUpdatableFieldsEquals(returnedOrder, getPersistedOrder(returnedOrder));

        insertedOrder = returnedOrder;
    }

    @Test
    void createOrderWithExistingId() throws Exception {
        // Create the Order with an existing ID
        order.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(order))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Order in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void getAllOrders() {
        // Initialize the database
        insertedOrder = orderRepository.save(order).block();

        // Get all the orderList
        webTestClient
            .get()
            .uri(ENTITY_API_URL + "?sort=id,desc")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.[*].id")
            .value(hasItem(order.getId().intValue()))
            .jsonPath("$.[*].email")
            .value(hasItem(DEFAULT_EMAIL))
            .jsonPath("$.[*].shippingAddressId")
            .value(hasItem(DEFAULT_SHIPPING_ADDRESS_ID))
            .jsonPath("$.[*].billingAddressId")
            .value(hasItem(DEFAULT_BILLING_ADDRESS_ID))
            .jsonPath("$.[*].note")
            .value(hasItem(DEFAULT_NOTE))
            .jsonPath("$.[*].tax")
            .value(hasItem(DEFAULT_TAX.doubleValue()))
            .jsonPath("$.[*].discount")
            .value(hasItem(DEFAULT_DISCOUNT.doubleValue()))
            .jsonPath("$.[*].numberItem")
            .value(hasItem(DEFAULT_NUMBER_ITEM))
            .jsonPath("$.[*].couponCode")
            .value(hasItem(DEFAULT_COUPON_CODE))
            .jsonPath("$.[*].totalPrice")
            .value(hasItem(sameNumber(DEFAULT_TOTAL_PRICE)))
            .jsonPath("$.[*].deliveryFee")
            .value(hasItem(sameNumber(DEFAULT_DELIVERY_FEE)))
            .jsonPath("$.[*].orderStatus")
            .value(hasItem(DEFAULT_ORDER_STATUS.toString()))
            .jsonPath("$.[*].deliveryMethod")
            .value(hasItem(DEFAULT_DELIVERY_METHOD.toString()))
            .jsonPath("$.[*].deliveryStatus")
            .value(hasItem(DEFAULT_DELIVERY_STATUS.toString()))
            .jsonPath("$.[*].paymentStatus")
            .value(hasItem(DEFAULT_PAYMENT_STATUS.toString()))
            .jsonPath("$.[*].paymentId")
            .value(hasItem(DEFAULT_PAYMENT_ID.intValue()))
            .jsonPath("$.[*].checkoutId")
            .value(hasItem(DEFAULT_CHECKOUT_ID))
            .jsonPath("$.[*].rejectReason")
            .value(hasItem(DEFAULT_REJECT_REASON));
    }

    @Test
    void getOrder() {
        // Initialize the database
        insertedOrder = orderRepository.save(order).block();

        // Get the order
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, order.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(order.getId().intValue()))
            .jsonPath("$.email")
            .value(is(DEFAULT_EMAIL))
            .jsonPath("$.shippingAddressId")
            .value(is(DEFAULT_SHIPPING_ADDRESS_ID))
            .jsonPath("$.billingAddressId")
            .value(is(DEFAULT_BILLING_ADDRESS_ID))
            .jsonPath("$.note")
            .value(is(DEFAULT_NOTE))
            .jsonPath("$.tax")
            .value(is(DEFAULT_TAX.doubleValue()))
            .jsonPath("$.discount")
            .value(is(DEFAULT_DISCOUNT.doubleValue()))
            .jsonPath("$.numberItem")
            .value(is(DEFAULT_NUMBER_ITEM))
            .jsonPath("$.couponCode")
            .value(is(DEFAULT_COUPON_CODE))
            .jsonPath("$.totalPrice")
            .value(is(sameNumber(DEFAULT_TOTAL_PRICE)))
            .jsonPath("$.deliveryFee")
            .value(is(sameNumber(DEFAULT_DELIVERY_FEE)))
            .jsonPath("$.orderStatus")
            .value(is(DEFAULT_ORDER_STATUS.toString()))
            .jsonPath("$.deliveryMethod")
            .value(is(DEFAULT_DELIVERY_METHOD.toString()))
            .jsonPath("$.deliveryStatus")
            .value(is(DEFAULT_DELIVERY_STATUS.toString()))
            .jsonPath("$.paymentStatus")
            .value(is(DEFAULT_PAYMENT_STATUS.toString()))
            .jsonPath("$.paymentId")
            .value(is(DEFAULT_PAYMENT_ID.intValue()))
            .jsonPath("$.checkoutId")
            .value(is(DEFAULT_CHECKOUT_ID))
            .jsonPath("$.rejectReason")
            .value(is(DEFAULT_REJECT_REASON));
    }

    @Test
    void getNonExistingOrder() {
        // Get the order
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_PROBLEM_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingOrder() throws Exception {
        // Initialize the database
        insertedOrder = orderRepository.save(order).block();

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the order
        Order updatedOrder = orderRepository.findById(order.getId()).block();
        updatedOrder
            .email(UPDATED_EMAIL)
            .shippingAddressId(UPDATED_SHIPPING_ADDRESS_ID)
            .billingAddressId(UPDATED_BILLING_ADDRESS_ID)
            .note(UPDATED_NOTE)
            .tax(UPDATED_TAX)
            .discount(UPDATED_DISCOUNT)
            .numberItem(UPDATED_NUMBER_ITEM)
            .couponCode(UPDATED_COUPON_CODE)
            .totalPrice(UPDATED_TOTAL_PRICE)
            .deliveryFee(UPDATED_DELIVERY_FEE)
            .orderStatus(UPDATED_ORDER_STATUS)
            .deliveryMethod(UPDATED_DELIVERY_METHOD)
            .deliveryStatus(UPDATED_DELIVERY_STATUS)
            .paymentStatus(UPDATED_PAYMENT_STATUS)
            .paymentId(UPDATED_PAYMENT_ID)
            .checkoutId(UPDATED_CHECKOUT_ID)
            .rejectReason(UPDATED_REJECT_REASON);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, updatedOrder.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(updatedOrder))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Order in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedOrderToMatchAllProperties(updatedOrder);
    }

    @Test
    void putNonExistingOrder() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        order.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, order.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(order))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Order in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchOrder() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        order.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, longCount.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(order))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Order in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamOrder() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        order.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(order))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Order in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateOrderWithPatch() throws Exception {
        // Initialize the database
        insertedOrder = orderRepository.save(order).block();

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the order using partial update
        Order partialUpdatedOrder = new Order();
        partialUpdatedOrder.setId(order.getId());

        partialUpdatedOrder
            .email(UPDATED_EMAIL)
            .note(UPDATED_NOTE)
            .tax(UPDATED_TAX)
            .discount(UPDATED_DISCOUNT)
            .totalPrice(UPDATED_TOTAL_PRICE)
            .orderStatus(UPDATED_ORDER_STATUS)
            .deliveryStatus(UPDATED_DELIVERY_STATUS)
            .paymentId(UPDATED_PAYMENT_ID);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedOrder.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(partialUpdatedOrder))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Order in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOrderUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedOrder, order), getPersistedOrder(order));
    }

    @Test
    void fullUpdateOrderWithPatch() throws Exception {
        // Initialize the database
        insertedOrder = orderRepository.save(order).block();

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the order using partial update
        Order partialUpdatedOrder = new Order();
        partialUpdatedOrder.setId(order.getId());

        partialUpdatedOrder
            .email(UPDATED_EMAIL)
            .shippingAddressId(UPDATED_SHIPPING_ADDRESS_ID)
            .billingAddressId(UPDATED_BILLING_ADDRESS_ID)
            .note(UPDATED_NOTE)
            .tax(UPDATED_TAX)
            .discount(UPDATED_DISCOUNT)
            .numberItem(UPDATED_NUMBER_ITEM)
            .couponCode(UPDATED_COUPON_CODE)
            .totalPrice(UPDATED_TOTAL_PRICE)
            .deliveryFee(UPDATED_DELIVERY_FEE)
            .orderStatus(UPDATED_ORDER_STATUS)
            .deliveryMethod(UPDATED_DELIVERY_METHOD)
            .deliveryStatus(UPDATED_DELIVERY_STATUS)
            .paymentStatus(UPDATED_PAYMENT_STATUS)
            .paymentId(UPDATED_PAYMENT_ID)
            .checkoutId(UPDATED_CHECKOUT_ID)
            .rejectReason(UPDATED_REJECT_REASON);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedOrder.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(partialUpdatedOrder))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Order in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOrderUpdatableFieldsEquals(partialUpdatedOrder, getPersistedOrder(partialUpdatedOrder));
    }

    @Test
    void patchNonExistingOrder() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        order.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, order.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(order))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Order in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchOrder() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        order.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, longCount.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(order))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Order in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamOrder() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        order.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(order))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Order in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteOrder() {
        // Initialize the database
        insertedOrder = orderRepository.save(order).block();

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the order
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, order.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return orderRepository.count().block();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected Order getPersistedOrder(Order order) {
        return orderRepository.findById(order.getId()).block();
    }

    protected void assertPersistedOrderToMatchAllProperties(Order expectedOrder) {
        // Test fails because reactive api returns an empty object instead of null
        // assertOrderAllPropertiesEquals(expectedOrder, getPersistedOrder(expectedOrder));
        assertOrderUpdatableFieldsEquals(expectedOrder, getPersistedOrder(expectedOrder));
    }

    protected void assertPersistedOrderToMatchUpdatableProperties(Order expectedOrder) {
        // Test fails because reactive api returns an empty object instead of null
        // assertOrderAllUpdatablePropertiesEquals(expectedOrder, getPersistedOrder(expectedOrder));
        assertOrderUpdatableFieldsEquals(expectedOrder, getPersistedOrder(expectedOrder));
    }
}
