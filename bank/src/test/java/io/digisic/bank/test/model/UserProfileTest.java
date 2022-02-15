package io.digisic.bank.test.junit.model;

import io.digisic.bank.model.UserProfile;
import io.digisic.bank.util.Patterns;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserProfileTest {
    /**
     * Verifies REQ-7
     */
    @Test
    void testUserProfileProperties() {
        // Arrange: Define known values for each field
        Long id = 1L;
        String firstName = "John";
        String lastName = "Doe";
        String title = "Mr.";
        String gender = "Male";
        String ssn = "123-45-6789";

        Date dob = new Date(90, 1, 15); // Date representing 1990-02-15 (year is 1900 + 90)
        Date dom = new Date(110, 5, 20); // Date representing 2010-06-20 (year is 1900 + 110)

        String emailAddress = "johndoe@example.com";
        String homePhone = "555-1234";
        String mobilePhone = "555-5678";
        String workPhone = "555-8765";
        String address = "123 Main St";
        String locality = "Springfield";
        String region = "IL";
        String postalCode = "62704";
        String country = "USA";

        // Set up the UserProfile instance with these values
        UserProfile userProfile = new UserProfile();
        userProfile.setId(id);
        userProfile.setFirstName(firstName);
        userProfile.setLastName(lastName);
        userProfile.setTitle(title);
        userProfile.setGender(gender);
        userProfile.setSsn(ssn);
        userProfile.setDob(dob);
        userProfile.setDom(dom);
        userProfile.setEmailAddress(emailAddress);
        userProfile.setHomePhone(homePhone);
        userProfile.setMobilePhone(mobilePhone);
        userProfile.setWorkPhone(workPhone);
        userProfile.setAddress(address);
        userProfile.setLocality(locality);
        userProfile.setRegion(region);
        userProfile.setPostalCode(postalCode);
        userProfile.setCountry(country);

        // Format dates to match toString output


        // Construct the expected output
        String expectedOutput = "\n\nUser Profile ***********************" +
                "\nId:\t\t\t" + id +
                "\nMemeber Since:\t\t" + dom +
                "\nTitle:\t\t\t" + title +
                "\nFrist Name:\t\t" + firstName +
                "\nLast Name:\t\t" + lastName +
                "\nEmail Address:\t\t" + emailAddress +
                "\nSSN:\t\t\t" + ssn +
                "\nDOB:\t\t\t" + dob +
                "\nGender:\t\t\t" + gender +
                "\nHome Phone:\t\t" + homePhone +
                "\nMobile Phone:\t\t" + mobilePhone +
                "\nWork Phone:\t\t" + workPhone +
                "\nAddress:\t\t" + address +
                "\nLocality:\t\t" + locality +
                "\nRegion:\t\t\t" + region +
                "\nPostal Code:\t\t" + postalCode +
                "\nCountry:\t\t" + country +
                "\n*******************************************\n";

        // Act: Call the toString() method
        String actualOutput = userProfile.toString();

        // Assert: Verify the actual output matches the expected output
        assertEquals(expectedOutput, actualOutput);
    }
}
