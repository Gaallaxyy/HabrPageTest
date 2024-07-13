package org.example.habrpagetest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MainPageTest {
    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        // Fix the issue https://github.com/SeleniumHQ/selenium/issues/11750
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://www.habr.com/");

    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    @DisplayName("Проверка вкладок в разделе 'Как стать автором'")
    public void lookingTabs() {

        WebElement authorButton = driver.findElement(By.xpath("// *[@class='tm-header__container']//* [contains(text(), 'Как стать автором')]"));
        authorButton.click();
        assertTrue(driver.findElement(By.xpath("//a [contains(text(), 'Самое важное')]")).isDisplayed(), "Вкладка 'Самое важное' не найдена");
        assertTrue(driver.findElement(By.xpath("//a [contains(text(), 'Новые авторы')]")).isDisplayed(), "Вкладка 'Новые авторы' не найдена");
        assertTrue(driver.findElement(By.xpath("//a [contains(text(), 'Ожидают приглашения')]")).isDisplayed(), "Вкладка 'Ожидают приглашения' не найдена");
    }

    @Test
    @DisplayName("Наполнение дропдауна в шапке")
    public void dropDownIn() {

        WebElement dropDownButton = driver.findElement(By.xpath("//button [@class='tm-header__dropdown-toggle']"));
        dropDownButton.click();
        driver.findElement(By.xpath("//div [@class='tm-dropdown__body tm-dropdown__body_left']")).isEnabled();
        assertTrue(driver.findElement(By.xpath("//div[@data-test-id='our-projects']" +
                "//* [contains(., 'Хабр') and contains(., 'Q&A') and contains(., 'Карьера') and contains (., 'Фриланс')]"))
                .isDisplayed(), "Требуемые элементы дропдауна не найдены");
    }
}
