package io.digisic.bank.test.playwright.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;
import io.digisic.bank.test.playwright.support.TestDataService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AtmLocationSearchApiIT {

    private static Playwright playwright;
    private static APIRequestContext api;
    private static String apiBaseUrl;
    private static String authToken;
    private static final ObjectMapper mapper = new ObjectMapper();

    @BeforeAll
    static void setUp() {
        playwright = Playwright.create();
        apiBaseUrl = System.getProperty("api.base.url", "https://localhost:8443/bank");
        api = playwright.request().newContext(
                new APIRequest.NewContextOptions().setIgnoreHTTPSErrors(true));
        try (TestDataService data = new TestDataService()) {
            authToken = data.getAuthToken();
        }
    }

    @AfterAll
    static void tearDown() {
        if (api != null) {
            api.dispose();
        }
        if (playwright != null) {
            playwright.close();
        }
    }

    @Test
    void invalidZipCodeReturns500() {
        APIResponse response = searchAtm("976539926568759");
        assertThat(response.status())
                .as("invalid zipcode should return HTTP 500")
                .isEqualTo(500);
        assertErrorMessagePresent(response);
    }

    @Test
    void emptyZipCodeReturns500() {
        APIResponse response = searchAtm("");
        assertThat(response.status())
                .as("empty zipcode should return HTTP 500")
                .isEqualTo(500);
        assertErrorMessagePresent(response);
    }

    private APIResponse searchAtm(String zipcode) {
        return api.get(apiBaseUrl + "/api/v1/search/atm",
                RequestOptions.create()
                        .setQueryParam("zipcode", zipcode)
                        .setHeader("Authorization", "Bearer " + authToken));
    }

    private void assertErrorMessagePresent(APIResponse response) {
        try {
            JsonNode body = mapper.readTree(response.body());
            assertThat(body.hasNonNull("message"))
                    .as("response body should contain a non-null 'message' field")
                    .isTrue();
            assertThat(body.get("message").asText())
                    .as("'message' field should not be empty")
                    .isNotBlank();
        } catch (Exception e) {
            throw new AssertionError("failed to parse response body as JSON", e);
        }
    }
}
