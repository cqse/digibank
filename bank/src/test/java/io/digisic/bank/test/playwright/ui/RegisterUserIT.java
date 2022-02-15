package io.digisic.bank.test.playwright.ui;

import io.digisic.bank.test.playwright.pages.HomePage;
import io.digisic.bank.test.playwright.pages.LoginPage;
import io.digisic.bank.test.playwright.pages.RegisterUserPage;
import io.digisic.bank.test.playwright.support.PlaywrightBaseIT;
import io.digisic.bank.test.playwright.support.TestDataService;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RegisterUserIT extends PlaywrightBaseIT {

    private RegisterUserPage openRegistration() {
        RegisterUserPage reg = new RegisterUserPage(page, UI_BASE_URL);
        reg.navigate();
        return reg;
    }

    private void assertStillOnIdentity(RegisterUserPage reg) {
        assertThat(reg.isOnIdentityPage())
                .as("registration form should remain on the identity (signup) page when validation prevents advancement")
                .isTrue();
    }

    private void assertStillOnContactDetails(RegisterUserPage reg) {
        assertThat(reg.isOnContactDetailsPage())
                .as("registration form should remain on the contact details page when validation prevents submission")
                .isTrue();
    }

    private void assertRegistrationSucceeded(String email, String password) {
        LoginPage login = new LoginPage(page, UI_BASE_URL);
        page.locator(".alert-success").waitFor();
        assertThat(login.successBannerVisible())
                .as("registration success banner should be rendered after submitting contact details form (server forwards to login template without redirecting)")
                .isTrue();
        assertThat(login.usernameValue())
                .as("login username field should be pre-filled with the freshly registered email")
                .contains(email);
        login.enterPassword(password);
        login.submit();
        page.waitForURL(url -> url.contains("/home"));
        assertThat(new HomePage(page).isAt())
                .as("registered user should land on home page after first login")
                .isTrue();
    }

    @Test
    void registerWithEmptyEmailAddress() {
        RegisterUserPage reg = openRegistration();
        reg.selectTitle("Mr.");
        reg.enterFirstName("Darwin");
        reg.enterLastName("Sweet");
        reg.selectGender("M");
        reg.enterDateOfBirth("02/13/1962");
        reg.enterSsn("053-85-3581");
        reg.enterEmailAddress("");
        reg.clickNext();
        assertStillOnIdentity(reg);
    }

    @Test
    void registerWithEmailMissingAtSign() {
        RegisterUserPage reg = openRegistration();
        reg.selectTitle("Mr.");
        reg.enterFirstName("Timothy");
        reg.enterLastName("Singleton");
        reg.selectGender("M");
        reg.enterDateOfBirth("04/02/1929");
        reg.enterSsn("884-52-8963");
        reg.enterEmailAddress("Timothy9122gmail.com");
        reg.clickNext();
        assertStillOnIdentity(reg);
    }

    @Test
    void registerWithEmailMissingDomain() {
        RegisterUserPage reg = openRegistration();
        reg.selectTitle("Mr.");
        reg.enterFirstName("Kevon");
        reg.enterLastName("Maldonado");
        reg.selectGender("M");
        reg.enterDateOfBirth("10/06/1999");
        reg.enterSsn("049-62-4002");
        reg.enterEmailAddress("Kevon4060@");
        reg.clickNext();
        assertStillOnIdentity(reg);
    }

    @Test
    void registerWithEmptyConfirmPassword() {
        RegisterUserPage reg = openRegistration();
        reg.selectTitle("Mr.");
        reg.enterFirstName("Eli");
        reg.enterLastName("Delaney");
        reg.selectGender("M");
        reg.enterDateOfBirth("03/02/1947");
        reg.enterSsn("289-84-0875");
        reg.enterEmailAddress("Eli16516@gmail.com");
        reg.enterPassword("tECOWVTa311");
        reg.enterConfirmPassword("");
        reg.clickNext();
        assertStillOnIdentity(reg);
    }

    @Test
    void registerWithNonMatchingConfirmPassword() {
        RegisterUserPage reg = openRegistration();
        reg.selectTitle("Mr.");
        reg.enterFirstName("Stephan");
        reg.enterLastName("Austin");
        reg.selectGender("M");
        reg.enterDateOfBirth("02/17/1949");
        reg.enterSsn("304-34-8410");
        reg.enterEmailAddress("Stephan16569@gmail.com");
        reg.enterPassword("QrCIgHgksXKWoixkfTl0");
        reg.enterConfirmPassword("DifferentPassword9");
        reg.clickNext();
        assertStillOnIdentity(reg);
    }

    @Test
    void registerWithEmptyRegion() {
        RegisterUserPage reg = openRegistration();
        fillValidIdentity(reg, "Mr.", "Reece", "Odonnell", "M", "08/16/1985", "973-07-8028",
                "Reece513209@gmail.com", "jGYUHfrrF2432");
        reg.clickNext();
        assertThat(reg.isOnContactDetailsPage()).isTrue();
        reg.enterAddress("UNIT 21 8304 11TH AVE SW");
        reg.enterLocality("Mosheim");
        reg.enterRegion("");
        reg.clickRegister();
        assertStillOnContactDetails(reg);
    }

    @Test
    void registerWithEmptyPostalCode() {
        RegisterUserPage reg = openRegistration();
        fillValidIdentity(reg, "Mr.", "Lamar", "Stephenson", "M", "01/18/1935", "920-54-9299",
                "Lamar7327@gmail.com", "mRWMcBcvQCXDBpA918");
        reg.clickNext();
        reg.enterAddress("BO CALZADA BUZON 84");
        reg.enterLocality("Auburn");
        reg.enterRegion("NE");
        reg.enterPostalCode("");
        reg.clickRegister();
        assertStillOnContactDetails(reg);
    }

    @Test
    void registerWithEmptyCountry() {
        RegisterUserPage reg = openRegistration();
        fillValidIdentity(reg, "Mr.", "Wilson", "Wyatt", "M", "09/24/1964", "690-54-1339",
                "Wilson5235@gmail.com", "TMHIGqMlNpk26963");
        reg.clickNext();
        reg.enterAddress("10421 ROYAL OAK DRIVE");
        reg.enterLocality("Palmyra");
        reg.enterRegion("PA");
        reg.enterPostalCode("17078");
        reg.enterCountry("");
        reg.clickRegister();
        assertStillOnContactDetails(reg);
    }

    @Test
    void registerWithEmptyHomePhone() {
        RegisterUserPage reg = openRegistration();
        fillValidIdentity(reg, "Mr.", "Addison", "Humphrey", "M", "08/07/1956", "077-13-9593",
                "Addison5036@gmail.com", "bblECLove4");
        reg.clickNext();
        fillContact(reg, "HC 02 BOX 1551", "Marietta", "GA", "30060", "United States", "", "", "");
        reg.clickRegister();
        assertStillOnContactDetails(reg);
    }

    @Test
    void registerWithInvalidHomePhone() {
        RegisterUserPage reg = openRegistration();
        fillValidIdentity(reg, "Mr.", "Jaylen", "Hanson", "M", "04/08/1968", "520-54-2869",
                "Jaylen69674@gmail.com", "lPSqrxMaISNtgVjpk08");
        reg.clickNext();
        fillContact(reg, "ATALAYA R-9 ALTURAS DE MAYAGUE", "Olmito", "TX", "78575", "United States",
                "299463013", "", "");
        reg.clickRegister();
        assertStillOnContactDetails(reg);
    }

    @Test
    void registerWithoutAgreeingToTerms() {
        RegisterUserPage reg = openRegistration();
        fillValidIdentity(reg, "Mr.", "Scott", "Emerson", "M", "09/19/1950", "170-11-3181",
                "Scott2769@gmail.com", "ZXBzjhUhHGVOrdn91");
        reg.clickNext();
        fillContact(reg, "URB SAN VICENTE C13 #276", "Jeffersonville", "KY", "40337", "United States",
                "9046514952", "9046514952", "9046514952");
        // intentionally do not check agree-terms
        reg.clickRegister();
        assertStillOnContactDetails(reg);
    }

    @Test
    void registerWithEmptyWorkPhoneAndDeclinedTerms() {
        RegisterUserPage reg = openRegistration();
        fillValidIdentity(reg, "Mr.", "Ray", "Snow", "M", "04/06/1981", "303-88-0429",
                "Ray359115@gmail.com", "qDsYNbuaIzJqnIO29752");
        reg.clickNext();
        fillContact(reg, "P.O BOX 2477", "Seattle", "WA", "98184", "United States",
                "4781391155", "4781391155", "");
        reg.clickRegister();
        assertStillOnContactDetails(reg);
    }

    @Test
    void registerWithInvalidWorkPhone() {
        RegisterUserPage reg = openRegistration();
        fillValidIdentity(reg, "Mr.", "Nicolas", "Odom", "M", "04/08/1928", "210-21-2863",
                "Nicolas4019@gmail.com", "MOMFVsyAry40");
        reg.clickNext();
        fillContact(reg, "APT 312 2605 COTE VERTU", "Lonedell", "MO", "63060", "United States",
                "8644643862", "8644643862", "5");
        reg.selectAgreeTerms(true);
        reg.clickRegister();
        assertStillOnContactDetails(reg);
    }

    @Test
    void registerSucceedsWithEmptyMobileAndWorkPhone() {
        String email = "Chance" + System.nanoTime() + "@gmail.com";
        String ssn = uniqueSsn();
        String password = "XLisTAoVRHjkczZkKJB34242";
        RegisterUserPage reg = openRegistration();
        fillValidIdentity(reg, "Mr.", "Chance", "Gregory", "M", "09/02/1983", ssn,
                email, password);
        reg.clickNext();
        fillContact(reg, "#13 ACACIA ST.", "Evadale", "TX", "77615", "United States",
                "2312065087", "", "");
        reg.selectAgreeTerms(true);
        reg.clickRegister();
        assertRegistrationSucceeded(email, password);
    }

    @Test
    void registerWithEmptyMobileAndInvalidWorkPhone() {
        RegisterUserPage reg = openRegistration();
        fillValidIdentity(reg, "Mr.", "Roy", "Walter", "M", "09/25/1989", "736-17-2288",
                "Roy5023@gmail.com", "IpnGqAtnXYWEnID6");
        reg.clickNext();
        fillContact(reg, "URB LOS JARDINES 68 CALLE HELE", "Kane", "PA", "16735", "United States",
                "8076226513", "", "84057");
        reg.clickRegister();
        assertStillOnContactDetails(reg);
    }

    @Test
    void registerWithInvalidMobilePhone() {
        RegisterUserPage reg = openRegistration();
        fillValidIdentity(reg, "Mr.", "Dario", "Kirby", "M", "12/20/1949", "898-70-2177",
                "Dario20233@gmail.com", "XKEuISmtnjA6011");
        reg.clickNext();
        fillContact(reg, "BO MAGINA CALLE PAPAYO BUZON", "Ferguson", "NC", "28624", "United States",
                "2701553271", "5046", "");
        reg.clickRegister();
        assertStillOnContactDetails(reg);
    }

    @Test
    void registerWithEmptyLocality() {
        RegisterUserPage reg = openRegistration();
        fillValidIdentity(reg, "Mr.", "Phillip", "Mosley", "M", "04/15/1974", "970-76-8510",
                "Phillip6594@gmail.com", "LikgAVVzNaRZxWv4");
        reg.clickNext();
        reg.enterAddress("10421 ROYAL OAK DRIVE");
        reg.enterLocality("");
        reg.clickRegister();
        assertStillOnContactDetails(reg);
    }

    @Test
    void registerWithEmptyAddress() {
        RegisterUserPage reg = openRegistration();
        fillValidIdentity(reg, "Mr.", "Gustavo", "Wood", "M", "07/31/1961", "815-61-9530",
                "Gustavo68973@gmail.com", "vIavpOZe356");
        reg.clickNext();
        reg.enterAddress("");
        reg.clickRegister();
        assertStillOnContactDetails(reg);
    }

    @Test
    void registerWithEmptyPassword() {
        RegisterUserPage reg = openRegistration();
        reg.selectTitle("Mr.");
        reg.enterFirstName("Jalen");
        reg.enterLastName("Baker");
        reg.selectGender("M");
        reg.enterDateOfBirth("03/17/1954");
        reg.enterSsn("155-96-6677");
        reg.enterEmailAddress("Jalen0579@gmail.com");
        reg.enterPassword("");
        reg.clickNext();
        assertStillOnIdentity(reg);
    }

    @Test
    void registerWithPasswordTooShort() {
        RegisterUserPage reg = openRegistration();
        reg.selectTitle("Mr.");
        reg.enterFirstName("Nicolas");
        reg.enterLastName("Moran");
        reg.selectGender("M");
        reg.enterDateOfBirth("08/09/1977");
        reg.enterSsn("392-07-1430");
        reg.enterEmailAddress("Nicolas2982@gmail.com");
        reg.enterPassword("PqKdZjB77");
        reg.clickNext();
        assertStillOnIdentity(reg);
    }

    @Test
    void registerWithPasswordMissingLowercase() {
        RegisterUserPage reg = openRegistration();
        reg.selectTitle("Mr.");
        reg.enterFirstName("Rogelio");
        reg.enterLastName("Tate");
        reg.selectGender("M");
        reg.enterDateOfBirth("04/04/1939");
        reg.enterSsn("368-08-2134");
        reg.enterEmailAddress("Rogelio7329@gmail.com");
        reg.enterPassword("GBWYBXMTXTEDZEFSDOLV8914");
        reg.clickNext();
        assertStillOnIdentity(reg);
    }

    @Test
    void registerWithPasswordMissingNumeric() {
        RegisterUserPage reg = openRegistration();
        reg.selectTitle("Mr.");
        reg.enterFirstName("Amarion");
        reg.enterLastName("Franks");
        reg.selectGender("M");
        reg.enterDateOfBirth("05/02/1926");
        reg.enterSsn("900-89-3261");
        reg.enterEmailAddress("Amarion401765@gmail.com");
        reg.enterPassword("ghCIdCMTJpNhoMtik");
        reg.clickNext();
        assertStillOnIdentity(reg);
    }

    @Test
    void registerWithPasswordMissingUppercase() {
        RegisterUserPage reg = openRegistration();
        reg.selectTitle("Mr.");
        reg.enterFirstName("Gilbert");
        reg.enterLastName("Diaz");
        reg.selectGender("M");
        reg.enterDateOfBirth("06/18/1948");
        reg.enterSsn("823-37-3935");
        reg.enterEmailAddress("Gilbert7139@gmail.com");
        reg.enterPassword("qxfqxhaexyteeelsdzbt8226");
        reg.clickNext();
        assertStillOnIdentity(reg);
    }

    @Test
    void registerWithAlreadyRegisteredEmailAddress() {
        String existingEmail = "ExistingEmailFixture@gmail.com";
        try (TestDataService data = new TestDataService()) {
            data.ensureRegisteredUser("Existing", existingEmail);
        }

        RegisterUserPage reg = openRegistration();
        reg.selectTitle("Mr.");
        reg.enterFirstName("Ahmad");
        reg.enterLastName("Logan");
        reg.selectGender("M");
        reg.enterDateOfBirth("01/01/1935");
        reg.enterSsn("480-55-9216");
        reg.enterEmailAddress(existingEmail);
        reg.enterPassword("ZfRTzEYNfbTfrKWzfC759");
        reg.enterConfirmPassword("ZfRTzEYNfbTfrKWzfC759");
        reg.clickNext();
        assertThat(reg.errorBannerVisible())
                .as("server should reject signup when email already exists for a registered user")
                .isTrue();
    }

    @Test
    void registerWithAlreadyRegisteredSsn() {
        String existingEmail = "ExistingSsnFixture@gmail.com";
        String existingSsn = "999-01-2345";
        try (TestDataService data = new TestDataService()) {
            data.ensureRegisteredUserWithSsn("Existing", existingEmail, existingSsn);
        }

        RegisterUserPage reg = openRegistration();
        reg.selectTitle("Mr.");
        reg.enterFirstName("Dylon");
        reg.enterLastName("Gillespie");
        reg.selectGender("M");
        reg.enterDateOfBirth("07/30/1985");
        reg.enterSsn(existingSsn);
        reg.enterEmailAddress("Dylon71996@gmail.com");
        reg.enterPassword("UZhHnfMPsw053");
        reg.enterConfirmPassword("UZhHnfMPsw053");
        reg.clickNext();
        assertThat(reg.errorBannerVisible())
                .as("server should reject signup when SSN already exists for a registered user")
                .isTrue();
    }

    @Test
    void registerWithEmptyDateOfBirthMaleGender() {
        RegisterUserPage reg = openRegistration();
        reg.selectTitle("Mr.");
        reg.enterFirstName("Brendon");
        reg.enterLastName("Sellers");
        reg.selectGender("M");
        reg.enterDateOfBirth("");
        reg.clickNext();
        assertStillOnIdentity(reg);
    }

    @Test
    void registerWithInvalidDateOfBirthMaleGender() {
        RegisterUserPage reg = openRegistration();
        reg.selectTitle("Mr.");
        reg.enterFirstName("Sammy");
        reg.enterLastName("Livingston");
        reg.selectGender("M");
        reg.enterDateOfBirth("1948-06-25nry");
        reg.clickNext();
        assertStillOnIdentity(reg);
    }

    @Test
    void registerWithSsnTooShort() {
        RegisterUserPage reg = openRegistration();
        reg.selectTitle("Mr.");
        reg.enterFirstName("Kale");
        reg.enterLastName("Estes");
        reg.selectGender("F");
        reg.enterDateOfBirth("06/18/1992");
        reg.enterSsn("7215929");
        reg.clickNext();
        assertStillOnIdentity(reg);
    }

    @Test
    void registerWithSsnTooLong() {
        RegisterUserPage reg = openRegistration();
        reg.selectTitle("Mr.");
        reg.enterFirstName("Estevan");
        reg.enterLastName("Knapp");
        reg.selectGender("F");
        reg.enterDateOfBirth("11/20/1933");
        reg.enterSsn("4350359958");
        reg.clickNext();
        assertStillOnIdentity(reg);
    }

    @Test
    void registerWithSsnContainingInvalidCharacters() {
        RegisterUserPage reg = openRegistration();
        reg.selectTitle("Mr.");
        reg.enterFirstName("Devin");
        reg.enterLastName("Weaver");
        reg.selectGender("F");
        reg.enterDateOfBirth("07/17/1987");
        reg.enterSsn("464$43$3336");
        reg.clickNext();
        assertStillOnIdentity(reg);
    }

    @Test
    void registerSucceedsWithFemaleGenderAndEmptyMobile() {
        String email = "Kaiden" + System.nanoTime() + "@gmail.com";
        String ssn = uniqueSsn();
        String password = "VImrCJsqDjnKrQL9";
        RegisterUserPage reg = openRegistration();
        fillValidIdentity(reg, "Mr.", "Kaiden", "Yang", "F", "09/19/1983", ssn,
                email, password);
        reg.clickNext();
        fillContact(reg, "HC 33 BOX 3003", "Pascagoula", "MS", "39581", "United States",
                "2898775330", "", "2898775330");
        reg.selectAgreeTerms(true);
        reg.clickRegister();
        assertRegistrationSucceeded(email, password);
    }

    @Test
    void registerWithEmptyDateOfBirthFemaleGender() {
        RegisterUserPage reg = openRegistration();
        reg.selectTitle("Mr.");
        reg.enterFirstName("Earl");
        reg.enterLastName("Riley");
        reg.selectGender("F");
        reg.enterDateOfBirth("");
        reg.clickNext();
        assertStillOnIdentity(reg);
    }

    @Test
    void registerWithInvalidDateOfBirthFemaleGender() {
        RegisterUserPage reg = openRegistration();
        reg.selectTitle("Mr.");
        reg.enterFirstName("Kareem");
        reg.enterLastName("Garner");
        reg.selectGender("F");
        reg.enterDateOfBirth("1987-07-06p");
        reg.clickNext();
        assertStillOnIdentity(reg);
    }

    @Test
    void registerWithoutSelectingGender() {
        RegisterUserPage reg = openRegistration();
        reg.selectTitle("Mr.");
        reg.enterFirstName("Derrick");
        reg.enterLastName("Bird");
        reg.selectGender("");
        reg.clickNext();
        assertStillOnIdentity(reg);
    }

    @Test
    void registerWithEmptyLastName() {
        RegisterUserPage reg = openRegistration();
        reg.selectTitle("Mr.");
        reg.enterFirstName("Travis");
        reg.enterLastName("");
        reg.clickNext();
        assertStillOnIdentity(reg);
    }

    @Test
    void registerWithEmptyFirstNameMrTitle() {
        RegisterUserPage reg = openRegistration();
        reg.selectTitle("Mr.");
        reg.enterFirstName("");
        reg.clickNext();
        assertStillOnIdentity(reg);
    }

    @Test
    void registerWithAlreadyRegisteredEmailAndSsnMsTitle() {
        String existingEmail = "ExistingComboFixture@gmail.com";
        String existingSsn = "999-04-5678";
        try (TestDataService data = new TestDataService()) {
            data.ensureRegisteredUserWithSsn("Existing", existingEmail, existingSsn);
        }

        RegisterUserPage reg = openRegistration();
        reg.selectTitle("Ms.");
        reg.enterFirstName("Ainsley");
        reg.enterLastName("Ortiz");
        reg.selectGender("M");
        reg.enterDateOfBirth("07/09/1991");
        reg.enterSsn(existingSsn);
        reg.enterEmailAddress(existingEmail);
        reg.enterPassword("SVrTFbTlynDnSO489");
        reg.enterConfirmPassword("SVrTFbTlynDnSO489");
        reg.clickNext();
        assertThat(reg.errorBannerVisible())
                .as("server should reject signup when both email and SSN match an existing user")
                .isTrue();
    }

    @Test
    void registerWithEmptyFirstNameMsTitle() {
        RegisterUserPage reg = openRegistration();
        reg.selectTitle("Ms.");
        reg.enterFirstName("");
        reg.clickNext();
        assertStillOnIdentity(reg);
    }

    @Test
    void registerWithEmptySsnMrsTitle() {
        RegisterUserPage reg = openRegistration();
        reg.selectTitle("Mrs.");
        reg.enterFirstName("Emilee");
        reg.enterLastName("Kirkland");
        reg.selectGender("F");
        reg.enterDateOfBirth("12/01/1929");
        reg.enterSsn("");
        reg.clickNext();
        assertStillOnIdentity(reg);
    }

    @Test
    void registerWithEmptyFirstNameMrsTitle() {
        RegisterUserPage reg = openRegistration();
        reg.selectTitle("Mrs.");
        reg.enterFirstName("");
        reg.clickNext();
        assertStillOnIdentity(reg);
    }

    @Test
    void registerWithoutSelectingTitle() {
        RegisterUserPage reg = openRegistration();
        reg.selectTitle("");
        reg.clickNext();
        assertStillOnIdentity(reg);
    }

    private void fillValidIdentity(RegisterUserPage reg, String title, String firstName, String lastName,
                                   String gender, String dob, String ssn, String email, String password) {
        reg.selectTitle(title);
        reg.enterFirstName(firstName);
        reg.enterLastName(lastName);
        reg.selectGender(gender);
        reg.enterDateOfBirth(dob);
        reg.enterSsn(ssn);
        reg.enterEmailAddress(email);
        reg.enterPassword(password);
        reg.enterConfirmPassword(password);
    }

    private void fillContact(RegisterUserPage reg, String address, String locality, String region,
                             String postalCode, String country, String homePhone, String mobilePhone,
                             String workPhone) {
        reg.enterAddress(address);
        reg.enterLocality(locality);
        reg.enterRegion(region);
        reg.enterPostalCode(postalCode);
        reg.enterCountry(country);
        reg.enterHomePhone(homePhone);
        reg.enterMobilePhone(mobilePhone);
        reg.enterWorkPhone(workPhone);
    }

    private String uniqueSsn() {
        long n = System.nanoTime();
        int last4 = (int) (n % 10000);
        int mid = (int) ((n / 10000) % 100);
        int first3 = 100 + (int) ((n / 1000000) % 800);
        return String.format("%03d-%02d-%04d", first3, mid, last4);
    }
}
