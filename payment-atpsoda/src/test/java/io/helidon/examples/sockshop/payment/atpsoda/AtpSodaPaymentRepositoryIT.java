/*
 * Copyright (c) 2020 Oracle and/or its affiliates.
 *
 * Licensed under the Universal Permissive License v 1.0 as shown at
 * http://oss.oracle.com/licenses/upl.
 */

package io.helidon.examples.sockshop.payment.atpsoda;

import io.helidon.examples.sockshop.payment.PaymentRepositoryTest;
import io.helidon.examples.sockshop.payment.TestPaymentRepository;

// import static io.helidon.examples.sockshop.payment.atpsoda.AtpSodaProducers.client;
// import static io.helidon.examples.sockshop.payment.atpsoda.AtpSodaProducers.db;
// import static io.helidon.examples.sockshop.payment.atpsoda.AtpSodaProducers.payments;

/**
 * Integration tests for {@link io.helidon.examples.sockshop.payment.mongo.MongoPaymentRepository}.
 */
class AtpSodaPaymentRepositoryIT extends PaymentRepositoryTest {
    // public TestPaymentRepository getPaymentRepository() {
    //     String host = System.getProperty("db.host","localhost");
    //     int    port = Integer.parseInt(System.getProperty("db.port","27017"));

    //     return new TestAtpSodaPaymentRepository(payments(db(client(host, port))));
    // }
}
