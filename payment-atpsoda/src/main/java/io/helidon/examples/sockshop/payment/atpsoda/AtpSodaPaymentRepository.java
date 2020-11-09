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



import org.eclipse.microprofile.opentracing.Traced;
import io.helidon.examples.sockshop.payment.atpsoda.AtpSodaProducers;

import static javax.interceptor.Interceptor.Priority.APPLICATION;

///////////////////

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Specializes;
import javax.inject.Inject;

import org.bson.BsonDocument;
import org.bson.conversions.Bson;
import org.eclipse.microprofile.opentracing.Traced;


import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonArray;

import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;


import io.helidon.config.Config;
import io.helidon.webserver.Routing;
import io.helidon.webserver.ServerRequest;
import io.helidon.webserver.ServerResponse;
import io.helidon.webserver.Service;

import java.io.*;
import java.util.Properties;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.HashMap;
import java.util.stream.Stream;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import oracle.soda.rdbms.OracleRDBMSClient;
import oracle.soda.OracleDatabase;
import oracle.soda.OracleCursor;
import oracle.soda.OracleCollection;
import oracle.soda.OracleDocument;
import oracle.soda.OracleException;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;

import org.apache.commons.lang3.StringUtils;
import java.time.LocalDateTime;

import io.helidon.examples.sockshop.payment.Err;


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
            String UserResponse = createData();
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
                System.out.println("saveAuthorization .... 200OK");
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

                        c = col.find().filter(filterSpec).getCursor();
        
                        OracleDocument resultDoc;
                        while (c.hasNext()) {
                            Authorization auth = new Authorization();
        
        
                            resultDoc = c.next();
        
                            JSONParser parser = new JSONParser();
        
                            Object obj = parser.parse(resultDoc.getContentAsString());
        
                            JSONObject jsonObject = (JSONObject) obj;

               
        
                            //String orderId, LocalDateTime time, boolean authorised, String message, Err error
                            auth.orderId = jsonObject.get("orderId").toString();

                           // String str = "2020-10-29T14:17:02.216+00:00"; 
                           String strDatewithTime = jsonObject.get("time").toString();
                           LocalDateTime aLDT = LocalDateTime.parse(strDatewithTime);


                            auth.time = aLDT;
                            auth.authorised = Boolean.parseBoolean(jsonObject.get("authorised").toString());
                            auth.message = jsonObject.get("message").toString();
                            auth.error = new Err(jsonObject.get("error").toString());     
        
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


        try {

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
