package io.digisic.bank.test.playwright.pages;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.Cookie;

import java.util.List;

public class LoginPage {

    private static final String REMEMBER_ME_COOKIE = "remember-me";

    private final Page page;
    private final String baseUrl;

    public LoginPage(Page page, String baseUrl) {
        this.page = page;
        this.baseUrl = baseUrl;
    }

    public void navigate() {
        page.navigate(baseUrl + "/login");
    }

    public boolean isAt() {
        return page.url().contains("/login");
    }

    public void enterUsername(String username) {
        page.locator("#username").fill(username);
    }

    public void enterPassword(String password) {
        page.locator("#password").fill(password);
    }

    public void toggleRememberMe() {
        page.locator("#remember-me").check();
    }

    public void submit() {
        page.locator("#submit").click();
    }

    public boolean errorBannerVisible() {
        return page.locator(".alert-danger").isVisible();
    }

    public boolean successBannerVisible() {
        return page.locator(".alert-success").isVisible();
    }

    public String usernameValue() {
        return page.locator("#username").inputValue();
    }

    public boolean rememberMeCookiePresent(BrowserContext context) {
        List<Cookie> cookies = context.cookies();
        return cookies.stream().anyMatch(c -> REMEMBER_ME_COOKIE.equals(c.name));
    }
}
