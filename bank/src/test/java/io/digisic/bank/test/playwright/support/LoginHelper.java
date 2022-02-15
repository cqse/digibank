package io.digisic.bank.test.playwright.support;

import com.microsoft.playwright.Page;

import io.digisic.bank.test.playwright.pages.LoginPage;

public final class LoginHelper {

    private LoginHelper() {}

    public static void loginAs(Page page, String uiBaseUrl, String email, String password) {
        submitLoginForm(page, uiBaseUrl, email, password, false);
    }

    public static void loginAsWithRememberMe(Page page, String uiBaseUrl, String email, String password) {
        submitLoginForm(page, uiBaseUrl, email, password, true);
    }

    private static void submitLoginForm(Page page, String uiBaseUrl, String email, String password, boolean rememberMe) {
        LoginPage login = new LoginPage(page, uiBaseUrl);
        login.navigate();
        login.enterUsername(email);
        login.enterPassword(password);
        if (rememberMe) {
            login.toggleRememberMe();
        }
        login.submit();
        page.waitForURL(url -> url.contains("/home") || url.contains("/login?error"));
    }
}
