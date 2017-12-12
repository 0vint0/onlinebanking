package com.vsvet.example.onlinebanking.domain;

import com.vsvet.example.onlinebanking.view.ClientStatusView;

/**
 * Defines client's statuses in the application.
 */
public enum ClientStatus {

    /**
     * Client is just created/signed-up and waiting for validation/activation.
     */
    PENDING,
    /**
     * Client is fully registered and can use the application.
     */
    ACTIVE,
    /**
     * Client is inactive - his access is not allowed for a while, due to some
     * actions from back-office.
     */
    INACTIVE,
    /**
     * Client is fully deleted from the system, it means we are doing logical delete
     * to keep history of his transactions, in future for this entries can be used another table.
     */
    DELETED;

}
