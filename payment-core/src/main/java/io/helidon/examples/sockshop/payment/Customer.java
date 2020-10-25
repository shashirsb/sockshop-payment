/*
 * Copyright (c) 2020 Oracle and/or its affiliates.
 *
 * Licensed under the Universal Permissive License v 1.0 as shown at
 * http://oss.oracle.com/licenses/upl.
 */

package io.helidon.examples.sockshop.payment;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 * Customer information.
 */
@Data
@NoArgsConstructor
@Schema(description = "Customer information")
public class Customer implements Serializable {
    /**
     * First name.
     */
    @Schema(description = "First name")
    private String firstName;

    /**
     * Last name.
     */
    @Schema(description = "Last name")
    private String lastName;

    @Builder
    Customer(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
