package io.digisic.bank.test.junit.model;

import io.digisic.bank.model.obp.Bank;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankTest {

    /**
     * Verifies REQ-3
     */
    @Test
    void testBankFields() {
        Bank bank = new Bank();
        bank.setFull_name("Bank of America");
        bank.setShort_name("BoA");
        bank.setId("1337");
        bank.setWebsite("https://www.bankofamerica.com/");

        String result = bank.toString();
        String expected = "Bank{" +
                "id='1337"  + '\'' +
                ", short_name='BoA" + '\'' +
                ", full_name='Bank of America"  + '\'' +
                ", logo='null"  + '\'' +
                ", website='https://www.bankofamerica.com/" + '\'' +
                ", bank_routing=null"  +
                '}';
        assertEquals(expected, result);
    }
}
