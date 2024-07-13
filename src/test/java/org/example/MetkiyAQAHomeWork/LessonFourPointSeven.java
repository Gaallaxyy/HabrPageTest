package org.example.MetkiyAQAHomeWork;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LessonFourPointSeven {
    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        // Fix the issue https://github.com/SeleniumHQ/selenium/issues/11750
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://www.bing.com/");
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    @DisplayName("Проверяем, что при клике на первую ссылку в bing по слову 'Selenium' происходит переход по ссылке 'https://www.selenium.dev/'")
    public void search() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        String input = "Selenium";
        WebElement searchField = driver.findElement(By.xpath("//input[@id='sb_form_q']"));
        searchField.sendKeys(input);
        searchField.submit();
        By links = By.xpath("//h2//a[@target='_blank'][@href]");

        wait.until(ExpectedConditions.and(
                ExpectedConditions.attributeContains((links), "href", "selenium"),
                ExpectedConditions.elementToBeClickable(links)));

        List<WebElement> results = wait.until(ExpectedConditions.
                visibilityOfAllElements(driver.findElements(links)));

        FirstLink(results, 4);
        System.out.println("Клик по ссылке");
        ArrayList tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1).toString());
        String url = driver.getCurrentUrl();
        assertEquals(url, "https://www.selenium.dev/", "Ссылка ведет на другой сайт");
    }

    public void FirstLink(List<WebElement> results, int num) {
        results.get(num).click();
    }

}