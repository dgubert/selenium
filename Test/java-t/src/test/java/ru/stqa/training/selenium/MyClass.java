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

    @Before
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
    }

    @Test
    public void myTest() {
        driver.get("http://youtube.com");
        driver.findElement(By.name("search_query")).sendKeys("webdriver");
        driver.findElement(By.id("search-icon-legacy")).click();
        wait.until(titleIs("webdriver - YouTube"));
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
