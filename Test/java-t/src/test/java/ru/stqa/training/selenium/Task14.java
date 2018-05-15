package ru.stqa.training.selenium;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Set;

public class Task14 {

    private static final String ADMIN_PAGE_URL = "http://localhost/litecart/admin/";

    private static final By USERNAME = By.name("username");
    private static final By PASSWORD = By.name("password");
    private static final By LOGIN = By.name("login");

    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin";

    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
    }

    /*
    Логин в админку интернет-магазина
     */
    private WebElement getUsername() {
        return driver.findElement(USERNAME);
    }

    private WebElement getPassword() {
        return driver.findElement(PASSWORD);
    }

    private WebElement getLogin() {
        return driver.findElement(LOGIN);
    }

    public void LoginAdmin() {
        driver.get(ADMIN_PAGE_URL);
        getUsername().sendKeys(ADMIN_USERNAME);
        getPassword().sendKeys(ADMIN_PASSWORD);
        getLogin().click();
    }

    public void NavigateToCountries() {
        driver.findElement(By.linkText("Countries")).click();
    }

    public ExpectedCondition<String> getThereIsWindowOtherThan(Set<String> oldWindows) {
        return  new ExpectedCondition<String>() {
            public String apply(WebDriver driver) {
                Set<String> handles = driver.getWindowHandles();
                handles.removeAll(oldWindows);
                return handles.size() > 0 ? handles.iterator().next() : null;
            }
        };
    }

    @Test
    public void Test1() {
        LoginAdmin();
        NavigateToCountries();
        driver.findElement(By.partialLinkText("Add New Country")).click();

        List<WebElement> links = driver.findElements(By.cssSelector("#content [class*=external-link]"));
        int linksCount = links.size();

        for (int i = 0; i < linksCount; i++) {
            WebElement link = driver.findElements(By.cssSelector("#content [class*=external-link]")).get(i);
            String mainWindow = driver.getWindowHandle();
            Set<String> oldWindows = driver.getWindowHandles();

            link.click();

            String newWindow = wait.until(getThereIsWindowOtherThan(oldWindows));
            driver.switchTo().window(newWindow);
            driver.close();
            driver.switchTo().window(mainWindow);
        }
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
