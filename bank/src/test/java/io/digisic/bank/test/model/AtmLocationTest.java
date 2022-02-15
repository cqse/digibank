package io.digisic.bank.test.junit.model;

import io.digisic.bank.model.AtmLocation;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

class AtmLocationTest {

    /**
     * Verifies REQ-8
     */
    @Test
    void testAllAtmLocationFields() {
        // Arrange: Set up known values for each field
        String name = "Central Bank ATM";
        String description = "24-hour ATM";
        String street = "123 Main St";
        String city = "Springfield";
        String state = "IL";
        String zipcode = "62704";
        String country = "USA";
        BigDecimal distance = new BigDecimal("0.5");
        String latitude = "39.7817";
        String longitude = "-89.6501";

        // Create an instance of AtmLocation and set its fields
        AtmLocation atmLocation = new AtmLocation();
        atmLocation.setName(name);
        atmLocation.setDescription(description);
        atmLocation.setStreet(street);
        atmLocation.setCity(city);
        atmLocation.setState(state);
        atmLocation.setZipcode(zipcode);
        atmLocation.setCountry(country);
        atmLocation.setDistance(distance);
        atmLocation.setLatitude(latitude);
        atmLocation.setLongitude(longitude);

        // Construct the expected output
        String expectedOutput = "AtmLocation {" +
                "\nName:\t\t" + name +
                "\nDescription:\t" + description +
                "\nStreet:\t\t" + street +
                "\nCity:\t\t" + city +
                "\nState:\t\t" + state +
                "\nZipcode:\t\t" + zipcode +
                "\nCountry:\t\t" + country +
                "\nDistance:\t" + distance +
                "\nLatitude:\t" + latitude +
                "\nLongitude:\t" + longitude +
                "\n}";

        // Act: Call the toString() method
        String actualOutput = atmLocation.toString();

        // Assert: Check that the actual output matches the expected output
        assertEquals(expectedOutput, actualOutput);
    }
}
