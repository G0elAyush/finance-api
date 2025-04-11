package com.myapp.restapi.mfm.txn;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myapp.restapi.user.User;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.persistence.EntityExistsException;
import jakarta.validation.Valid;

@Validated
@RequestMapping(value = "/txn", produces = { "application/json" })
public interface TransactionApi {
	
	
	@Operation(summary = "Add transaction", description = "This api is used to add new transaction to the ledger", tags = {"Transaction Resource" })
	@ApiResponses(value = {
	@ApiResponse(responseCode = "201", description = "Successful Creation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Transaction.class)))})
	//@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))),
	//@ApiResponse(responseCode = "409", description = "Duplicate user name", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))),
	//@ApiResponse(responseCode = "500"415, description = "Internal Service Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))) })
	@PostMapping(consumes = { "application/json"})
	ResponseEntity<User> createUser(@Valid @RequestBody User body) throws EntityExistsException;


}
