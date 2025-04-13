import io.github.bonigarcia.wdm.WebDriverManager;
import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import ru.praktikum.yandex.MainPage;
import ru.praktikum.yandex.OrderPage;

import static org.hamcrest.core.StringContains.containsString;

@RunWith(Parameterized.class)
public class OrderPageTest {
    private WebDriver driver;

    private final String pageUrl = "https://qa-scooter.praktikum-services.ru/";

    private final String name;
    private final String surname;
    private final String address;
    private final String metro;
    private final String phone;
    private final String date;
    private final String term;
    private final String color;
    private final String comment;
    private final String textSuccessOrder = "Заказ оформлен";

    public OrderPageTest(String name, String surname, String address, String metro, String phone, String date, String term, String color, String comment) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.metro = metro;
        this.phone = phone;
        this.date = date;
        this.term = term;
        this.color = color;
        this.comment = comment;
    }

    @Parameterized.Parameters
    public static Object[][] testDataForOrder() {
        return new Object[][]{
        {"Дмитрий", "Ларчук", "Полтавская 15 квартира 63", "Сокольники", "89153151515", "15.05.2025", "семеро суток", "чёрный жемчуг", "За час позвонить"},
        {"Даниил", "Тимошенко", "Корнеева 87-3", "Красные Ворота", "+79101001010", "26.06.2025", "трое суток", "серая безысходность", "С хорошими тормозами"},
    };
    }

    @Before
    public void StartUp() {
        WebDriverManager.chromedriver().setup();
        //driver = new ChromeDriver();
        driver = new FirefoxDriver();
        driver.get(pageUrl);
    }

private void makeOrder(OrderPage orderPage) {
    orderPage.waitForLoadOrderPage();

    orderPage.setNameInput(this.name);
    orderPage.setSurnameInput(this.surname);
    orderPage.setAddressInput(this.address);
    orderPage.setMetroInput(this.metro);
    orderPage.setPhoneInput(this.phone);

    orderPage.clickButtonOnward();

    orderPage.setDateInput(this.date);
    orderPage.setTermInput(this.term);
    orderPage.setColorInput(this.color);
    orderPage.setCommentInput(this.comment);

    orderPage.completeOrder();
    }

    @Test
    public void checkOrderWithClickHeaderButton() {
        MainPage mainPage = new MainPage(driver);
        OrderPage orderPage = new OrderPage(driver);

        mainPage.clickOnCookieButton();
        mainPage.clickOrderButtonHeader();
        makeOrder(orderPage);

        MatcherAssert.assertThat("Ошибка в создании нового заказа", orderPage.getOrderSuccessMessage(), containsString(textSuccessOrder));
    }

    @Test
    public void checkOrderWithClickBodyButton() {
        MainPage mainPage = new MainPage(driver);
        OrderPage orderPage = new OrderPage(driver);

        mainPage.clickOnCookieButton();
        mainPage.clickOrderButtonBody();
        makeOrder(orderPage);

        MatcherAssert.assertThat("Ошибка в создании нового заказа", orderPage.getOrderSuccessMessage(), containsString(textSuccessOrder));
    }





    @After
    public void tearDown() {
        driver.quit();
    }
}
