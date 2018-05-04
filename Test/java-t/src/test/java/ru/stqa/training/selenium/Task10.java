package ru.stqa.training.selenium;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.w3c.dom.ls.LSException;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.Assert.*;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class Task10 {

    private static final By PRODUCT = By.cssSelector("#box-campaigns .link");
    private static final By PRODUCT_NAME = By.cssSelector(".name");
    private static final By PRODUCT_TITLE = By.cssSelector("h1.title");
    private static final By PRODUCT_REGULAR_PRICE = By.cssSelector(".regular-price");
    private static final By PRODUCT_CAMPAIGN_PRICE = By.cssSelector(".campaign-price");

    private WebDriver driver;

    @Before
    public void start() {
        driver = new ChromeDriver();
        //driver = new InternetExplorerDriver();
        //driver = new FirefoxDriver();
    }

    public String getName(WebElement el) {
        return el.getAttribute("textContent");
    }

    public String getPrice(WebElement el) {
        return el.getAttribute("textContent");
    }

    public String getCssValue(WebElement el, String property) {
        return el.getCssValue(property);
    }

    public int getFontWeight(WebElement el) {
        return Integer.parseInt(getCssValue(el,"font-weight"));
    }

    public List<String> getColor(WebElement el) {
        List<String> colors = new ArrayList<String>();
        String str = getCssValue(el,"color");
        Pattern pat= Pattern.compile("\\d+");
        Matcher m = pat.matcher(str);
        while (m.find()) {
            colors.add(m.group());
        }

        return colors;
    }

    public boolean isGrey(WebElement el) {
        List<String> color = getColor(el);

        return color.get(0).compareTo(color.get(1)) == 0 && color.get(0).compareTo(color.get(2)) == 0;
    }

    public boolean isRed(WebElement el) {
        List<String> color = getColor(el);

        return color.get(1).compareTo(color.get(2)) == 0 && color.get(1).compareTo("0") == 0;
    }

    public boolean isBold(WebElement el) {
        return getFontWeight(el) == 700;
    }

    public boolean isNormal(WebElement el) {
        return getFontWeight(el) == 400;
    }

    public boolean isLineThrough(WebElement el) {
        return getCssValue(el,"text-decoration-line").compareTo("line-through") == 0;
    }
    /*
    нужно открыть главную страницу, выбрать первый товар в блоке Campaigns и проверить следующее:

а) на главной странице и на странице товара совпадает текст названия товара
б) на главной странице и на странице товара совпадают цены (обычная и акционная)
в) обычная цена зачёркнутая и серая (можно считать, что "серый" цвет это такой, у которого в RGBa представлении одинаковые значения для каналов R, G и B)
г) акционная жирная и красная (можно считать, что "красный" цвет это такой, у которого в RGBa представлении каналы G и B имеют нулевые значения)
(цвета надо проверить на каждой странице независимо, при этом цвета на разных страницах могут не совпадать)
д) акционная цена крупнее, чем обычная (это тоже надо проверить на каждой странице независимо)
     */
    @Test
    public void Test1() {
        driver.get("http://localhost/litecart/en/");
        WebElement product = driver.findElement(PRODUCT);
        WebElement regularPrice = product.findElement(PRODUCT_REGULAR_PRICE);
        WebElement campaignPrice = product.findElement(PRODUCT_CAMPAIGN_PRICE);

        String name = getName(product.findElement(PRODUCT_NAME));
        String regularPriceValue = getPrice(regularPrice);
        String campaignPriceValue = getPrice(campaignPrice);

        assertTrue(isLineThrough(regularPrice));
        assertTrue(isGrey(regularPrice));
        assertTrue(isRed(campaignPrice));
        assertTrue(isNormal(regularPrice));
        assertTrue(isBold(campaignPrice));
        assertTrue( getFontWeight(campaignPrice) > getFontWeight(regularPrice));
        product.click();

        regularPrice = driver.findElement(PRODUCT_REGULAR_PRICE);
        campaignPrice = driver.findElement(PRODUCT_CAMPAIGN_PRICE);

        assertEquals(getName(driver.findElement(PRODUCT_TITLE)), name);
        assertEquals(getPrice(regularPrice), regularPriceValue);
        assertEquals(getPrice(campaignPrice), campaignPriceValue);
        assertTrue(isLineThrough(regularPrice));
        assertTrue(isGrey(regularPrice));
        assertTrue(isRed(campaignPrice));
        assertTrue(isNormal(regularPrice));
        assertTrue(isBold(campaignPrice));
        assertTrue( getFontWeight(campaignPrice) > getFontWeight(regularPrice));
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
