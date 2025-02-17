package tests;

import model.EventPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class EventTest extends BaseTest {
    @Test
    public void testBasicPistolShootingCourseTickets() {
        EventPage eventPage = new EventPage(getDriver());
        boolean ticketsFound = false;
        String eventDate = "";

        for (int i = 1; i < 4; i++) {
            String currentUrl = getDriver().getCurrentUrl();
            eventDate = eventPage.getAvailableEventDate();
            if (!eventDate.isEmpty()) {
                ticketsFound = true;
                break;
            }
            if (!eventPage.goToNextPage(i)) {
                break;
            }
            eventPage.waitForEventsToLoad(currentUrl);
        }

        Assert.assertFalse(ticketsFound,
                "Tickets are available for 'Basic Pistol Shooting Course' on " + eventDate + ". Test failed.");
    }
}
