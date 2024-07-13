package org.example.habrpagetest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LessonExamples {
    private WebDriver driver;


    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    @DisplayName("Доступность кнопки")
    public void enable() {
        driver.get("https://demoqa.com/dynamic-properties");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(6));
        WebElement disabledButton = driver.findElement(By.xpath("//* [@id='enableAfter']"));
        wait.until(ExpectedConditions.elementToBeClickable(disabledButton));
        assertTrue(disabledButton.isEnabled(), "Кнопка не стала активной");
    }

    @Test
    public void hidden() {
        driver.get("https://the-internet.herokuapp.com/dynamic_loading/1");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(6));

        driver.findElement(By.xpath("//button [contains(., Start)]")).click();
        WebElement helloText = driver.findElement(By.xpath("//div [@id='finish']"));
        wait.until(ExpectedConditions.visibilityOf(helloText));
        assertTrue(helloText.isDisplayed(), "Текста нет");
    }

    @Test
    public void notLoaded() {
        driver.get("https://the-internet.herokuapp.com/dynamic_loading/2");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));

        driver.findElement(By.xpath("//button [contains(., Start)]")).click();
        wait.until(ExpectedConditions.elementToBeClickable((By.xpath("//div [@id='finish']"))));
        assertTrue(driver.findElement(By.xpath("//div [@id='finish']")).isDisplayed(), "Нет нужного текста");
    }
}