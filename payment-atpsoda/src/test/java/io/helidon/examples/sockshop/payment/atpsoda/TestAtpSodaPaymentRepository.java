/*
 * Copyright (c) 2020 Oracle and/or its affiliates.
 *
 * Licensed under the Universal Permissive License v 1.0 as shown at
 * http://oss.oracle.com/licenses/upl.
 */

package io.helidon.examples.sockshop.carts.atpsoda;

import javax.annotation.Priority;
import javax.enterprise.inject.Alternative;
import javax.inject.Inject;

import io.helidon.examples.sockshop.payment.Authorization;
import io.helidon.examples.sockshop.payment.TestPaymentRepository;


import com.mongodb.client.MongoCollection;
import org.bson.BsonDocument;

import static javax.interceptor.Interceptor.Priority.APPLICATION;

@Alternative
@Priority(APPLICATION + 5)
// public class TestAtpSodaPaymentRepository extends AtpSodaPaymentRepository implements TestPaymentRepository {
//     // @Inject
//     // TestAtpSodaPaymentRepository(MongoCollection<Authorization> payments) {
//     //   //  super(payments);
//     // }

//     @Override
//     public void clear() {
//         //payments.deleteMany(new BsonDocument());
//     }
// }

public class TestAtpSodaPaymentRepository {}