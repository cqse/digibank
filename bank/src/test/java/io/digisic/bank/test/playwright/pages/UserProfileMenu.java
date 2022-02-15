package io.digisic.bank.test.playwright.pages;

import com.microsoft.playwright.Page;

public class UserProfileMenu {

    private final Page page;

    public UserProfileMenu(Page page) {
        this.page = page;
    }

    public void open() {
        page.locator(".user-area.dropdown:has(.user-menu) img.user-avatar").click();
    }

    public void selectByLinkText(String linkText) {
        page.locator(".user-area .user-menu a.nav-link", new Page.LocatorOptions().setHasText(linkText)).click();
    }
}
