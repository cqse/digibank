package io.digisic.bank.test.playwright.ui;

import io.digisic.bank.test.playwright.pages.HomePage;
import io.digisic.bank.test.playwright.pages.LoginPage;
import io.digisic.bank.test.playwright.pages.UserProfileMenu;
import io.digisic.bank.test.playwright.support.LoginHelper;
import io.digisic.bank.test.playwright.support.PlaywrightBaseIT;
import io.digisic.bank.test.playwright.support.TestDataService;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LogoutUserIT extends PlaywrightBaseIT {

    private static final String DEFAULT_PASSWORD = "Demo123!";

    @Test
    void logoutEndsSession() {
        String persona = "Keanu";
        String email = "Keanu05681@gmail.com";

        try (TestDataService data = new TestDataService()) {
            data.ensureRegisteredUser(persona, email);
        }

        LoginHelper.loginAs(page, UI_BASE_URL, email, DEFAULT_PASSWORD);
        assertThat(new HomePage(page).isAt()).as("user should be on home page before logout").isTrue();

        UserProfileMenu menu = new UserProfileMenu(page);
        menu.open();
        menu.selectByLinkText("Logout");
        page.waitForURL(url -> url.contains("/login"));

        LoginPage login = new LoginPage(page, UI_BASE_URL);
        assertThat(login.isAt()).as("user should land on login page after logout").isTrue();
        assertThat(login.successBannerVisible())
                .as("logout success banner should be visible after logout")
                .isTrue();
    }
}
