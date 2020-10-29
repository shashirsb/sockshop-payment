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

import static com.mongodb.client.model.Filters.eq;
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
    AtpSodaPRepository() {
        try {
            String UserResponse = createData();
            System.out.println(UserResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveAuthorization(Authorization auth) {
        //payments.insertOne(auth);
       // String orderId, LocalDateTime time, boolean authorised, String message, Err error


            try {

                OracleCollection col = this.db.admin().createCollection("payments");
                String _document = "{\"orderId\":\"" + auth.orderId.toString() + "\",\"time\":\"" + auth.time + "\",\"authorised\":\"" + auth.authorised + "\",\"message\":\"" + auth.message + "\",\"error\":\"" + auth.error + "\"}";
        
        
                // Create a JSON document.
                OracleDocument doc =
                    this.db.createDocumentFromString(_document);

                // Insert the document into a collection.
                col.insert(doc);

            } catch (OracleException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
   

    }

    @Override
    public Collection<? extends Authorization> findAuthorizationsByOrder(String orderId) {
        ArrayList<Authorization> results = new ArrayList<>();

        // payments.find(eq("orderId", orderId))
        //         .forEach((Consumer<? super Authorization>) results::add);

                org.json.simple.JSONObject _jsonObject = new JSONObject();
                org.json.simple.parser.JSONParser _parser = new JSONParser();
        
        
                try {
        
                    // Get a collection with the name "socks".
                    // This creates a database table, also named "socks", to store the collection.
                    OracleCollection col = this.db.admin().createCollection("payments");
        
                    // Find all documents in the collection.
                    OracleCursor c = null;
                    String jsonFormattedString = null;
                    try {
        
                        OracleDocument filterSpec = this.db.createDocumentFromString("{ \"orderId\" : \"" + orderId + "\"}");

                        OracleCursor c = col.find().filter(filterSpec).getCursor();
        
                        OracleDocument resultDoc;
                        while (c.hasNext()) {
                            Authorization auth = new Authorization();
        
        
                            resultDoc = c.next();
        
                            JSONParser parser = new JSONParser();
        
                            Object obj = parser.parse(resultDoc.getContentAsString());
        
                            JSONObject jsonObject = (JSONObject) obj;
        
                            //String orderId, LocalDateTime time, boolean authorised, String message, Err error
                            auth.orderId = jsonObject.get("orderId").toString();
                            auth.time = jsonObject.get("time");
                            auth.authorised = jsonObject.get("authorised");
                            auth.message = Float.parseFloat(jsonObject.get("message").toString());
                            auth.error = Integer.parseInt(jsonObject.get("error"));     
        
                            results.add(auth);
                        }
                        
        
        
                    } finally {
                        // IMPORTANT: YOU MUST CLOSE THE CURSOR TO RELEASE RESOURCES.
                        if (c != null) c.close();
                    }
        
                } catch (Exception e) {
                    e.printStackTrace();
                }
        
                System.out.println("/payments.. findAuthorizationsByOrder GET Request 200OK");
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
