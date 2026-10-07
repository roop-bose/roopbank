package com.rooptech.bankingapp.loan.calculator;
import java.math.BigDecimal;
import java.math.RoundingMode;
public class LoanPaymentCalculator {
    public static BigDecimal calculateMonthlyInterestAmount(
            BigDecimal outstandingAmount,
            BigDecimal annualInterestRate
    ) {

        return outstandingAmount
                .multiply(annualInterestRate)
                .divide(BigDecimal.valueOf(1200), 2, RoundingMode.HALF_UP);
    }
    public static BigDecimal MonthlyInterestRate(BigDecimal annualInterestRate){
        return  annualInterestRate
                .divide(BigDecimal.valueOf(1200), 2, RoundingMode.HALF_UP);
    }

//    public static BigDecimal calculatePrincipal(
//            BigDecimal emi,
//            BigDecimal interestAmount
//    ) {
//
//        return emi.subtract(interestAmount);
//    }

    public static BigDecimal calculateOutstanding(
            BigDecimal outstandingAmount,
            BigDecimal principalAmount
    ) {

        return outstandingAmount
                .subtract(principalAmount)
                .max(BigDecimal.ZERO)
                .setScale(2, RoundingMode.HALF_UP);
    }
}
