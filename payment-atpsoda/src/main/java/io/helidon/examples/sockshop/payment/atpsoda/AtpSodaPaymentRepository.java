/*
 * Copyright (c) 2020 Oracle and/or its affiliates.
 *
 * Licensed under the Universal Permissive License v 1.0 as shown at
 * http://oss.oracle.com/licenses/upl.
 */

package io.helidon.examples.sockshop.payment.atpsoda;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;

import javax.annotation.Priority;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;
import javax.inject.Inject;

import io.helidon.examples.sockshop.payment.Authorization;
import io.helidon.examples.sockshop.payment.PaymentRepository;

import com.mongodb.client.MongoCollection;

import org.eclipse.microprofile.opentracing.Traced;

import io.helidon.examples.sockshop.payment.atpsoda.AtpSodaProducers;

import static javax.interceptor.Interceptor.Priority.APPLICATION;

/**
 * An implementation of {@link io.helidon.examples.sockshop.payment.PaymentRepository}
 * that that uses MongoDB as a backend data store.
 */
@ApplicationScoped
@Alternative
@Priority(APPLICATION)
@Traced
public class AtpSodaPaymentRepository implements PaymentRepository {
    public static AtpSodaProducers asp = new AtpSodaProducers();
    public static OracleDatabase db = asp.dbConnect();

    @Inject
    AtpSodaPaymentRepository() {
        try {
            String UserResponse = "something";//createData();
            System.out.println(UserResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveAuthorization(Authorization auth) {
        //payments.insertOne(auth);
    }

    @Override
    public Collection<? extends Authorization> findAuthorizationsByOrder(String orderId) {
        ArrayList<Authorization> results = new ArrayList<>();

        // payments.find(eq("orderId", orderId))
        //         .forEach((Consumer<? super Authorization>) results::add);

        return results;
    }

    public String createData() {
        // Create a collection with the name "MyJSONCollection".
        // This creates a database table, also named "MyJSONCollection", to store the collection.

        try {

            // Create a collection with the name "MyJSONCollection".
            // This creates a database table, also named "MyJSONCollection", to store the collection.\

            OracleCollection col = this.db.admin().createCollection("payments");

            col.admin().truncate();


        } catch (OracleException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "successfully created payments collection !!!";
    }
}
