package io.digisic.bank.test.junit.model;
import io.digisic.bank.model.AccountType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
public class AccountTypeTest {
    /**
     * Verifies REQ-4
     */
    @Test
    void testAccountMustHaveRequiredFields() {
        AccountType account = new AccountType();
        account.setId(1L);
        account.setName("Savings Account");
        account.setCode("SA01");
        account.setCategory("Savings");
        account.setInterestRate(0.02);
        account.setMinDeposit(BigDecimal.valueOf(10000));
        account.setOverdraftLimit(BigDecimal.valueOf(500));
        account.setOverdraftFee(BigDecimal.valueOf(25));

        // Construct the expected output based on the known values
        String expectedOutput = "\n\nAccount Type ***********************" +
                "\nId:\t\t\t" + account.getId() +
                "\nName:\t\t" + account.getName() +
                "\nCode:\t\t\t" + account.getCode() +
                "\nCategory:\t\t" + account.getCategory() +
                "\nInterest Rate:\t" + account.getInterestRate() +
                "\nMinimum Deposit:\t" + account.getMinDeposit() +
                "\nOverdraft Limit:\t" + account.getOverdraftLimit() +
                "\nOverdraft Fee:\t" + account.getOverdraftFee();

        // Act: Call the toString() method
        String actualOutput = account.toString();

        // Assert: Verify that the actual output matches the expected output
        assertEquals(expectedOutput, actualOutput);
    }
}
