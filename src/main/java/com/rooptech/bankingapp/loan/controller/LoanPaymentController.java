package com.rooptech.bankingapp.loan.controller;
import com.rooptech.bankingapp.common.ErrorResponseDto;
import com.rooptech.bankingapp.loan.dto.LoanPaymentRequestDto;
import com.rooptech.bankingapp.loan.dto.LoanPaymentResponseDto;
import com.rooptech.bankingapp.loan.service.LoanPaymentService;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/loans/payments")
@RequiredArgsConstructor
@Tag(
        name = "Loan Payments",
        description = "APIs for making loan payments and retrieving loan payment history"
)
@SecurityRequirement(name = "bearerAuth")
public class LoanPaymentController {
//    POST http://localhost:8080/api/loans/payments
    private final LoanPaymentService loanPaymentService;
    @Operation(
            summary = "Make a loan payment",
            description = """
                Makes a loan payment using the bank account associated
                with the loan.

                The payment amount is based on the loan EMI.
                The system calculates the interest and principal portions,
                deducts the payment amount from the linked account,
                updates the outstanding loan amount and records the payment.

                If the payment fully settles the outstanding loan amount,
                the loan status is changed to CLOSED.
                """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Loan payment processed successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = LoanPaymentResponseDto.class),
                            examples = @ExampleObject(
                                    name = "Successful Loan Payment",
                                    value = """
                                        {
                                          "paymentId": 1001,
                                          "paymentAmount": 10747.00,
                                          "interestRate": 10.50,
                                          "principalAmount": 6372.00,
                                          "interestAmount": 4375.00,
                                          "paymentDate": "2026-09-29",
                                          "outstandingAmount": 493628.00,
                                          "paymentStatus": "SUCCESS",
                                          "loanId": 501
                                        }
                                        """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid loan payment request",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Validation Error",
                                    value = """
                                        {
                                          "loanId": "Loan ID must be greater than zero"
                                        }
                                        """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Loan not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class),
                            examples = @ExampleObject(
                                    name = "Loan Not Found",
                                    value = """
                                        {
                                          "apiPath": "/api/loans/payments",
                                          "errorTime": "2026-09-29T16:30:00",
                                          "errorCode": "404 NOT_FOUND",
                                          "errorMessage": "Loan not found"
                                        }
                                        """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Payment could not be processed",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class),
                            examples = @ExampleObject(
                                    name = "Payment Processing Error",
                                    value = """
                                        {
                                          "apiPath": "/api/loans/payments",
                                          "errorTime": "2026-09-29T16:30:00",
                                          "errorCode": "500 INTERNAL_SERVER_ERROR",
                                          "errorMessage": "Insufficient account balance"
                                        }
                                        """
                            )
                    )
            )
    })
    @PostMapping
    public ResponseEntity<LoanPaymentResponseDto> makePayment(
            @Valid @RequestBody LoanPaymentRequestDto requestDto
    ) {

        LoanPaymentResponseDto response =
                loanPaymentService.makePayment(requestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
    @Operation(
            summary = "Get loan payment history",
            description = """
                Retrieves the payment history of a specific loan.
                Payments are returned in descending order of payment date,
                with the most recent payment appearing first.
                """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Loan payment history retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = LoanPaymentResponseDto.class
                            ),
                            examples = @ExampleObject(
                                    name = "Payment History",
                                    value = """
                                        [
                                          {
                                            "paymentId": 1002,
                                            "paymentAmount": 10747.00,
                                            "interestRate": 10.50,
                                            "principalAmount": 6500.00,
                                            "interestAmount": 4247.00,
                                            "paymentDate": "2026-09-29",
                                            "outstandingAmount": 487128.00,
                                            "paymentStatus": "SUCCESS",
                                            "loanId": 501
                                          },
                                          {
                                            "paymentId": 1001,
                                            "paymentAmount": 10747.00,
                                            "interestRate": 10.50,
                                            "principalAmount": 6372.00,
                                            "interestAmount": 4375.00,
                                            "paymentDate": "2026-08-29",
                                            "outstandingAmount": 493628.00,
                                            "paymentStatus": "SUCCESS",
                                            "loanId": 501
                                          }
                                        ]
                                        """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Loan ID must be greater than zero",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Validation Error",
                                    value = """
                                        {
                                          "loanId": "Loan ID must be greater than zero"
                                        }
                                        """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Loan not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class),
                            examples = @ExampleObject(
                                    name = "Loan Not Found",
                                    value = """
                                        {
                                          "apiPath": "/api/loans/payments/loan/501",
                                          "errorTime": "2026-09-29T16:30:00",
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
                                          "apiPath": "/api/loans/payments/loan/501",
                                          "errorTime": "2026-09-29T16:30:00",
                                          "errorCode": "500 INTERNAL_SERVER_ERROR",
                                          "errorMessage": "Unexpected internal server error"
                                        }
                                        """
                            )
                    )
            )
    })
    @GetMapping("/loan/{loanId}")
    public ResponseEntity<List<LoanPaymentResponseDto>> getPaymentHistory(
            @PathVariable
            @Positive(message = "Loan ID must be greater than zero")
            Long loanId
    ) {

        List<LoanPaymentResponseDto> response =
                loanPaymentService.getPaymentHistory(loanId);

        return ResponseEntity.ok(response);
    }
}