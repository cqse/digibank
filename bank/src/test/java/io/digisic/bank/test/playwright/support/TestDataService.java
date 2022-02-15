package io.digisic.bank.test.playwright.support;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.javafaker.Faker;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class TestDataService implements AutoCloseable {

    private static final String DEFAULT_PASSWORD = "Demo123!";
    private static final String ADMIN_USER = "admin@demo.io";
    private static final String ADMIN_PASS = "Demo123!";

    private final Playwright playwright;
    private final APIRequestContext api;
    private final String apiBaseUrl;
    private final ObjectMapper mapper = new ObjectMapper();
    private final Faker faker = new Faker(new Locale("en-US"));
    private String authToken;

    public TestDataService() {
        this.playwright = Playwright.create();
        this.apiBaseUrl = System.getProperty("api.base.url", "https://localhost:8443/bank");
        this.api = playwright.request().newContext(
                new APIRequest.NewContextOptions()
                        .setIgnoreHTTPSErrors(true));
    }

    public String getAuthToken() {
        ensureAuth();
        return authToken;
    }

    public int ensureRegisteredUser(String persona, String email) {
        ensureAuth();
        int existingId = findUserId(email);
        if (existingId > 0) {
            return existingId;
        }
        return registerUser(persona, email);
    }

    public int ensureRegisteredUserWithSsn(String persona, String email, String ssn) {
        ensureAuth();
        int existingId = findUserId(email);
        if (existingId > 0) {
            return existingId;
        }
        return registerUserWithSsn(persona, email, ssn);
    }

    public boolean validateRegisteredUser(String persona, String email) {
        return findUserId(email) > 0;
    }

    public int findUserId(String email) {
        ensureAuth();
        APIResponse res = api.get(apiBaseUrl + "/api/v1/user/find",
                RequestOptions.create()
                        .setQueryParam("username", email)
                        .setHeader("Authorization", "Bearer " + authToken)
                        .setHeader("Content-Type", "application/json"));
        if (res.status() != 200) {
            return -1;
        }
        try {
            JsonNode body = readJson(res);
            if (body == null || !body.hasNonNull("id")) {
                return -1;
            }
            return body.get("id").asInt();
        } catch (Exception e) {
            return -1;
        }
    }

    public int registerUser(String persona, String email) {
        return registerUserWithSsn(persona, email, faker.numerify("###-##-####"));
    }

    public int registerUserWithSsn(String persona, String email, String ssn) {
        ensureAuth();

        String gender = faker.demographic().sex().substring(0, 1);
        String title = gender.equals("F") ? "Mrs." : "Mr.";
        SimpleDateFormat formatDate = new SimpleDateFormat("MM/dd/yyyy");

        ObjectNode body = mapper.createObjectNode();
        body.put("address", faker.address().streetAddress());
        body.put("country", "US");
        body.put("dob", formatDate.format(faker.date().birthday()));
        body.put("emailAddress", email);
        body.put("firstName", persona);
        body.put("gender", gender);
        body.put("homePhone", faker.numerify("###-###-####"));
        body.put("lastName", faker.name().lastName());
        body.put("locality", faker.address().city());
        body.put("mobilePhone", faker.numerify("###-###-####"));
        body.put("password", DEFAULT_PASSWORD);
        body.put("postalCode", faker.address().zipCode().split("-")[0]);
        body.put("region", faker.address().stateAbbr());
        body.put("ssn", ssn);
        body.put("title", title);
        body.put("workPhone", faker.numerify("###-###-####"));

        APIResponse create = api.post(apiBaseUrl + "/api/v1/user",
                RequestOptions.create()
                        .setQueryParam("role", "USER")
                        .setHeader("Authorization", "Bearer " + authToken)
                        .setHeader("Content-Type", "application/json")
                        .setData(body.toString()));
        if (create.status() != 201) {
            throw new IllegalStateException("registerUser failed: HTTP " + create.status() + " " + create.text());
        }
        int id = readJson(create).get("id").asInt();

        APIResponse role = api.put(apiBaseUrl + "/api/v1/user/" + id + "/role",
                RequestOptions.create()
                        .setQueryParam("role", "API")
                        .setHeader("Authorization", "Bearer " + authToken)
                        .setHeader("Content-Type", "application/json"));
        if (role.status() != 200) {
            throw new IllegalStateException("grant API role failed: HTTP " + role.status() + " " + role.text());
        }
        return id;
    }

    public void lockAccount(int userId) {
        mutateUserState(userId, "/state/unlock", "unlock");
    }

    public void expireAccount(int userId) {
        mutateUserState(userId, "/state/unexpire", "unexpire");
    }

    public void disableAccount(int userId) {
        mutateUserState(userId, "/state/enable", "enabled");
    }

    public void expireCredentials(int userId) {
        mutateUserState(userId, "/password/unexpire", "unexpire");
    }

    private void mutateUserState(int userId, String pathSuffix, String queryParam) {
        ensureAuth();
        APIResponse res = api.put(apiBaseUrl + "/api/v1/user/" + userId + pathSuffix,
                RequestOptions.create()
                        .setQueryParam(queryParam, "false")
                        .setHeader("Authorization", "Bearer " + authToken)
                        .setHeader("Content-Type", "application/json"));
        if (res.status() != 204) {
            throw new IllegalStateException(
                    "user state mutation " + pathSuffix + " failed: HTTP " + res.status() + " " + res.text());
        }
    }

    private void ensureAuth() {
        if (authToken == null || authToken.isEmpty()) {
            authenticateAPI();
        }
    }

    private void authenticateAPI() {
        APIResponse res = api.post(apiBaseUrl + "/api/v1/auth",
                RequestOptions.create()
                        .setQueryParam("username", ADMIN_USER)
                        .setQueryParam("password", ADMIN_PASS)
                        .setHeader("Content-Type", "application/json"));
        if (res.status() != 200) {
            throw new IllegalStateException("authenticateAPI failed: HTTP " + res.status() + " " + res.text());
        }
        authToken = readJson(res).get("authToken").asText();
    }

    private JsonNode readJson(APIResponse res) {
        try {
            return mapper.readTree(res.body());
        } catch (Exception e) {
            throw new IllegalStateException("JSON parse failed", e);
        }
    }

    @Override
    public void close() {
        if (api != null) {
            api.dispose();
        }
        if (playwright != null) {
            playwright.close();
        }
    }
}
