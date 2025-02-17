package utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
    private static final ChromeOptions chromeOptions = new ChromeOptions();
    public static WebDriver createDriver() {
        chromeOptions.addArguments("--headless", "--window-size=1920,1080");
        chromeOptions.addArguments("--no-sandbox");
        chromeOptions.addArguments("--disable-dev-shm-usage");
        chromeOptions.addArguments("--disable-extensions");
        return new ChromeDriver(chromeOptions);
    }

    public static void get(WebDriver driver) {
        driver.get("https://centerfiress.com/events/");
    }
    public static void takeScreenshot(WebDriver driver, String methodName, String className) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy_MM_dd_hhmmss");
        String date = simpleDateFormat.format(new Date());

        File file = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(file, new File(String.format("screenshots/screenshot_%s_%s_%s.png", className, methodName, date)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void log(String str) {
        System.out.println(str);
    }

    public static void logf(String str, Object... arr) {
        System.out.printf(str, arr);
        System.out.println();
    }

    public static WebElement safeFindElement(WebElement parent, By locator) {
        try {
            return parent.findElement(locator);
        } catch (NoSuchElementException e) {
            return null;
        }
    }
}
