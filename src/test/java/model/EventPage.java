package model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.Utils;

import java.util.List;
import java.util.NoSuchElementException;

public class EventPage extends BasePage {
    private static final String EVENT_NAME = "Basic Pistol Shooting Course";

    @FindBy(css = "div.tribe-events-calendar-list__event-details")
    private List<WebElement> eventBlocks;

    @FindBy(css = "a.tribe-events-c-nav__next")
    private WebElement nextPageButton;

    public EventPage(WebDriver driver) {
        super(driver);
    }

    public String getAvailableEventDate() {
        for (WebElement event : eventBlocks) {
            String title = getEventTitle(event);
            if (title.contains(EVENT_NAME)) {
                WebElement ticketsLink = getTicketsLink(event);
                String date = getEventDate(event);
                if (ticketsLink != null) {
                    Utils.log("Available tickets found for: " + title + ". Date: " + date);
                    return date;
                } else {
                    Utils.log(EVENT_NAME + " is found, but tickets are sold out for " + date);
                    return "";
                }
            }
        }
        Utils.log("Tickets not found on the page.");
        return "";
    }

    public boolean goToNextPage(int pageNumber) {
        try {
            String previousUrl = getDriver().getCurrentUrl();
            nextPageButton.click();
            waitForEventsToLoad(previousUrl);
            Utils.log("Navigated to page " + (pageNumber + 1));
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public void waitForEventsToLoad(String previousUrl) {
        getWait10().until(ExpectedConditions.not(ExpectedConditions.urlToBe(previousUrl)));
    }

    private String getEventTitle(WebElement event) {
        return event.findElement(By.cssSelector("h3.tribe-events-calendar-list__event-title a")).getText();
    }

    private WebElement getTicketsLink(WebElement event) {
        return Utils.safeFindElement(event, By.cssSelector("a.tribe-events-c-small-cta__link"));
    }

    private String getEventDate(WebElement event) {
        return event.findElement(By.cssSelector("span.tribe-event-date-start")).getText();
    }
}
