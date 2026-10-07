package com.rooptech.bankingapp.loan.controller;
import com.rooptech.bankingapp.common.ErrorResponseDto;
import com.rooptech.bankingapp.common.ResponseDto;
import com.rooptech.bankingapp.loan.constant.LoanType;
import com.rooptech.bankingapp.loan.dto.LoanRequestDto;
import com.rooptech.bankingapp.loan.dto.LoanResponseDto;
import com.rooptech.bankingapp.loan.service.LoanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
@Tag(
        name = "Loans",
        description = "APIs for creating, retrieving and deleting customer loans"
)
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/loans/api",produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
@SecurityRequirement(name = "bearerAuth")
public class LoanController {
    private final LoanService loanService;
    @Operation(
            summary = "Create a new loan",
            description = """
                Creates a new loan for an existing bank account.
                The loan type must have an active loan product.
                The loan amount and tenure must not exceed the limits
                configured for the selected loan product.
                The EMI, interest rate, loan status and issue date
                are calculated and assigned by the system.
                """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Loan created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples = @ExampleObject(
                                    name = "Success Response",
                                    value = """
                                        {
                                          "statusCode": "CREATED",
                                          "statusMessage": "loan created successfully"
                                        }
                                        """
                            )
                    )
            ),

            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid loan request data",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Validation Error",
                                    value = """
                                        {
                                          "accountId": "Account ID must be greater than zero",
                                          "loanAmount": "Loan amount must be greater than zero"
                                        }
                                        """
                            )
                    )
            ),

            @ApiResponse(
                    responseCode = "404",
                    description = "Account or active loan product not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class),
                            examples = @ExampleObject(
                                    name = "Resource Not Found",
                                    value = """
                                        {
                                          "apiPath": "/loans/api",
                                          "errorTime": "2026-09-29T13:30:00",
                                          "errorCode": "404 NOT_FOUND",
                                          "errorMessage": "Account not found"
                                        }
                                        """
                            )
                    )
            ),

            @ApiResponse(
                    responseCode = "409",
                    description = "Loan of the selected type already exists for the account",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class),
                            examples = @ExampleObject(
                                    name = "Loan Already Exists",
                                    value = """
                                        {
                                          "apiPath": "/loans/api",
                                          "errorTime": "2026-09-29T13:30:00",
                                          "errorCode": "409 CONFLICT",
                                          "errorMessage": "Loan already exists with account 1001 and loan type PERSONAL"
                                        }
                                        """
                            )
                    )
            ),

            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error or loan amount/tenure exceeds configured product limits",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class),
                            examples = @ExampleObject(
                                    name = "Internal Server Error",
                                    value = """
                                        {
                                          "apiPath": "/loans/api",
                                          "errorTime": "2026-09-29T13:30:00",
                                          "errorCode": "500 INTERNAL_SERVER_ERROR",
                                          "errorMessage": "Unexpected internal server error"
                                        }
                                        """
                            )
                    )
            )
    })
    @PostMapping
    public ResponseEntity<ResponseDto> createLoan(
            @Valid
            @RequestBody LoanRequestDto loanRequestDto
            ){
        loanService.createLoan(loanRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(HttpStatus.CREATED,
                        "loan created successfully"));
    }

    @Operation(
            summary = "Get loan details",
            description = """
                Retrieves loan details for a specific bank account
                and loan type.
                """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Loan details retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = LoanResponseDto.class),
                            examples = @ExampleObject(
                                    name = "Loan Details",
                                    value = """
                                        {
                                          "loanId": 501,
                                          "loanAmount": 500000.00,
                                          "loanIssuedAt": "2026-09-29",
                                          "outstandingAmount": 500000.00,
                                          "tenureMonths": 60,
                                          "loanStatus": "ACTIVE",
                                          "loanType": "PERSONAL",
                                          "interestRate": 10.50,
                                          "emi": 10747.00,
                                          "accountId": 1001
                                        }
                                        """
                            )
                    )
            ),

            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid account ID or loan type",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Validation Error",
                                    value = """
                                        {
                                          "accountId": "Account ID must be greater than zero"
                                        }
                                        """
                            )
                    )
            ),

            @ApiResponse(
                    responseCode = "404",
                    description = "Loan not found for the specified account and loan type",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class),
                            examples = @ExampleObject(
                                    name = "Loan Not Found",
                                    value = """
                                        {
                                          "apiPath": "/loans/api/account/1001/type/PERSONAL",
                                          "errorTime": "2026-09-29T13:30:00",
                                          "errorCode": "404 NOT_FOUND",
                                          "errorMessage": "Loan not found"
                                        }
                                        """
                            )
                    )
            ),

            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class),
                            examples = @ExampleObject(
                                    name = "Internal Server Error",
                                    value = """
                                        {
                                          "apiPath": "/loans/api/account/1001/type/PERSONAL",
                                          "errorTime": "2026-09-29T13:30:00",
                                          "errorCode": "500 INTERNAL_SERVER_ERROR",
                                          "errorMessage": "Unexpected internal server error"
                                        }
                                        """
                            )
                    )
            )
    })
    @GetMapping("account/{accountId}/type/{loanType}")
    public ResponseEntity<LoanResponseDto> getLoan(
            @Positive(message = "Account ID must be greater than zero")
            @PathVariable  Long accountId,
            @PathVariable LoanType loanType
            ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(loanService.getLoan(accountId,loanType));
    }

    @Operation(
            summary = "Delete a loan",
            description = """
                Deletes the specified loan associated with a bank account
                using the account ID and loan type.
                """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Loan deleted successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples = @ExampleObject(
                                    name = "Success Response",
                                    value = """
                                        {
                                          "statusCode": "OK",
                                          "statusMessage": "loan deleted successfully"
                                        }
                                        """
                            )
                    )
            ),

            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid account ID or loan type",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Validation Error",
                                    value = """
                                        {
                                          "accountId": "Account ID must be greater than zero"
                                        }
                                        """
                            )
                    )
            ),

            @ApiResponse(
                    responseCode = "404",
                    description = "Loan not found for the specified account and loan type",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class),
                            examples = @ExampleObject(
                                    name = "Loan Not Found",
                                    value = """
                                        {
                                          "apiPath": "/loans/api/account/1001/type/PERSONAL",
                                          "errorTime": "2026-09-29T13:30:00",
                                          "errorCode": "404 NOT_FOUND",
                                          "errorMessage": "Loan not found"
                                        }
                                        """
                            )
                    )
            ),

            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class),
                            examples = @ExampleObject(
                                    name = "Internal Server Error",
                                    value = """
                                        {
                                          "apiPath": "/loans/api/account/1001/type/PERSONAL",
                                          "errorTime": "2026-09-29T13:30:00",
                                          "errorCode": "500 INTERNAL_SERVER_ERROR",
                                          "errorMessage": "Unexpected internal server error"
                                        }
                                        """
                            )
                    )
            )
    })
    @DeleteMapping("account/{accountId}/type/{loanType}")
    public ResponseEntity<ResponseDto> deleteLoan(
            @Positive(message = "Account ID must be greater than zero")
            @PathVariable  Long accountId,
            @PathVariable LoanType loanType
    ){
        loanService.deleteLoan(accountId,loanType);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDto(HttpStatus.OK,
                        "loan deleted successfully"));
    }

}
