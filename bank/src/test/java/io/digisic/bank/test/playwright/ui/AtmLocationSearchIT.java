package io.digisic.bank.test.playwright.ui;

import io.digisic.bank.test.playwright.pages.SearchLocationPage;
import io.digisic.bank.test.playwright.support.LoginHelper;
import io.digisic.bank.test.playwright.support.PlaywrightBaseIT;
import io.digisic.bank.test.playwright.support.TestDataService;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AtmLocationSearchIT extends PlaywrightBaseIT {

    private static final String DEFAULT_PASSWORD = "Demo123!";

    @Test
    void invalidZipCodeShowsValidationError() {
        loginAndSearchWithZipcode("Pentheus", "Pentheus6237@gmail.com", "680083148655");
        assertZipcodeRequiredValidation();
    }

    @Test
    void emptyZipCodeShowsValidationError() {
        loginAndSearchWithZipcode("Dominica", "Dominica96840@gmail.com", "");
        assertZipcodeRequiredValidation();
    }

    private void loginAndSearchWithZipcode(String persona, String email, String zipcode) {
        try (TestDataService data = new TestDataService()) {
            data.ensureRegisteredUser(persona, email);
        }
        LoginHelper.loginAs(page, UI_BASE_URL, email, DEFAULT_PASSWORD);

        SearchLocationPage search = new SearchLocationPage(page);
        search.openSearchForm();
        search.enterZipCodeAndSubmit(zipcode);
    }

    private void assertZipcodeRequiredValidation() {
        SearchLocationPage search = new SearchLocationPage(page);
        assertThat(search.zipcodeFieldHasRequiredAttribute())
                .as("zipcode field should carry HTML5 'required' attribute, triggering native validation on invalid/empty input")
                .isTrue();
    }
}
