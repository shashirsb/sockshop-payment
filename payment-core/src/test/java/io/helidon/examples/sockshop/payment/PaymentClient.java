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

import java.util.Collection;

@Grpc(name = "PaymentGrpc")
@GrpcMarshaller("jsonb")
public interface PaymentClient {
    @Unary
    Authorization authorize(PaymentRequest request);

    @Unary
    Collection<? extends Authorization> getOrderAuthorizations(String orderId);
}
