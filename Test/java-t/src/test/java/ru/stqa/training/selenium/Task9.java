package ru.stqa.training.selenium;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class Task9 {

    private static final String HOME_PAGE_URL = "http://localhost/litecart/admin/?app=countries&doc=countries";
    private static final String GEO_ZONES_PAGE_URL = "http://localhost/litecart/admin/?app=geo_zones&doc=geo_zones";
    private static final String ADMIN_PAGE_URL = "http://localhost/litecart/admin/";

    private static final By USERNAME = By.name("username");
    private static final By PASSWORD = By.name("password");
    private static final By LOGIN = By.name("login");

    private static final By COUNTRIES_HOME_PAGE = By.cssSelector("[name=countries_form] a:not([title])");
    private static final By ZONES_HOME_PAGE = By.cssSelector("[name=countries_form] td:nth-child(6)");
    private static final By ZONES_COUNTRY_PAGE = By.cssSelector(".dataTable [name*=name][type=hidden]");
    private static final By GEOZONES_GEOZONES_PAGE = By.cssSelector(".dataTable td a:not([title])");
    private static final By GEOZONES_EDIT_GEOZONES_PAGE = By.cssSelector("[id=table-zones] select:not([class]) selected");

    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin";

    private WebDriver driver;

    @Before
    public void start() {
        driver = new ChromeDriver();
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

    /*
    Методы по странице HomePage
     */
    private List<WebElement> getCountries() {
        return driver.findElements(COUNTRIES_HOME_PAGE);
    }

    private List<WebElement> getZones() {
        return driver.findElements(ZONES_HOME_PAGE);
    }

    /*
    Методы по странице Country
     */
    private List<WebElement> getCountryZones() {
        return driver.findElements(ZONES_COUNTRY_PAGE);
    }

    /*
    Методы по странице GeoZonesPage
     */
    private List<WebElement> getGeoZones() {
        return driver.findElements(GEOZONES_GEOZONES_PAGE);
    }

    private List<WebElement> getZonesEditPage() {
        return driver.findElements(GEOZONES_EDIT_GEOZONES_PAGE);
    }


    /*
    На странице http://localhost/litecart/admin/?app=countries&doc=countries страны расположены в алфавитном порядке.
    Для тех стран, у которых количество зон отлично от нуля - открывается страница этой страны и проверяется сортировка зон.
     */
    @Test
    public void Test1() {
        LoginAdmin();
        driver.get(HOME_PAGE_URL);

        List<WebElement> countries = getCountries();
        int countriesCount = countries.size();

        for (int i = 0; i < countriesCount; i++) {
            if (i != countriesCount - 1) {
                assertTrue(getCountries().get(i).getAttribute("text")
                        .compareTo(getCountries().get(i + 1).getAttribute("text")) < 0);
            }

            int zonesCount = Integer.parseInt(getZones().get(i).getAttribute("textContent"));

            if (zonesCount != 0) {
                countries.get(i).click();

                List<WebElement> zones = getCountryZones();

                for (int j = 0; j < zonesCount; j++) {
                    if (j != zonesCount - 1) {
                        assertTrue(zones.get(j).getAttribute("value")
                                .compareTo(zones.get(j + 1).getAttribute("value")) < 0);
                    }
                }

                driver.get(HOME_PAGE_URL);

                countries = getCountries();

            }
        }
    }

    /*
    на странице http://localhost/litecart/admin/?app=geo_zones&doc=geo_zones
    зайти в каждую из стран и проверить, что зоны расположены в алфавитном порядке
     */
    @Test
    public void Test2() {
        LoginAdmin();
        driver.get(GEO_ZONES_PAGE_URL);

        List<WebElement> geoZones = getGeoZones();

        for (int i = 0; i < geoZones.size(); i++) {
            geoZones.get(i).click();

            List<WebElement> zones = getZonesEditPage();
            int zonesCount = zones.size();

            for (int j = 0; j < zonesCount; j++) {
                if (j != zonesCount - 1) {
                    assertTrue(zones.get(j).getAttribute("text")
                            .compareTo(zones.get(j + 1).getAttribute("text")) < 0);
                }
            }

            driver.get(GEO_ZONES_PAGE_URL);

            geoZones = getGeoZones();
        }
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
