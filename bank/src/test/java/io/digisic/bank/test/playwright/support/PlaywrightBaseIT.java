package io.digisic.bank.test.playwright.support;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public abstract class PlaywrightBaseIT {

    protected static final String UI_BASE_URL = System.getProperty("ui.base.url", "https://localhost:8443/bank");

    protected static Playwright playwright;
    protected static Browser browser;
    protected BrowserContext context;
    protected Page page;

    @BeforeAll
    static void launchBrowser() {
        playwright = Playwright.create();
        BrowserType.LaunchOptions options = new BrowserType.LaunchOptions()
                .setHeadless(shouldRunHeadless())
                .setSlowMo(slowMoMillis());
        String browserName = System.getProperty("pw.browser", "chromium");
        switch (browserName) {
            case "firefox":
                browser = playwright.firefox().launch(options);
                break;
            case "webkit":
                browser = playwright.webkit().launch(options);
                break;
            default:
                browser = playwright.chromium().launch(options);
                break;
        }
    }

    private static boolean shouldRunHeadless() {
        String showBrowser = System.getProperty("showBrowser");
        return showBrowser == null || showBrowser.equalsIgnoreCase("false");
    }

    private static double slowMoMillis() {
        String slowMo = System.getProperty("slowMo");
        if (slowMo == null || slowMo.isEmpty()) {
            return 0;
        }
        try {
            return Double.parseDouble(slowMo);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid -DslowMo value: " + slowMo, e);
        }
    }

    @BeforeEach
    void createContextAndPage() {
        context = browser.newContext(new Browser.NewContextOptions().setIgnoreHTTPSErrors(true));
        page = context.newPage();
    }

    @AfterEach
    void closeContext() {
        if (context != null) {
            context.close();
        }
    }

    @AfterAll
    static void closeBrowser() {
        if (browser != null) {
            browser.close();
        }
        if (playwright != null) {
            playwright.close();
        }
    }
}
