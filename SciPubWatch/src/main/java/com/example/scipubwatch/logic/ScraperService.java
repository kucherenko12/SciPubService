package com.example.scipubwatch.logic;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.*;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.numberOfElementsToBeMoreThan;

public class ScraperService
{
    FirefoxDriver driver;
    WebDriverWait webDriverWait;  // selenium 3

    public ScraperService(String browserLocation, String driverLocation, int timeout)
    {
        driver = getFirefoxDriver(browserLocation, driverLocation);

        webDriverWait = new WebDriverWait(driver, timeout);  // selenium 3
    }

    public void getUrl(String url)
    {
        driver.get(url);
    }

    public String[] scrape (FindType findType, String text)
    {
        List<WebElement> webElementsList = null;

        if(findType == FindType.FIND_ELEMENT_BY_CLASS_NAME)    { webElementsList = driver.findElements(By.className(text)); }
        else if(findType == FindType.FIND_ELEMENT_BY_ID)       { webElementsList = driver.findElements(By.id(text)); }
        else if(findType == FindType.FIND_ELEMENT_BY_TAG_NAME) { webElementsList = driver.findElements(By.tagName(text)); }
        else if(findType == FindType.FIND_ELEMENT_BY_XPATH)    { webElementsList = driver.findElements(By.xpath(text)); }

        webElementsList.forEach (webElement -> System.out.println(webElement.getText()));

        String[] webElements = new String[webElementsList.size()];
        int i = 0;
        for (WebElement webElement : webElementsList)
        {
            webElements[i] = webElement.getText();
            i++;
        }
        return (webElements);
    }

    public static FirefoxDriver getFirefoxDriver(String browserLocation, String driverLocation)
    {
        System.setProperty("webdriver.firefox.bin", browserLocation);
        System.setProperty("webdriver.gecko.driver", driverLocation);
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--headless");

        //ProfilesIni profile = new ProfilesIni();
        //FirefoxProfile testprofile = profile.getProfile("debanjan");
        //FirefoxOptions opt = new FirefoxOptions();
        //opt.setProfile(testprofile);
        // OR =========================================================================
        options.setProfile(new FirefoxProfile());

        FirefoxDriver driver = new FirefoxDriver(options);
        return(driver);
    }

    public void stopFirefoxDriver() { driver.quit(); }

    public void clickButtonById(String id) { driver.findElement(By.id(id)).click(); }

    public void waitUntilNumberOfElementsToBeMoreThan(FindType findType, String identificator, int number)
    {
        if (findType == FindType.FIND_ELEMENT_BY_CLASS_NAME)
        { webDriverWait.until(numberOfElementsToBeMoreThan(By.className(identificator), number)); }
        else if (findType == FindType.FIND_ELEMENT_BY_ID)
        { webDriverWait.until(numberOfElementsToBeMoreThan(By.id(identificator), number)); }
        else if (findType == FindType.FIND_ELEMENT_BY_TAG_NAME)
        { webDriverWait.until(numberOfElementsToBeMoreThan(By.tagName(identificator), number)); }
        else if (findType == FindType.FIND_ELEMENT_BY_XPATH)
        { webDriverWait.until(numberOfElementsToBeMoreThan(By.xpath(identificator), 20)); }
    }

    public List<String[]> scrapeAndGet (FindType findType, String text)
    {
        List<WebElement> webElementsList = null;

        if(findType == FindType.FIND_ELEMENT_BY_CLASS_NAME)    { webElementsList = driver.findElements(By.className(text)); }
        else if(findType == FindType.FIND_ELEMENT_BY_ID)       { webElementsList = driver.findElements(By.id(text)); }
        else if(findType == FindType.FIND_ELEMENT_BY_TAG_NAME) { webElementsList = driver.findElements(By.tagName(text)); }
        else if(findType == FindType.FIND_ELEMENT_BY_XPATH)    { webElementsList = driver.findElements(By.xpath(text)); }

        List<String[]> data = new ArrayList<>();
        webElementsList.forEach (webElement -> data.add(webElement.getText().split("\n")));
        return(data);
    }

    public void scrollBy(int quantity)
    {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0, "+String.valueOf(quantity)+")", "");
    }

    public enum FindType
    {
        FIND_ELEMENT_BY_CLASS_NAME,
        FIND_ELEMENT_BY_ID,
        FIND_ELEMENT_BY_TAG_NAME,
        FIND_ELEMENT_BY_XPATH
    }

}