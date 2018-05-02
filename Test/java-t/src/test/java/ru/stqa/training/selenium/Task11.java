package ru.stqa.training.selenium;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.Random;

public class Task11 {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
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

    public static String getRandomEmail() {
        return getRandomString(10) + "@" + getRandomString(5) + "." + getRandomString(3);
    }

    public void CreateRandomCustomer(String email, String password) {
        driver.get("http://localhost/litecart/en/");
        driver.findElement(By.cssSelector("[name=login_form] [href*=create_account]")).click();

        String firstname = getRandomString(10);
        String lastname = getRandomString(10);
        String address1 = getRandomString(10);
        String postcode = getRandomIntStr(5);
        String city = getRandomString(10);
        String phone = getRandomIntStr(10);

        driver.findElement(By.cssSelector("[name=firstname]")).sendKeys(firstname);
        driver.findElement(By.cssSelector("[name=lastname]")).sendKeys(lastname);
        driver.findElement(By.cssSelector("[name=address1]")).sendKeys(address1);

        WebElement selectElemCountry = driver.findElement(By.cssSelector("[name=country_code]"));
        Select selectCountry = new Select(selectElemCountry);
        selectCountry.selectByValue("US");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebElement selectElemZone = driver.findElement(By.cssSelector("select[name=zone_code]"));
        Select selectZone = new Select(selectElemZone);
        selectZone.selectByIndex(getRandomInt(selectZone.getOptions().toArray().length - 1));

        driver.findElement(By.cssSelector("[name=postcode]")).sendKeys(postcode);
        driver.findElement(By.cssSelector("[name=city]")).sendKeys(city);
        driver.findElement(By.cssSelector("[name=email]")).sendKeys(email);
        driver.findElement(By.cssSelector("[name=phone]")).sendKeys(phone);
        driver.findElement(By.cssSelector("[name=password]")).sendKeys(password);
        driver.findElement(By.cssSelector("[name=confirmed_password]")).sendKeys(password);
        driver.findElement(By.cssSelector("[name=create_account]")).click();
    }

    public void LogoutCustomer() {
        driver.findElement(By.cssSelector("#box-account [href*=logout]")).click();
    }

    public void LoginCustomer(String email, String password) {
        driver.findElement(By.cssSelector("[name=login_form]  [name=email]")).sendKeys(email);
        driver.findElement(By.cssSelector("[name=login_form]  [name=password]")).sendKeys(password);
        driver.findElement(By.cssSelector("[name=login_form]  [name=login]")).click();
    }

    @Test
    public void Test1() {
        String email = getRandomEmail();
        String password = getRandomString(10);

        CreateRandomCustomer(email, password);
        LogoutCustomer();
        LoginCustomer(email, password);
        LogoutCustomer();
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
