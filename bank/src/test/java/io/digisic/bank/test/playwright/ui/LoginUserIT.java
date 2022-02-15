package io.digisic.bank.test.playwright.ui;

import io.digisic.bank.test.playwright.pages.HomePage;
import io.digisic.bank.test.playwright.pages.LoginPage;
import io.digisic.bank.test.playwright.support.LoginHelper;
import io.digisic.bank.test.playwright.support.PlaywrightBaseIT;
import io.digisic.bank.test.playwright.support.TestDataService;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LoginUserIT extends PlaywrightBaseIT {

    private static final String DEFAULT_PASSWORD = "Demo123!";

    @Test
    void loginWithValidCredentials() {
        String persona = "Nourbese";
        String email = "Nourbese46196@gmail.com";

        try (TestDataService data = new TestDataService()) {
            data.ensureRegisteredUser(persona, email);
        }

        LoginHelper.loginAs(page, UI_BASE_URL, email, DEFAULT_PASSWORD);

        LoginPage login = new LoginPage(page, UI_BASE_URL);
        HomePage home = new HomePage(page);
        assertThat(home.isAt()).as("user should land on home page after valid login").isTrue();
        assertThat(login.rememberMeCookiePresent(context))
                .as("remember-me cookie should not be set when checkbox not selected")
                .isFalse();
    }

    @Test
    void loginValidUsernameInvalidPassword() {
        String email = "Dau7009@gmail.com";
        ensureUser("Dau", email);
        LoginHelper.loginAs(page, UI_BASE_URL, email, "ZYGStNSsH922");
        assertErrorBannerVisible();
    }

    @Test
    void loginValidUsernameEmptyPassword() {
        String email = "Nalinaksha18796@gmail.com";
        ensureUser("Nalinaksha", email);
        LoginHelper.loginAs(page, UI_BASE_URL, email, "");
        assertErrorBannerVisible();
    }

    @Test
    void loginRememberMeSelected() {
        String email = "Munyiga19371@gmail.com";
        ensureUser("Munyiga", email);

        LoginHelper.loginAsWithRememberMe(page, UI_BASE_URL, email, DEFAULT_PASSWORD);

        LoginPage login = new LoginPage(page, UI_BASE_URL);
        assertThat(new HomePage(page).isAt())
                .as("user should land on home page after valid login with remember-me")
                .isTrue();
        assertThat(login.rememberMeCookiePresent(context))
                .as("remember-me cookie should be set when checkbox selected")
                .isTrue();
    }

    @Test
    void loginCredentialsExpired() {
        String email = "Angel1496@gmail.com";
        try (TestDataService data = new TestDataService()) {
            int userId = data.ensureRegisteredUser("Angel", email);
            data.expireCredentials(userId);
        }
        LoginHelper.loginAs(page, UI_BASE_URL, email, DEFAULT_PASSWORD);
        assertErrorBannerVisible();
    }

    @Test
    void loginAccountExpired() {
        String email = "Ierne4219@gmail.com";
        try (TestDataService data = new TestDataService()) {
            int userId = data.ensureRegisteredUser("Ierne", email);
            data.expireAccount(userId);
        }
        LoginHelper.loginAs(page, UI_BASE_URL, email, DEFAULT_PASSWORD);
        assertErrorBannerVisible();
    }

    @Test
    void loginAccountLocked() {
        String email = "Cameron57931@gmail.com";
        try (TestDataService data = new TestDataService()) {
            int userId = data.ensureRegisteredUser("Cameron", email);
            data.lockAccount(userId);
        }
        LoginHelper.loginAs(page, UI_BASE_URL, email, DEFAULT_PASSWORD);
        assertErrorBannerVisible();
    }

    @Test
    void loginAccountDisabled() {
        String email = "Johnathan873928@gmail.com";
        try (TestDataService data = new TestDataService()) {
            int userId = data.ensureRegisteredUser("Johnathan", email);
            data.disableAccount(userId);
        }
        LoginHelper.loginAs(page, UI_BASE_URL, email, DEFAULT_PASSWORD);
        assertErrorBannerVisible();
    }

    @Test
    void loginInvalidUsernameValidPassword() {
        ensureUser("Fanish", "Fanish252290@gmail.com");
        LoginHelper.loginAs(page, UI_BASE_URL, "MdFanish252290@gmail.com", DEFAULT_PASSWORD);
        assertErrorBannerVisible();
    }

    @Test
    void loginInvalidUsernameInvalidPassword() {
        ensureUser("Kelal", "Kelal518648@gmail.com");
        LoginHelper.loginAs(page, UI_BASE_URL, "BesKelal518648@gmail.com", "UiacBCRVBPxQZ572");
        assertErrorBannerVisible();
    }

    @Test
    void loginInvalidUsernameEmptyPassword() {
        ensureUser("Nathen", "Nathen0615@gmail.com");
        LoginHelper.loginAs(page, UI_BASE_URL, "HNathen0615@gmail.com", "");
        assertErrorBannerVisible();
    }

    @Test
    void loginEmptyUsernameValidPassword() {
        ensureUser("Erysichthon", "Erysichthon3329@gmail.com");
        LoginHelper.loginAs(page, UI_BASE_URL, "", DEFAULT_PASSWORD);
        assertErrorBannerVisible();
    }

    @Test
    void loginEmptyUsernameInvalidPassword() {
        ensureUser("Anatolio", "Anatolio35619@gmail.com");
        LoginHelper.loginAs(page, UI_BASE_URL, "", "taFPzBULBMGOUNJv10687");
        assertErrorBannerVisible();
    }

    @Test
    void loginEmptyUsernameEmptyPassword() {
        ensureUser("Aled", "Aled1969@gmail.com");
        LoginHelper.loginAs(page, UI_BASE_URL, "", "");
        assertErrorBannerVisible();
    }

    private void ensureUser(String persona, String email) {
        try (TestDataService data = new TestDataService()) {
            data.ensureRegisteredUser(persona, email);
        }
    }

    private void assertErrorBannerVisible() {
        LoginPage login = new LoginPage(page, UI_BASE_URL);
        assertThat(login.errorBannerVisible())
                .as("login error banner should be visible after failed authentication")
                .isTrue();
    }
}
