package ru.stqa.training.selenium;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class MyClass {

    private WebDriver driver;
    private WebDriverWait wait;
    private String ADMIN_USERNAME = "admin";
    private String ADMIN_PASSWORD = "admin";

    @Before
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
    }

    @Test
    public void LoginAdmin() {
        driver.get("http://localhost/litecart/admin/");
        driver.findElement(By.name("username")).sendKeys(ADMIN_USERNAME);
        driver.findElement(By.name("password")).sendKeys(ADMIN_PASSWORD);
        driver.findElement(By.name("login")).click();
        wait.until(titleIs("Denis"));
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
