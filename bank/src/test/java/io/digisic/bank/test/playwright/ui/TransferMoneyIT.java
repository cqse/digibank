package io.digisic.bank.test.playwright.ui;

import io.digisic.bank.test.playwright.pages.NewSavingsAccountPage;
import io.digisic.bank.test.playwright.pages.TransferPage;
import io.digisic.bank.test.playwright.support.LoginHelper;
import io.digisic.bank.test.playwright.support.PlaywrightBaseIT;
import io.digisic.bank.test.playwright.support.TestDataService;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TransferMoneyIT extends PlaywrightBaseIT {

    private static final String DEFAULT_PASSWORD = "Demo123!";
    private static final String SAME_ACCOUNT_ERROR = "Can not trasnsfer from and to the same account.";

    @Test
    void transferBetweenSameAccountIsNotPossible() {
        String persona = "Carleen";
        String email = "Carleen6231@gmail.com";

        try (TestDataService data = new TestDataService()) {
            data.ensureRegisteredUser(persona, email);
        }

        LoginHelper.loginAs(page, UI_BASE_URL, email, DEFAULT_PASSWORD);

        NewSavingsAccountPage savings = new NewSavingsAccountPage(page, UI_BASE_URL);
        savings.navigate();
        savings.enterAccountName("Tangerine Savings");
        savings.selectOwnershipType("Individual");
        savings.selectAccountType("Money Market");
        savings.enterInitialBalance("2500");
        savings.submit();
        page.waitForURL(url -> url.contains(NewSavingsAccountPage.VIEW_PATH));

        TransferPage transfer = new TransferPage(page, UI_BASE_URL);
        transfer.navigate();
        transfer.selectFromAccountByIndex(1);
        transfer.selectToAccountByIndex(1);
        transfer.enterAmount("11");
        transfer.submit();

        assertThat(transfer.errorVisible())
                .as("same-account transfer should display the server error banner")
                .isTrue();
        assertThat(transfer.errorText()).isEqualTo(SAME_ACCOUNT_ERROR);
    }
}
