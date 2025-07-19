package tests;

import io.qameta.allure.*;
import model.EventPage;
import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("Course search")
@Feature("Tickets availability check")
public class EventTest extends BaseTest {
    private static final int MAX_PAGES_SEARCHED = 4;

    @Test(
            description = "Verify that tickets are not available for 'Basic Pistol Shooting Course'",
            groups = {"Smoke"}
    )
    @Severity(SeverityLevel.CRITICAL)
    @Story("Check ticket availability across multiple pages")
    public void testBasicPistolShootingCourseTickets() {
        boolean ticketsFound = false;
        String eventDate = "";

        EventPage eventPage = new EventPage(getDriver());
        for (int i = 1; i < MAX_PAGES_SEARCHED; i++) {
            stepLog("Check tickets on page " + i);
            String currentUrl = getDriver().getCurrentUrl();

            eventDate = eventPage.getAvailableEventDate();
            if (!eventDate.isEmpty()) {
                ticketsFound = true;
                break;
            }

            if (!eventPage.goToNextPage(i)) {
                stepLog("No more pages available.");
                break;
            }

            eventPage.waitForEventsToLoad(currentUrl);
        }

        Assert.assertFalse(ticketsFound,
                "Tickets are available for 'Basic Pistol Shooting Course' on " + eventDate + ". Test failed.");
    }

    @Step("{0}")
    private void stepLog(String message) {
    }
}
