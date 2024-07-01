import io.qameta.allure.*;
import io.qameta.allure.junit4.*;
import io.restassured.response.Response;
//import org.main.java.CourierAPI;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

@Link(url = "https://qa-scooter.praktikum-services.ru/docs/#api-Courier-CreateCourier", name = "#api-Courier-CreateCourier")
@DisplayName("Создать курьера")
public class CreateCourierTest extends CourierAPI {
    private String login;
    private String password;
    private String firstName;

    public CreateCourierTest() {
    }

    @Before
    @Step("Подготовка тестовых данных")
    public void prepareTestData() {
        this.login = "courier_" + UUID.randomUUID();
        this.password = "pass_" + UUID.randomUUID();
        this.firstName = "name_" + UUID.randomUUID();
    }

    @After
    @Step("Откат данных после теста")
    public void cleanAfterTests() {
        if (!isCourierCreated()) return;

        Integer idCourier = getIdCourier(loginCourier(login, password));

        if (idCourier != null) {
            deleteCourier(idCourier);
        }

        setIsCreated(false);
    }

    @Test
    @DisplayName("Создать нового курьера")
    @Description("Проверка на возможность добавления нового курьера. Ожидаемый результат: новый курьер создан")
    public void createNewCourierIsSuccess() {
        Response response = createCourier(login, password, firstName);
        setIsCreated(isCourierCreated(response, 201));

        checkStatusCode(response, 201);
        checkMessage(response, "ok", true);
    }

    @Test
    @DisplayName("Создание двух одинаковых курьеров")
    @Description("Проверка на возможность создать двух одинаковых курьеров. Ожидаемый результат: невозможно создать двух одинаковых курьеров, тест завален")
    public void createSameCouriersIsFailed(){
        Response response = createCourier(login, password, firstName);
        setIsCreated(isCourierCreated(response, 201));

        checkStatusCode(response, 201);
        checkMessage(response, "ok", true);

        response = createCourier(login, password, firstName);

        checkStatusCode(response, 409);
        checkMessage(response, "message", "Этот логин уже используется");
    }

    @Test
    @DisplayName("Создание нового курьера без входных данных")
    @Description("Проверка на возможность создания нового курьера без входных данных. Ожидаемый результат: новый курьер не создается")
    public void createCourierMissingAllParamsIsFailed() {
        Response response = createCourier("", "", "");
        setIsCreated(isCourierCreated(response, 201));

        checkStatusCode(response, 400);
        checkMessage(response, "message", "Недостаточно данных для создания учетной записи");
    }

    @Test
    @DisplayName("Создание нового курьера без логина")
    @Description("Проверка на возможность создания нового курьера без логина. Ожидаемый результат: новый курьер не создается")
    public void createCourierMissingLoginParamIsFailed() {
        Response response = createCourier("", password, firstName);
        setIsCreated(isCourierCreated(response, 201));

        checkStatusCode(response, 400);
        checkMessage(response, "message", "Недостаточно данных для создания учетной записи");
    }

    @Test
    @DisplayName("Создание нового курьера без пароля")
    @Description("Проверка на возможность создания нового курьера без пароля. Ожидаемый результат: новый курьер не создается")
    public void createCourierMissingPasswordParamIsFailed() {
        Response response = createCourier(login, "", firstName);
        setIsCreated(isCourierCreated(response, 201));

        checkStatusCode(response, 400);
        checkMessage(response, "message", "Недостаточно данных для создания учетной записи");
    }
}