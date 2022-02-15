package io.digisic.bank.test.playwright.pages;

import com.microsoft.playwright.Page;

public class ChangePasswordPage {

    public static final String PATH = "/user/password";

    private final Page page;
    private final String baseUrl;

    public ChangePasswordPage(Page page, String baseUrl) {
        this.page = page;
        this.baseUrl = baseUrl;
    }

    public void navigate() {
        page.navigate(baseUrl + PATH);
    }

    public boolean isAt() {
        return page.url().contains(PATH);
    }

    public void enterCurrentPassword(String password) {
        page.locator("#currentPassword").fill(password);
    }

    public void enterNewPassword(String password) {
        page.locator("#newPassword").fill(password);
    }

    public void enterConfirmPassword(String password) {
        page.locator("#confirmPassword").fill(password);
        page.locator("#confirmPassword").dispatchEvent("keyup");
    }

    public void submit() {
        page.locator("#payment-button").click();
    }

    public boolean successBannerVisible() {
        return page.locator(".alert-success").isVisible();
    }

    public boolean errorBannerVisible() {
        return page.locator(".alert-danger").isVisible();
    }

    public String errorBannerText() {
        return page.locator(".alert-danger").innerText();
    }

    public String successBannerText() {
        return page.locator(".alert-success").innerText();
    }

    public boolean formIsValid() {
        return (Boolean) page.locator("#payment-button").evaluate("b => b.form.checkValidity()");
    }
}
