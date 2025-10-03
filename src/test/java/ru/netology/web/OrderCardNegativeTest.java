package ru.netology.web;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderCardNegativeTest {
    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    @DisplayName("Required Field First Name And Name")
    void RequiredFieldFirstNameAndName() {
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79270000000");
        driver.findElement(By.cssSelector("[data-test-id=agreement] .checkbox__box")).click();
        driver.findElement(By.cssSelector(".button")).click();
        String text = driver.findElement(By.cssSelector(".input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", text.trim());
    }

    @Test
    @DisplayName("Required field phone")
    void RequiredFieldPhone() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Василий");
        driver.findElement(By.cssSelector("[data-test-id=agreement] .checkbox__box")).click();
        driver.findElement(By.cssSelector(".button")).click();

        List<WebElement> elements = driver.findElements(By.cssSelector(".input__sub"));
        if (elements.size() >= 2) {
            String text = elements.get(1).getText();
            assertEquals("Поле обязательно для заполнения", text.trim());
        } else {
            fail("Не найдено достаточно элементов .input__sub. Найдено: " + elements.size());
        }
    }

    @Test
    @DisplayName("Required agreement")
    void RequiredAagreement() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Василий");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79194570000");
        driver.findElement(By.cssSelector(".button")).click();

        WebElement errorElement = driver.findElement(By.cssSelector(".checkbox__text"));
        String finalColor = errorElement.getCssValue("color");

        assertTrue(finalColor.equals("rgba(255, 92, 92, 1)") || finalColor.equals("rgb(255, 92, 92)"));
    }

        @Test
        @DisplayName("Invalid field Name and First name")
        void EnteringNumbersInsteadOfLettersInTheLastNameAndFirstNameFields() {
            driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("1111");
            driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79270000000");
            driver.findElement(By.cssSelector("[data-test-id=agreement] .checkbox__box")).click();
            driver.findElement(By.cssSelector(".button")).click();
            String text = driver.findElement(By.cssSelector(".input__sub")).getText();
            assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
        }

    @Test
    @DisplayName("Invalid field Phone")
    void EnteringInvalidsNumbersInsteadOfLettersInThePhoneFields() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Василий");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("000000000");
        driver.findElement(By.cssSelector("[data-test-id=agreement] .checkbox__box")).click();
        driver.findElement(By.cssSelector(".button")).click();

        List<WebElement> elements = driver.findElements(By.cssSelector(".input__sub"));
        if (elements.size() >= 2) {
            String text = elements.get(1).getText();
            assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
        } else {
            fail("Не найдено достаточно элементов .input__sub. Найдено: " + elements.size());
        }
    }

    @Test
    @DisplayName("The number of characters in the 'Phone' field is 12")
    void UpperLimitOfTheFieldPhone() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Татьяна");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+791945756000");
        driver.findElement(By.cssSelector("[data-test-id=agreement] .checkbox__box")).click();
        driver.findElement(By.cssSelector(".button")).click();
        List<WebElement> elements = driver.findElements(By.cssSelector(".input__sub"));
        if (elements.size() >= 2) {
            String text = elements.get(1).getText();
            assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
        } else {
            fail("Не найдено достаточно элементов .input__sub. Найдено: " + elements.size());
        }
    }

    @Test
    @DisplayName("The number of characters in the 'Phone' field is 10")
    void UpperLimitOfTheFieldPhoneMinusOne() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Татьяна");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+7919457560");
        driver.findElement(By.cssSelector("[data-test-id=agreement] .checkbox__box")).click();
        driver.findElement(By.cssSelector(".button")).click();
        List<WebElement> elements = driver.findElements(By.cssSelector(".input__sub"));
        if (elements.size() >= 2) {
            String text = elements.get(1).getText();
            assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
        } else {
            fail("Не найдено достаточно элементов .input__sub. Найдено: " + elements.size());
        }
    }

    @Test
    @DisplayName("Entering characters into the field Name and First name")
    void EnteringCharactersIntoTheFieldNameAndFirstNameFields() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("%#*");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79270000000");
        driver.findElement(By.cssSelector("[data-test-id=agreement] .checkbox__box")).click();
        driver.findElement(By.cssSelector(".button")).click();
        String text = driver.findElement(By.cssSelector(".input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }



}

