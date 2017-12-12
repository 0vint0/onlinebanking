package com.vsvet.example.onlinebanking.controller;

import com.vsvet.example.onlinebanking.api.ClientApi;
import com.vsvet.example.onlinebanking.view.CardAccountView;
import com.vsvet.example.onlinebanking.view.CardTransactionView;
import com.vsvet.example.onlinebanking.view.ClientView;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = "/clients")
public class ClientController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientController.class);

    private final ClientApi clientApi;

    private final RestResponses restResponses = RestResponses.json();

    public ClientController(ClientApi clientApi) {
        this.clientApi = Objects.requireNonNull(clientApi);
    }

    @ApiOperation(value = "Client signUp profile", notes = "Client signUp profile")
    @ApiResponses(value = {
            @ApiResponse(code = 412, message = "Validation errors!"),
            @ApiResponse(code = 500, message = "internal server error!"),
            @ApiResponse(code = 201, message = "Success!")})
    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<Void> signUp(@RequestBody ClientView clientView) {
        LOGGER.info("Called signUp service for client '{}'!", clientView.getEmail());
        clientApi.signUp(clientView);
        return restResponses.created();
    }

    @ApiOperation(value = "Get client details", notes = "Get client details")
    @ApiResponses(value = {
            @ApiResponse(code = 412, message = "Validation errors!"),
            @ApiResponse(code = 500, message = "Internal server error!"),
            @ApiResponse(code = 200, message = "Success!")})
    @RequestMapping(value = "/{email}", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<ClientView> getDetails(@PathVariable("email") String email) {
        LOGGER.info("Called getDetails for client '{}'!", email);
        return clientApi.findClient(email).map(restResponses::ok).orElse(restResponses.ok(null));
    }

    @ApiOperation(value = "Get client's default card account.", notes = "Get client's default card account.")
    @ApiResponses(value = {
            @ApiResponse(code = 412, message = "Validation errors!"),
            @ApiResponse(code = 404, message = "No default card was found!"),
            @ApiResponse(code = 500, message = "internal server error!"),
            @ApiResponse(code = 200, message = "Success!")})
    @RequestMapping(value = "/{email}/cards/default", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<CardAccountView> getDefaultAccount(@PathVariable("email") String email) {
        LOGGER.info("Called defaultAccount for client '{}'!", email);
        return restResponses.ok(clientApi.getDefaultCardAccount(email));
    }

    @ApiOperation(value = "Client supply default card with resources this is used for both operations supply/withdraw", notes = "Client supply default card with resources this is used for both operations supply/withdraw")
    @ApiResponses(value = {
            @ApiResponse(code = 412, message = "Validation errors!"),
            @ApiResponse(code = 500, message = "internal server error!"),
            @ApiResponse(code = 201, message = "Success!")})
    @RequestMapping(value = "/{email}/cards/default/transactions", method = RequestMethod.POST, consumes = "application/json")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<Void> createTransactionOnDefaultAccount(@PathVariable("email") String email, @RequestBody CardTransactionView view) {
        LOGGER.info("Called createTransactionOnDefaultAccount service for client '{}'!", email);
        view.setClientEmail(email);
        clientApi.createTransactionOnDefaultAccount(view);
        return restResponses.created();
    }

    @ApiOperation(value = "Get all client's transactions", notes = "Get all client's transactions")
    @ApiResponses(value = {
            @ApiResponse(code = 412, message = "Validation errors!"),
            @ApiResponse(code = 500, message = "Internal server error!"),
            @ApiResponse(code = 200, message = "Success!")})
    @RequestMapping(value = "/{email}/transactions", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<List<CardTransactionView>> getTransactions(@PathVariable("email") String email) {
        LOGGER.info("Called getTransactions for client '{}'!", email);
        return restResponses.ok(clientApi.findAllTransactions(email));
    }

    @ApiOperation(value = "Get all client's transactions for given card number", notes = "Get all client's transactions for given card number")
    @ApiResponses(value = {
            @ApiResponse(code = 412, message = "Validation errors!"),
            @ApiResponse(code = 500, message = "Internal server error!"),
            @ApiResponse(code = 200, message = "Success!")})
    @RequestMapping(value = "/{email}/cards/{cardNumber}/transactions", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<List<CardTransactionView>> getTransactionsByCardNumber(@PathVariable("email") String email, @PathVariable("cardNumber") String cardNumber) {
        LOGGER.info("Called getTransactionsByCardNumber for client '{}' and cardNumber {}!", email, cardNumber);
        return restResponses.ok(clientApi.findAllTransactionsByEmailAndCardNumber(email, cardNumber));
    }
}
