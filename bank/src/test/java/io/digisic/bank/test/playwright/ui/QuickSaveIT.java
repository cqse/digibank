package io.digisic.bank.test.playwright.ui;

import io.digisic.bank.test.playwright.pages.NewSavingsAccountPage;
import io.digisic.bank.test.playwright.pages.QuickSavePage;
import io.digisic.bank.test.playwright.support.LoginHelper;
import io.digisic.bank.test.playwright.support.PlaywrightBaseIT;
import io.digisic.bank.test.playwright.support.TestDataService;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class QuickSaveIT extends PlaywrightBaseIT {

    private static final String DEFAULT_PASSWORD = "Demo123!";

    @Test
    void quickSaveOrderCanBePlacedAgainstSavingsAccount() {
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

        QuickSavePage quickSave = new QuickSavePage(page, UI_BASE_URL);
        quickSave.navigate();
        quickSave.selectToAccountByIndex(1);
        quickSave.enterAmount("11");
        quickSave.submit();

        page.waitForURL(url -> url.contains(NewSavingsAccountPage.VIEW_PATH));
        assertThat(savings.isAtViewSavingsPage())
                .as("QuickSave order against savings account should redirect to savings-view")
                .isTrue();
    }
}
