package ru.praktikum.yandex;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;
import java.time.Duration;

public class OrderPage {
    private WebDriver driver;

    //Форма заказ "Для кого самокат"
    private final By orderForm = By.className("Order_Form__17u6u");

    //Поле ввода имени
    private final By nameInput = By.xpath(".//input[contains(@placeholder,'Имя')]");

    //Поле ввода фамилии
    private final By surnameInput = By.xpath(".//input[contains(@placeholder,'Фамилия')]");

    //Поле ввода адреса
    private final By addressInput = By.xpath(".//input[contains(@placeholder,'Адрес')]");

    //Поле ввода метро
    private final By metroInput = By.xpath(".//input[contains(@placeholder,'Станция метро')]");

    //Поле ввода телефона
    private final By phoneInput = By.xpath(".//input[contains(@placeholder,'Телефон')]");

    //Выпадающий список станций
    private  final By metroList = By.className("select-search__select");

    //Список доступных станций метро
    private final By metroListItems = By.xpath(".//div[starts-with(@class,'Order_Text')]");

    //Кнопка "Далее"
    private final By nextButton = By.xpath(".//button[text()='Далее']");

    //Поле ввода даты
    private final By dateInput = By.xpath(".//input[contains(@placeholder,'Когда привезти самокат')]");

    //Выбранная дата
    private final By dateSelected = By.className("react-datepicker__day--selected");

    //Поле срок аренды
    private final By termDropdownRoot = By.className("Dropdown-root");

    //Выпадающий список с доступными сроками аренды
    private final By termDropdownMenu = By.className("Dropdown-option");

    //Поле для выбора цвета
    private final By choiceColor = By.className("Checkbox_Label__3wxSf");

    //Поле комментарий для курьера
    private final By commentInput = By.xpath(".//input[contains(@placeholder,'Комментарий')]");

    //Кнопка Заказать
    private final By orderButtonFinal = By.xpath(".//button[contains(@class, 'Button_Middle__1CSJM') and (text()='Заказать')]");

    //Кнопка "Да" в окне подтверждения заказа
    private final By confirmOrderButton = By.xpath(".//button[contains(@class, 'Button_Middle__1CSJM') and (text()='Да')]");

    //Текст с сообщением об успешном оформлении заказа
    private final By successOrderMessage = By.xpath(".//div[(starts-with(@class,'Order_ModalHeader'))]");

    //Конструктор класса OrderPage
    public OrderPage (WebDriver driver) {
        this.driver = driver;
    }

    //Метод ожидания загрузки формы
    public void waitForLoadOrderPage() {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOf(driver.findElement(orderForm)));
    }

    //Метод выбора элемента для выпадающего списка
    private void chooseElementFromDropdown (By dropDownElements, String elementToChoose) {
        List<WebElement> elementsFiltered = driver.findElements(dropDownElements);
        for (WebElement element : elementsFiltered) {
            if (element.getText().equals(elementToChoose)) {
                element.click();
                break;
            }
        }
    }

    //Метод ожидания загрузки элемента
    private void waitForElementLoad(By elementToLoad) {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOf(driver.findElement(elementToLoad)));
    }

    //Метод заполнения поля Имя
    public void setNameInput(String name) {
        driver.findElement(nameInput).sendKeys(name);
    }

    //Метод заполнения поля Фамилия
    public void setSurnameInput(String surname) {
        driver.findElement(surnameInput).sendKeys(surname);
    }

    //Метод заполнения поля Адрес
    public void setAddressInput(String address) {
        driver.findElement(addressInput).sendKeys(address);
    }

    //Метод выбора станции метро
    public void setMetroInput(String metro) {
        driver.findElement(metroInput).sendKeys(metro);
        waitForElementLoad((metroList));
        chooseElementFromDropdown(metroListItems, metro);
    }

    //Метод заполнения поля телефон
    public void setPhoneInput (String phone) {
        driver.findElement(phoneInput).sendKeys(phone);
    }

    //Метод нажатия кнопки далее
    public void clickButtonOnward() {
        driver.findElement(nextButton).click();
    }

    //Метод нажатия на дату в календаре
    private void clickDateSelected() {
        driver.findElement(dateSelected).click();
    }

    //Метод выбора даты
    public void setDateInput(String date) {
        driver.findElement(dateInput).sendKeys(date);
        waitForElementLoad(dateSelected);
        clickDateSelected();
    }

    //Метод нажатия на поле "Срок аренды"
    private void clickTermDropdown() {
        driver.findElement(termDropdownRoot).click();
    }

    //Метод выбора срока аренды
    public void setTermInput(String termToChoose) {
        clickTermDropdown();
        chooseElementFromDropdown(termDropdownMenu, termToChoose);
    }

    //Метод выбора цвета
    public void setColorInput(String colorToChoose) {
        chooseElementFromDropdown(choiceColor, colorToChoose);
    }

    //Метод заполнения поля Комментарий
    public void setCommentInput(String comment) {
        driver.findElement(commentInput).sendKeys(comment);
    }

    //Метод нажатия на кнопку заказать(после заполнения формы)
    private void clickFinalOrderButton() {
        driver.findElement(orderButtonFinal).click();
    }

    //Метод нажатия на подтверждения заказа
    private void clickConfirmOrderButton() {
        driver.findElement(confirmOrderButton).click();
    }

    //Метод завершения заказа
    public void completeOrder() {
        clickFinalOrderButton();
        waitForElementLoad(confirmOrderButton);
        clickConfirmOrderButton();
    }

    //Метод получения сообщения об оформлении заказа
    public String getOrderSuccessMessage() {
        return driver.findElement(successOrderMessage).getText();
    }

    //Метод создания заказа
    public void makeOrder(String name, String surname, String address, String metro, String phone, String date, String term, String color, String comment) {
        waitForLoadOrderPage();

        setNameInput(name);
        setSurnameInput(surname);
        setAddressInput(address);
        setMetroInput(metro);
        setPhoneInput(phone);

        clickButtonOnward();

        setDateInput(date);
        setTermInput(term);
        setColorInput(color);
        setCommentInput(comment);

        completeOrder();
    }
}

