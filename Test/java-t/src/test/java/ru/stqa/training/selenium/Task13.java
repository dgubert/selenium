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

import java.util.List;
import java.util.Random;

import static org.openqa.selenium.support.ui.ExpectedConditions.attributeContains;
import static org.openqa.selenium.support.ui.ExpectedConditions.stalenessOf;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;

public class Task13 {
    private WebDriver driver;
    private WebDriverWait wait;

    private static final By CART_QUANTITY = By.cssSelector("#cart .quantity");
    @Before
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
    }

    public void NavigateToHomePage() {
        driver.get("http://localhost/litecart/en/");
    }

    public void NavigateToCartPage() {
        driver.findElement(By.linkText("Checkout »")).click();
    }

    public void AddProductToCart(int productIndex) {
        driver.findElements(By.cssSelector(".content .link")).get(productIndex).click();
        driver.findElement(By.cssSelector("[name=add_cart_product]")).click();
    }

    public void RemoveProductFromCart(int productIndex) {
        List <WebElement> items = driver.findElements(By.cssSelector(".shortcut"));

        if (items.size() > 1) {
            items.get(productIndex).click();
        }

        wait.until(visibilityOf(driver.findElement(By.cssSelector("[name=remove_cart_item]")))).click();
    }

    @Test
    public void Test1() {
        //Добавление первого продукта в корзину с проверкой увеличения счетчика
        for (int i = 0; i < 3; i++) {
            NavigateToHomePage();

            WebElement quantityEl = driver.findElement(CART_QUANTITY);
            int quantity = Integer.parseInt(quantityEl.getAttribute("textContent"));
            String expectedQuantity = (quantity + 1) + "";

            AddProductToCart(0);
            wait.until(attributeContains(CART_QUANTITY, "textContent", expectedQuantity));
        }

        //Удаление всех товаров из корзины с проверкой таблицы товаров
        NavigateToCartPage();

        for (int i = 0; i < 3; i++) {

            List<WebElement> items = driver.findElements(By.cssSelector("[class^=dataTable] [class=item]"));

            RemoveProductFromCart(0);
            wait.until(stalenessOf(items.get(0)));
        }
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
