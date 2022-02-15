package io.digisic.bank.test.playwright.pages;

import com.microsoft.playwright.Page;

public class NewSavingsAccountPage {

    public static final String PATH = "/account/savings-add";
    public static final String VIEW_PATH = "/account/savings-view";

    private final Page page;
    private final String baseUrl;

    public NewSavingsAccountPage(Page page, String baseUrl) {
        this.page = page;
        this.baseUrl = baseUrl;
    }

    public void navigate() {
        page.navigate(baseUrl + PATH);
    }

    public boolean isAtNewSavingsPage() {
        return page.url().contains(PATH);
    }

    public boolean isAtViewSavingsPage() {
        return page.url().contains(VIEW_PATH);
    }

    public void enterAccountName(String name) {
        page.locator("#name").fill(name);
    }

    public void enterInitialBalance(String amount) {
        page.locator("#openingBalance").fill(amount);
    }

    public void selectAccountType(String accountType) {
        if ("None Selected".equals(accountType)) {
            return;
        }
        page.locator("[id=\"" + accountType + "\"]").check();
    }

    public void selectOwnershipType(String ownershipType) {
        if ("None Selected".equals(ownershipType)) {
            return;
        }
        page.locator("[id=\"" + ownershipType + "\"]").check();
    }

    public void submit() {
        page.locator("#newSavingsSubmit").click();
    }

    public boolean errorAlertVisible() {
        return page.locator("#new-account-error-alert").isVisible();
    }

    public String errorAlertText() {
        return page.locator("#new-account-error-msg").innerText();
    }
}
