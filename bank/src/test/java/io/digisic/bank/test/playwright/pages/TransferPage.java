package io.digisic.bank.test.playwright.pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.SelectOption;

public class TransferPage {

    public static final String PATH = "/account/xfer-between";

    private final Page page;
    private final String baseUrl;

    public TransferPage(Page page, String baseUrl) {
        this.page = page;
        this.baseUrl = baseUrl;
    }

    public void navigate() {
        page.navigate(baseUrl + PATH);
    }

    public boolean isAt() {
        return page.url().contains(PATH);
    }

    public void selectFromAccountByIndex(int index) {
        page.locator("#fromAccount").selectOption(new SelectOption().setIndex(index));
    }

    public void selectToAccountByIndex(int index) {
        page.locator("#toAccount").selectOption(new SelectOption().setIndex(index));
    }

    public void enterAmount(String amount) {
        page.locator("#amount").fill(amount);
    }

    public void submit() {
        page.locator("#formSubmitButton").click();
    }

    public boolean errorVisible() {
        return page.locator("#error").isVisible();
    }

    public String errorText() {
        return page.locator("#error").innerText();
    }
}
