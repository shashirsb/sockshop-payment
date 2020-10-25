/*
 * Copyright (c) 2020 Oracle and/or its affiliates.
 *
 * Licensed under the Universal Permissive License v 1.0 as shown at
 * http://oss.oracle.com/licenses/upl.
 */

package io.helidon.examples.sockshop.payment;

import io.helidon.microprofile.grpc.core.GrpcMarshaller;
import io.helidon.microprofile.grpc.core.Grpc;
import io.helidon.microprofile.grpc.core.Unary;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.metrics.annotation.Metered;

import java.util.Collection;

/**
 * Implementation of the Payment Service gRPC API.
 */
@ApplicationScoped
@Grpc
@GrpcMarshaller("jsonb")
public class PaymentGrpc {
    /**
     * Payment repository to use.
     */
    @Inject
    private PaymentRepository payments;

    /**
     * Payment service to use.
     */
    @Inject
    private PaymentService paymentService;

    @Unary
    public Collection<? extends Authorization> getOrderAuthorizations(String orderId) {
        return payments.findAuthorizationsByOrder(orderId);
    }

    @Unary
    @Metered
    public Authorization authorize(PaymentRequest paymentRequest) {
        String firstName = paymentRequest.getCustomer().getFirstName();
        String lastName  = paymentRequest.getCustomer().getLastName();

        Authorization auth = paymentService.authorize(
                paymentRequest.getOrderId(),
                firstName,
                lastName,
                paymentRequest.getCard(),
                paymentRequest.getAddress(),
                paymentRequest.getAmount());

        payments.saveAuthorization(auth);

        return auth;
    }
}
