package ru.praktikum.yandex;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainPage {
    private WebDriver driver;

    //Константа с URL адресом сервиса
    public static final String QA_SCOOTER_URL = "https://qa-scooter.praktikum-services.ru/";

    //Кнопка оформления заказа в шапке
    private final By headerOrderButton = By.xpath(".//button[@class='Button_Button__ra12g' and text()='Заказать']");
    //Кнопка оформления заказа в теле страницы
    private final By bodyOrderButton = By.xpath(".//button[contains (@class, 'Button_UltraBig__UU3Lp') and text()='Заказать']");
    //Заголовок раскрывающегося блока с вопросами
    private final By accordionHeader = By.className("accordion__button");
    //Панель раскрывающегося блока
    private final By accordionItem = By.xpath(".//div[@class='accordion__panel']/p");
    //Кнопка принять cookie
    private final By cookieButton = By.id("rcc-confirm-button");
    //Логотип яндекса в шапке
    private final By logoYandex = By.className("Header_LogoYandex__3TSOI");
    //Логотип самоката в шапке
    private final By logoScooter = By.className("Header_LogoScooter__3lsAR");

    //Конструктор класса MainPage
    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    //Метод принятия cookie
    public void clickOnCookieButton() {
        driver.findElement(cookieButton).click();
    }
    //Метод ожидания загрузки панели с ответом на вопрос
    public void waitLoadAccordionItem(int index) {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOf(driver.findElements(accordionItem).get(index)));
    }

    //Метод для получения текста из заголовка блока вопросов
    public String getTextHeaderAccordion(int index) {
        return driver.findElements(accordionHeader).get(index).getText();
    }

    //Метод для нажатия на блок с вопросом
    public void clickHeaderAccordion(int index) {
        driver.findElements(accordionHeader).get(index).click();
    }

    //Метод для получения текста из панели блока вопросов
    public String getTextItemAccordion(int index) {
        return driver.findElements(accordionItem).get(index).getText();
    }

    //Метод для проверки раскрытия блока с вопросом
    public boolean isAccordionItemOpen(int index) {
        return driver.findElements(accordionItem).get(index).isDisplayed();
    }

    //Метод для нажатия кнопки заказать в теле страницы
    public void clickOrderButtonBody() {
        driver.findElement(bodyOrderButton).click();
    }

    //Метод для нажатия кнопки заказать в шапке страницы
    public void clickOrderButtonHeader() {
        driver.findElement(headerOrderButton).click();
    }

}
