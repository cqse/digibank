package io.digisic.bank.test.playwright.ui;

import io.digisic.bank.test.playwright.pages.NewSavingsAccountPage;
import io.digisic.bank.test.playwright.support.LoginHelper;
import io.digisic.bank.test.playwright.support.PlaywrightBaseIT;
import io.digisic.bank.test.playwright.support.TestDataService;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateSavingsAccountIT extends PlaywrightBaseIT {

    private static final String DEFAULT_PASSWORD = "Demo123!";

    private NewSavingsAccountPage openNewSavings(String persona, String email) {
        try (TestDataService data = new TestDataService()) {
            data.ensureRegisteredUser(persona, email);
        }
        LoginHelper.loginAs(page, UI_BASE_URL, email, DEFAULT_PASSWORD);
        NewSavingsAccountPage savings = new NewSavingsAccountPage(page, UI_BASE_URL);
        savings.navigate();
        return savings;
    }

    private void submitAndSettle(NewSavingsAccountPage savings) {
        savings.submit();
        page.waitForLoadState();
    }

    private void assertAccountCreated(NewSavingsAccountPage savings) {
        page.waitForURL(url -> url.contains(NewSavingsAccountPage.VIEW_PATH));
        assertThat(savings.isAtViewSavingsPage())
                .as("successful savings submission should redirect to the savings-view page")
                .isTrue();
    }

    private void assertAccountNotCreated(NewSavingsAccountPage savings) {
        assertThat(savings.isAtViewSavingsPage())
                .as("rejected savings submission must not redirect to the savings-view page")
                .isFalse();
        assertThat(savings.isAtNewSavingsPage())
                .as("rejected savings submission should leave the user on the savings-add page")
                .isTrue();
    }

    private void assertInsufficientBalanceAlertShown(NewSavingsAccountPage savings) {
        page.locator("#new-account-error-alert").waitFor();
        assertThat(savings.errorAlertVisible())
                .as("server should render the insufficient-deposit alert banner")
                .isTrue();
        assertThat(savings.errorAlertText())
                .as("alert text should reference the minimum-deposit message")
                .contains("does not meet the minimum amount");
    }

    @Test
    void individualMoneyMarketBelowMinimumIsRejectedWithInsufficientBalance() {
        NewSavingsAccountPage savings = openNewSavings("Carleen", "Carleen6231@gmail.com");
        savings.enterAccountName("Figs Savings");
        savings.selectOwnershipType("Individual");
        savings.selectAccountType("Money Market");
        savings.enterInitialBalance("1807");
        submitAndSettle(savings);
        assertAccountNotCreated(savings);
        assertInsufficientBalanceAlertShown(savings);
    }

    @Test
    void individualMoneyMarketNegativeDepositIsBlockedByFormValidation() {
        NewSavingsAccountPage savings = openNewSavings("Busara", "Busara4792@gmail.com");
        savings.enterAccountName("Nectarines Savings");
        savings.selectOwnershipType("Individual");
        savings.selectAccountType("Money Market");
        savings.enterInitialBalance("-30");
        submitAndSettle(savings);
        assertAccountNotCreated(savings);
    }

    @Test
    void individualMoneyMarketAtMinimumCreatesAccount() {
        NewSavingsAccountPage savings = openNewSavings("Ducha", "Ducha793045@gmail.com");
        savings.enterAccountName("Tangerine Savings");
        savings.selectOwnershipType("Individual");
        savings.selectAccountType("Money Market");
        savings.enterInitialBalance("2500");
        submitAndSettle(savings);
        assertAccountCreated(savings);
    }

    @Test
    void individualMoneyMarketAboveMinimumCreatesAccount() {
        NewSavingsAccountPage savings = openNewSavings("Kamalkali", "Kamalkali45522@gmail.com");
        savings.enterAccountName("Jujube Savings");
        savings.selectOwnershipType("Individual");
        savings.selectAccountType("Money Market");
        savings.enterInitialBalance("3311");
        submitAndSettle(savings);
        assertAccountCreated(savings);
    }

    @Test
    void individualSavingsAtMinimumCreatesAccount() {
        NewSavingsAccountPage savings = openNewSavings("Citiwala", "Citiwala79985@gmail.com");
        savings.enterAccountName("Barberry Savings");
        savings.selectOwnershipType("Individual");
        savings.selectAccountType("Savings");
        savings.enterInitialBalance("25");
        submitAndSettle(savings);
        assertAccountCreated(savings);
    }

    @Test
    void individualSavingsAboveMinimumCreatesAccount() {
        NewSavingsAccountPage savings = openNewSavings("CamronCamshron", "CamronCamshron879652@gmail.com");
        savings.enterAccountName("Mandarin Savings");
        savings.selectOwnershipType("Individual");
        savings.selectAccountType("Savings");
        savings.enterInitialBalance("687");
        submitAndSettle(savings);
        assertAccountCreated(savings);
    }

    @Test
    void individualSavingsBelowMinimumIsRejectedWithInsufficientBalance() {
        NewSavingsAccountPage savings = openNewSavings("Izhar", "Izhar202550@gmail.com");
        savings.enterAccountName("Watermelon Savings");
        savings.selectOwnershipType("Individual");
        savings.selectAccountType("Savings");
        savings.enterInitialBalance("19");
        submitAndSettle(savings);
        assertAccountNotCreated(savings);
        assertInsufficientBalanceAlertShown(savings);
    }

    @Test
    void individualSavingsNegativeDepositIsBlockedByFormValidation() {
        NewSavingsAccountPage savings = openNewSavings("Keyur", "Keyur2368@gmail.com");
        savings.enterAccountName("Jujube Savings");
        savings.selectOwnershipType("Individual");
        savings.selectAccountType("Savings");
        savings.enterInitialBalance("-60");
        submitAndSettle(savings);
        assertAccountNotCreated(savings);
    }

    @Test
    void individualWithoutAccountTypeIsBlockedByFormValidation() {
        NewSavingsAccountPage savings = openNewSavings("Madaha", "Madaha72830@gmail.com");
        savings.enterAccountName("Key Lime Savings");
        savings.selectOwnershipType("Individual");
        savings.selectAccountType("None Selected");
        submitAndSettle(savings);
        assertAccountNotCreated(savings);
    }

    @Test
    void jointMoneyMarketBelowMinimumIsRejectedWithInsufficientBalance() {
        NewSavingsAccountPage savings = openNewSavings("Nureet", "Nureet70644@gmail.com");
        savings.enterAccountName("Pineapple Savings");
        savings.selectOwnershipType("Joint");
        savings.selectAccountType("Money Market");
        savings.enterInitialBalance("411");
        submitAndSettle(savings);
        assertAccountNotCreated(savings);
        assertInsufficientBalanceAlertShown(savings);
    }

    @Test
    void jointMoneyMarketNegativeDepositIsBlockedByFormValidation() {
        NewSavingsAccountPage savings = openNewSavings("Phoebe", "Phoebe81976@gmail.com");
        savings.enterAccountName("Custard Apple Savings");
        savings.selectOwnershipType("Joint");
        savings.selectAccountType("Money Market");
        savings.enterInitialBalance("-42");
        submitAndSettle(savings);
        assertAccountNotCreated(savings);
    }

    @Test
    void jointMoneyMarketAtMinimumCreatesAccount() {
        NewSavingsAccountPage savings = openNewSavings("Marcos", "Marcos789361@gmail.com");
        savings.enterAccountName("Raspberry Savings");
        savings.selectOwnershipType("Joint");
        savings.selectAccountType("Money Market");
        savings.enterInitialBalance("2500");
        submitAndSettle(savings);
        assertAccountCreated(savings);
    }

    @Test
    void jointMoneyMarketAboveMinimumCreatesAccount() {
        NewSavingsAccountPage savings = openNewSavings("Frances", "Frances323900@gmail.com");
        savings.enterAccountName("Barberry Savings");
        savings.selectOwnershipType("Joint");
        savings.selectAccountType("Money Market");
        savings.enterInitialBalance("3619");
        submitAndSettle(savings);
        assertAccountCreated(savings);
    }

    @Test
    void jointSavingsAtMinimumCreatesAccount() {
        NewSavingsAccountPage savings = openNewSavings("Evadne", "Evadne2633@gmail.com");
        savings.enterAccountName("Passion Fruit Savings");
        savings.selectOwnershipType("Joint");
        savings.selectAccountType("Savings");
        savings.enterInitialBalance("25");
        submitAndSettle(savings);
        assertAccountCreated(savings);
    }

    @Test
    void jointSavingsAboveMinimumCreatesAccount() {
        NewSavingsAccountPage savings = openNewSavings("Autolycus", "Autolycus615233@gmail.com");
        savings.enterAccountName("Vanilla Savings");
        savings.selectOwnershipType("Joint");
        savings.selectAccountType("Savings");
        savings.enterInitialBalance("439");
        submitAndSettle(savings);
        assertAccountCreated(savings);
    }

    @Test
    void jointSavingsBelowMinimumIsRejectedWithInsufficientBalance() {
        NewSavingsAccountPage savings = openNewSavings("Kefira", "Kefira7788@gmail.com");
        savings.enterAccountName("Pawpaw Savings");
        savings.selectOwnershipType("Joint");
        savings.selectAccountType("Savings");
        savings.enterInitialBalance("12");
        submitAndSettle(savings);
        assertAccountNotCreated(savings);
        assertInsufficientBalanceAlertShown(savings);
    }

    @Test
    void jointSavingsNegativeDepositIsBlockedByFormValidation() {
        NewSavingsAccountPage savings = openNewSavings("Nurisha", "Nurisha0309@gmail.com");
        savings.enterAccountName("Blueberry Savings");
        savings.selectOwnershipType("Joint");
        savings.selectAccountType("Savings");
        savings.enterInitialBalance("-77");
        submitAndSettle(savings);
        assertAccountNotCreated(savings);
    }

    @Test
    void jointWithoutAccountTypeIsBlockedByFormValidation() {
        NewSavingsAccountPage savings = openNewSavings("Tsoka", "Tsoka36489@gmail.com");
        savings.enterAccountName("Orange Savings");
        savings.selectOwnershipType("Joint");
        savings.selectAccountType("None Selected");
        submitAndSettle(savings);
        assertAccountNotCreated(savings);
    }

    @Test
    void withoutOwnershipSelectionIsBlockedByFormValidation() {
        NewSavingsAccountPage savings = openNewSavings("Nicodemus", "Nicodemus4858@gmail.com");
        savings.enterAccountName("Crabapple Savings");
        savings.selectOwnershipType("None Selected");
        submitAndSettle(savings);
        assertAccountNotCreated(savings);
    }

    @Test
    void emptyAccountNameIsBlockedByFormValidation() {
        NewSavingsAccountPage savings = openNewSavings("Nishant", "Nishant190233@gmail.com");
        savings.enterAccountName("");
        submitAndSettle(savings);
        assertAccountNotCreated(savings);
    }

    @Test
    void invalidAccountNameIsBlockedByFormValidation() {
        NewSavingsAccountPage savings = openNewSavings("Lipi", "Lipi7131@gmail.com");
        savings.enterAccountName("Rhubarb Savings ()($");
        submitAndSettle(savings);
        assertAccountNotCreated(savings);
    }
}
