package io.digisic.bank.test.junit.model;

import io.digisic.bank.model.Notification;
import io.digisic.bank.model.enums.NotificationType;
import io.digisic.bank.model.security.Users;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NotificationTest {
    /**
     * Verifies REQ-5
     */
    @Test
    void testNotificationsAreComplete(){
        long id = 101;
        String content = "You have a new message!";
        NotificationType notificationType = NotificationType.WARNING;
        Date date= Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC));
        // Mock or set up a user profile
        Users user = new Users();
        user.setId(55L);

        // Create the Notification instance and set its fields
        Notification notification = new Notification();
        notification.setId(id);
        notification.setContent(content);
        notification.setNotificationType(notificationType);
        notification.setTimestamp(date);
        notification.setUsers(user);

        // Construct the expected output
        String expectedOutput = "Notification [notificationId=" + id +
                ", content=" + content +
                ", notificationType=" + notificationType +
                ", timestamp=" + date +
                ", userProfile=" + user.getId() + "]";

        // Act: Call the toString() method
        String actualOutput = notification.toString();

        // Assert: Verify the actual output matches the expected output
        assertEquals(expectedOutput, actualOutput);
    }
}
