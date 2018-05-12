package ru.stqa.training.selenium;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.Random;

import static org.junit.Assert.assertTrue;

public class Task12 {

    private static final String ADMIN_PAGE_URL = "http://localhost/litecart/admin/";

    private static final By USERNAME = By.name("username");
    private static final By PASSWORD = By.name("password");
    private static final By LOGIN = By.name("login");

    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin";

    private WebDriver driver;

    @Before
    public void start() {
        driver = new ChromeDriver();
    }

    public static String getRandomString(Integer lengthString) {
        Random r = new Random();
        String eng = "abcdefghijklmnopqrstuvwxyz";
        String str = "";

        for (int i = 0; i < lengthString; i++) {
            str = str +  eng.charAt(r.nextInt(eng.length()));
        }

        return str;
    }

    public static String getRandomIntStr(Integer lengthString) {
        Random r = new Random();
        String dig = "0123456789";
        String str = "";

        for (int i = 0; i < lengthString; i++) {
            str = str +  dig.charAt(r.nextInt(dig.length()));
        }

        return str;
    }

    public static Integer getRandomInt(Integer max) {
        return (int)Math.floor( Math.random() * max );
    }

    //Задержка в мс
    public void Delay(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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

    public void NavigateToCatalog() {
        driver.findElement(By.linkText("Catalog")).click();
    }

    public String AddNewProduct() {
        driver.findElement(By.partialLinkText("Add New Product")).click();

        String name = "Durk " + getRandomString(10);
        String code = getRandomString(5);
        String quantity = getRandomInt(10) + "";

        driver.findElement(By.cssSelector("[name=status]")).click();
        driver.findElement(By.cssSelector("[name^=name]")).sendKeys(name);
        driver.findElement(By.cssSelector("[name=code]")).sendKeys(code);
        driver.findElement(By.cssSelector("[name=quantity]")).sendKeys(quantity);

        String imagePath = System.getProperty("user.dir") + "\\duck-test.png";

        driver.findElement(By.cssSelector("[name^=new_images]")).sendKeys(imagePath);

        // Закладка Information
        driver.findElement(By.cssSelector("[href*=tab-information]")).click();
        Delay(2000);

        String shortDescription = getRandomString(20);
        String description = getRandomString(50);

        driver.findElement(By.cssSelector("[name^=short_description]")).sendKeys(shortDescription);
        driver.findElement(By.cssSelector("[name^=description]")).sendKeys(description);

        // Закладка Prices
        driver.findElement(By.cssSelector("[href*=tab-prices]")).click();
        Delay(2000);

        String purchase_price = getRandomIntStr(2);
        int currencyId = 1+ getRandomInt(2);

        driver.findElement(By.cssSelector("[name=purchase_price]")).sendKeys(purchase_price);

        Select selectCurrency = new Select(driver.findElement(By.cssSelector("[name=purchase_price_currency_code")));
        selectCurrency.selectByIndex(currencyId);

        driver.findElement(By.cssSelector("[name=save]")).click();

        return name;
    }

    @Test
    public void Test1() {
        LoginAdmin();
        NavigateToCatalog();

        String productName = AddNewProduct();

        assertTrue(driver.findElement(By.cssSelector(".dataTable")).findElements(By.linkText(productName)).size() == 1);
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
