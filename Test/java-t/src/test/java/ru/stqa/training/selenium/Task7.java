package ru.stqa.training.selenium;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.Assert.assertTrue;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleContains;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class Task7 {

    private static final By USERNAME = By.name("username");
    private static final By PASSWORD = By.name("password");
    private static final By LOGIN = By.name("login");
    private static final By MENU = By.cssSelector("ul#box-apps-menu li#app-");
    private static final By SUB_MENU = By.cssSelector("ul#box-apps-menu li#app- li");

    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin";

    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
    }

    boolean isElementPresent(By locator) {
        return driver.findElements(locator).size() > 0;
    }

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
        driver.get("http://localhost/litecart/admin/");
        getUsername().sendKeys(ADMIN_USERNAME);
        getPassword().sendKeys(ADMIN_PASSWORD);
        getLogin().click();
        wait.until(titleIs("Denis"));
    }

    private List<WebElement> getMenu() {
        return driver.findElements(MENU);
    }

    private List<WebElement> getSubMenu() {
        return driver.findElements(SUB_MENU);
    }

    @Test
    public void Lesson4() {
        LoginAdmin();

        for (int i = 0; i < getMenu().toArray().length; i++) {
            getMenu().get(i).click();

            for (int j = 0; j < getSubMenu().toArray().length; j++) {
                getSubMenu().get(j).click();
                assertTrue(isElementPresent(By.cssSelector("h1")));
            }
        }
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
