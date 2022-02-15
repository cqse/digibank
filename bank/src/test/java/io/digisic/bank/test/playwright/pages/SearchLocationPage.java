package io.digisic.bank.test.playwright.pages;

import com.microsoft.playwright.Page;

public class SearchLocationPage {

    private final Page page;

    public SearchLocationPage(Page page) {
        this.page = page;
    }

    public void openSearchForm() {
        page.locator("#searchLocations").click();
    }

    public void enterZipCodeAndSubmit(String zipcode) {
        page.locator("#zipcode").fill(zipcode);
        page.locator("#zipcode").press("Enter");
    }

    public boolean zipcodeFieldHasRequiredAttribute() {
        return page.locator("#zipcode").getAttribute("required") != null;
    }
}
