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

    public EventPage(WebDriver driver) {
        super(driver);
    }

    public boolean goToNextPage(int pageNumber) {
        try {
            WebElement nextPageButton = getDriver().findElement(By.cssSelector("a.tribe-events-c-nav__next"));
            String previousUrl = getDriver().getCurrentUrl();
            nextPageButton.click();
            waitForEventsToLoad(previousUrl);
            Utils.log("Navigated to page " + (pageNumber + 1));
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public String getAvailableEventDate() {
        for (WebElement event : eventBlocks) {
            String title = event.findElement(By.cssSelector("h3.tribe-events-calendar-list__event-title a")).getText();
            if (title.contains(EVENT_NAME)) {
                WebElement getTicketsLink = Utils.safeFindElement(event, By.cssSelector("a.tribe-events-c-small-cta__link"));
                WebElement dateElement = event.findElement(By.cssSelector("span.tribe-event-date-start"));
                if (getTicketsLink != null) {
                    Utils.log("Available tickets found for: " + title + ". Date: " + dateElement.getText());
                    return dateElement.getText();
                } else {
                    Utils.log(EVENT_NAME + " is found, but tickets are sold out for " + dateElement.getText());
                }
            }
        }
        return "";
    }

    public void waitForEventsToLoad(String previousUrl) {
        getWait10().until(ExpectedConditions.not(ExpectedConditions.urlToBe(previousUrl)));
    }
}
