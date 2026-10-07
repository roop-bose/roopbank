package com.rooptech.bankingapp.loan.calculator;
import java.math.BigDecimal;
import java.math.RoundingMode;
public class LoanCalculator {

    public static BigDecimal emiCalculator(
            BigDecimal loanAmount,
            BigDecimal annualInterestRate,
            Integer tenureMonth
    ){
        BigDecimal monthlyRate= annualInterestRate.divide(BigDecimal.valueOf(12) ,
                10, RoundingMode.HALF_UP)
                .divide(BigDecimal.valueOf(100), 10,RoundingMode.HALF_UP);
        BigDecimal onePlusRate = BigDecimal.ONE.add(monthlyRate);
        BigDecimal power = onePlusRate.pow(tenureMonth);
        BigDecimal numerator = loanAmount
                .multiply(monthlyRate)
                .multiply(power);
        BigDecimal denominator =power.subtract(BigDecimal.ONE);
        return numerator.divide(
                denominator ,2, RoundingMode.HALF_UP
        );
    }

}
