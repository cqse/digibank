package io.digisic.bank.test.playwright.pages;

import com.microsoft.playwright.Page;

public class RegisterUserPage {

    private final Page page;
    private final String baseUrl;

    public RegisterUserPage(Page page, String baseUrl) {
        this.page = page;
        this.baseUrl = baseUrl;
    }

    public void navigate() {
        page.navigate(baseUrl + "/signup");
    }

    public void selectTitle(String title) {
        page.locator("#title").selectOption(title);
    }

    public void enterFirstName(String value) {
        page.locator("#firstName").fill(value);
    }

    public void enterLastName(String value) {
        page.locator("#lastName").fill(value);
    }

    public void selectGender(String gender) {
        if (gender == null || gender.isEmpty()) {
            return;
        }
        page.locator("input[name=gender][value=" + gender + "]").check();
    }

    public void enterDateOfBirth(String value) {
        page.locator("#dob").fill(value);
    }

    public void enterSsn(String value) {
        page.locator("#ssn").fill(value);
    }

    public void enterEmailAddress(String value) {
        page.locator("#emailAddress").fill(value);
    }

    public void enterPassword(String value) {
        page.locator("#password").fill(value);
    }

    public void enterConfirmPassword(String value) {
        page.locator("#confirmPassword").fill(value);
        page.evaluate("if (typeof validatePassword === 'function') { validatePassword(); }");
    }

    public void clickNext() {
        page.locator("form button[type=submit]").click();
    }

    public void enterAddress(String value) {
        page.locator("#address").fill(value);
    }

    public void enterLocality(String value) {
        page.locator("#locality").fill(value);
    }

    public void enterRegion(String value) {
        page.locator("#region").fill(value);
    }

    public void enterPostalCode(String value) {
        page.locator("#postalCode").fill(value);
    }

    public void enterCountry(String value) {
        page.locator("#country").fill(value);
    }

    public void enterHomePhone(String value) {
        page.locator("#homePhone").fill(value);
    }

    public void enterWorkPhone(String value) {
        page.locator("#workPhone").fill(value);
    }

    public void enterMobilePhone(String value) {
        page.locator("#mobilePhone").fill(value);
    }

    public void selectAgreeTerms(boolean agree) {
        if (agree) {
            page.locator("#agree-terms").check();
        }
    }

    public void clickRegister() {
        page.locator("form button[type=submit]").click();
    }

    public boolean isOnIdentityPage() {
        String action = page.locator("form").getAttribute("action");
        return action != null && action.contains("/signup");
    }

    public boolean isOnContactDetailsPage() {
        String action = page.locator("form").getAttribute("action");
        return action != null && action.contains("/register");
    }

    public boolean errorBannerVisible() {
        return page.locator(".alert-danger").isVisible();
    }

    public boolean successBannerVisible() {
        return page.locator(".alert-success").isVisible();
    }
}
