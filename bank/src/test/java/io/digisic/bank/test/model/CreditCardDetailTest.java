package io.digisic.bank.test.junit.model;

import io.digisic.bank.model.CreditCardDetail;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreditCardDetailTest {
    /**
     * Verifies REQ-6
     */
    @Test
    void testCreditCardsHaveAllRequiredInformation() {
        // Arrange: Create a CreditCardDetail instance with known values
        Long id = 12345L;
        String cardNumber = "1234-5678-9876-5432";
        String dateExpire = "12/24";
        String dateValid = "12/22";
        String cvv = "123";
        BigDecimal creditLimit = new BigDecimal("5000.00");
        BigDecimal apr = new BigDecimal("15.99");

        // Create the CreditCardDetail instance and set its fields
        CreditCardDetail creditCardDetail = new CreditCardDetail();
        creditCardDetail.setId(id);
        creditCardDetail.setCardNumber(cardNumber);
        creditCardDetail.setDateExpire(dateExpire);
        creditCardDetail.setDateValid(dateValid);
        creditCardDetail.setCvv(cvv);
        creditCardDetail.setCreditLimit(creditLimit);
        creditCardDetail.setApr(apr);

        // Construct the expected output
        String expectedOutput = "CreditCardDetail [id=" + id +
                ", cardNumber=" + cardNumber +
                ", dateExpire=" + dateExpire +
                ", dateValid=" + dateValid +
                ", cvv=" + cvv +
                ", creditLimit=" + creditLimit +
                ", apr=" + apr + "]";

        // Act: Call the toString() method
        String actualOutput = creditCardDetail.toString();

        // Assert: Verify the actual output matches the expected output
        assertEquals(expectedOutput, actualOutput);
    }
}
