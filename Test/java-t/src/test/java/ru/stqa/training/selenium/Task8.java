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
import org.w3c.dom.ls.LSException;

import java.util.List;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.Assert.assertTrue;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class Task8 {

    private static final By PRODUCT = By.cssSelector(".listing-wrapper .link");
    private static final By STICKER = By.cssSelector(".listing-wrapper .link .sticker");

    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
        driver = new ChromeDriver();
    }

    /*
Проверка наличия стикеров у всех товаров на главной странице litecart.
У каждого товара имеется ровно 1 стикер.
 */
    
    @Test
    public void Test1() {
        driver.get("http://localhost/litecart/en/");
        List<WebElement> products = driver.findElements(PRODUCT);

        for (int i = 0; i < products.toArray().length; i++) {
            assertTrue(products.get(i).findElements(STICKER).size() == 1);
        }

    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
