package com.rooptech.bankingapp.transaction.controller;
import com.rooptech.bankingapp.common.ErrorResponseDto;
import com.rooptech.bankingapp.common.ResponseDto;
import com.rooptech.bankingapp.transaction.constant.TransactionType;
import com.rooptech.bankingapp.transaction.dto.TransactionRequestDto;
import com.rooptech.bankingapp.transaction.dto.TransactionResponseDto;
import com.rooptech.bankingapp.transaction.service.TransactionService;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping( path = "/transactions/api")
@Tag(
        name = "Transactions",
        description = "APIs for depositing, withdrawing and retrieving account transactions"
)
@SecurityRequirement(name = "bearerAuth")
public class TransactionController {

    private final TransactionService transactionService;
    @Operation(
            summary = "Deposit money into an account",
            description = """
                Deposits the specified amount into the given bank account.
                The account balance is updated and a successful transaction
                record is created.

                After the transaction is saved, a transaction event is
                published for notification processing.
                """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Amount deposited successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples = @ExampleObject(
                                    name = "Success Response",
                                    value = """
                                        {
                                          "statusCode": "OK",
                                          "statusMessage": "Amount deposited successfully"
                                        }
                                        """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid account ID or transaction amount",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Validation Error",
                                    value = """
                                        {
                                          "amount": "amount must be positive"
                                        }
                                        """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Account not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class),
                            examples = @ExampleObject(
                                    name = "Account Not Found",
                                    value = """
                                        {
                                          "apiPath": "/transactions/api/1001/deposit",
                                          "errorTime": "2026-09-29T17:30:00",
                                          "errorCode": "404 NOT_FOUND",
                                          "errorMessage": "Account not found"
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
                                          "apiPath": "/transactions/api/1001/deposit",
                                          "errorTime": "2026-09-29T17:30:00",
                                          "errorCode": "500 INTERNAL_SERVER_ERROR",
                                          "errorMessage": "Unexpected internal server error"
                                        }
                                        """
                            )
                    )
            )
    })
    @PostMapping("/{accountId}/deposit")
    public ResponseEntity<ResponseDto> deposit(
            @Valid @RequestBody TransactionRequestDto requestDto,
            @PathVariable
            @Positive(message = "Account ID must be positive")
            Long accountId
    ) {

        transactionService.deposit(requestDto, accountId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        new ResponseDto(
                                HttpStatus.OK,
                                "Amount deposited successfully"
                        )
                );
    }
    @Operation(
            summary = "Withdraw money from an account",
            description = """
                Withdraws the specified amount from the given bank account.
                The account must have sufficient balance to complete the transaction.

                The account balance is updated and a successful withdrawal
                transaction is recorded. A transaction event is then published
                for notification processing.
                """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Amount withdrawn successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples = @ExampleObject(
                                    name = "Success Response",
                                    value = """
                                        {
                                          "statusCode": "OK",
                                          "statusMessage": "Amount withdrawn successfully"
                                        }
                                        """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid account ID or transaction amount",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Validation Error",
                                    value = """
                                        {
                                          "amount": "amount must be positive"
                                        }
                                        """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Account not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class),
                            examples = @ExampleObject(
                                    name = "Account Not Found",
                                    value = """
                                        {
                                          "apiPath": "/transactions/api/1001/withdraw",
                                          "errorTime": "2026-09-29T17:30:00",
                                          "errorCode": "404 NOT_FOUND",
                                          "errorMessage": "Account not found"
                                        }
                                        """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Withdrawal could not be processed",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class),
                            examples = @ExampleObject(
                                    name = "Insufficient Balance",
                                    value = """
                                        {
                                          "apiPath": "/transactions/api/1001/withdraw",
                                          "errorTime": "2026-09-29T17:30:00",
                                          "errorCode": "500 INTERNAL_SERVER_ERROR",
                                          "errorMessage": "Balance is insufficient"
                                        }
                                        """
                            )
                    )
            )
    })
    @PostMapping("/{accountId}/withdraw")
    public ResponseEntity<ResponseDto> withdraw(
            @Valid @RequestBody TransactionRequestDto requestDto,
            @PathVariable
            @Positive(message = "Account ID must be positive")
            Long accountId
    ) {

        transactionService.withdraw(requestDto, accountId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        new ResponseDto(
                                HttpStatus.OK,
                                "Amount withdrawn successfully"
                        )
                );
    }
    @Operation(
            summary = "Get account transaction history",
            description = """
                Retrieves paginated transaction history for a bank account.

                The transactionType parameter is optional. If it is not provided,
                all transaction types are returned. If provided, transactions
                are filtered by the specified transaction type.

                By default, transactions are returned in descending order
                of transaction date with a page size of 2.
                """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Transaction history retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = Page.class
                            ),
                            examples = @ExampleObject(
                                    name = "Transaction History",
                                    value = """
                                        {
                                          "content": [
                                            {
                                              "transactionId": 1001,
                                              "amount": 5000.00,
                                              "balanceAfter": 25000.00,
                                              "transactionDate": "2026-09-29T10:30:00",
                                              "transactionType": "DEPOSIT",
                                              "transactionStatus": "SUCCESS",
                                              "accountId": 1001
                                            },
                                            {
                                              "transactionId": 1000,
                                              "amount": 2000.00,
                                              "balanceAfter": 20000.00,
                                              "transactionDate": "2026-09-28T15:45:00",
                                              "transactionType": "WITHDRAWAL",
                                              "transactionStatus": "SUCCESS",
                                              "accountId": 1001
                                            }
                                          ],
                                          "pageable": {
                                            "pageNumber": 0,
                                            "pageSize": 2
                                          },
                                          "totalElements": 2,
                                          "totalPages": 1,
                                          "last": true,
                                          "first": true,
                                          "size": 2,
                                          "number": 0
                                        }
                                        """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid account ID or transaction type",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Validation Error",
                                    value = """
                                        {
                                          "accountId": "Account ID must be positive"
                                        }
                                        """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Account not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class),
                            examples = @ExampleObject(
                                    name = "Account Not Found",
                                    value = """
                                        {
                                          "apiPath": "/transactions/api/account/1001",
                                          "errorTime": "2026-09-29T17:30:00",
                                          "errorCode": "404 NOT_FOUND",
                                          "errorMessage": "Account not found"
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
                                          "apiPath": "/transactions/api/account/1001",
                                          "errorTime": "2026-09-29T17:30:00",
                                          "errorCode": "500 INTERNAL_SERVER_ERROR",
                                          "errorMessage": "Unexpected internal server error"
                                        }
                                        """
                            )
                    )
            )
    })
    @GetMapping("/account/{accountId}")
    public ResponseEntity<Page<TransactionResponseDto>> getAllTransaction(
            @PathVariable
            @Positive(message = "Account ID must be positive")
            Long accountId,

            @RequestParam(required = false)
            TransactionType transactionType,
            @PageableDefault(
                    size = 2,
                    sort = "transactionDate",
                    direction = Sort.Direction.DESC
            )
            Pageable pageable


    ) {

        return ResponseEntity.ok(
                transactionService.getAllTransaction(
                        accountId,
                        transactionType,
                        pageable
        ));
    }
}