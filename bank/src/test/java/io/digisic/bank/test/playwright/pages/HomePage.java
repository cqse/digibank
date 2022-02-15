package io.digisic.bank.test.playwright.pages;

import com.microsoft.playwright.Page;

public class HomePage {

    private final Page page;

    public HomePage(Page page) {
        this.page = page;
    }

    public boolean isAt() {
        return page.url().contains("/home");
    }
}
