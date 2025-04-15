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
import static ru.praktikum.yandex.MainPage.QA_SCOOTER_URL;

@RunWith(Parameterized.class)
public class OrderPageTest {
    private WebDriver driver;


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

    // Константы с тестовыми данными
    private static final Object[][] ORDER_TEST_DATA = {
            {
                    "Дмитрий",
                    "Ларчук",
                    "Полтавская 15 квартира 63",
                    "Сокольники",
                    "89153151515",
                    "15.05.2025",
                    "семеро суток",
                    "чёрный жемчуг",
                    "За час позвонить"
            },
            {
                    "Даниил",
                    "Тимошенко",
                    "Корнеева 87-3",
                    "Красные Ворота",
                    "+79101001010",
                    "26.06.2025",
                    "трое суток",
                    "серая безысходность",
                    "С хорошими тормозами"
            }
    };

    @Parameterized.Parameters(name = "Тестовые данные: {0} {1}")
    public static Object[][] testDataForOrder() {
        return ORDER_TEST_DATA;
    }

    @Before
    public void StartUp() {
        WebDriverManager.chromedriver().setup();
        //driver = new ChromeDriver();
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.get(QA_SCOOTER_URL);
    }

    @Test
    public void checkOrderWithClickHeaderButton() {
        MainPage mainPage = new MainPage(driver);
        OrderPage orderPage = new OrderPage(driver);

        mainPage.clickOnCookieButton();
        mainPage.clickOrderButtonHeader();
        orderPage.makeOrder(this.name,this.surname,this.address,this.metro,this.phone,this.date,this.term,this.color,this.comment);

        MatcherAssert.assertThat("Ошибка в создании нового заказа", orderPage.getOrderSuccessMessage(), containsString(textSuccessOrder));
    }

    @Test
    public void checkOrderWithClickBodyButton() {
        MainPage mainPage = new MainPage(driver);
        OrderPage orderPage = new OrderPage(driver);

        mainPage.clickOnCookieButton();
        mainPage.clickOrderButtonBody();
        orderPage.makeOrder(this.name,this.surname,this.address,this.metro,this.phone,this.date,this.term,this.color,this.comment);

        MatcherAssert.assertThat("Ошибка в создании нового заказа", orderPage.getOrderSuccessMessage(), containsString(textSuccessOrder));
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
