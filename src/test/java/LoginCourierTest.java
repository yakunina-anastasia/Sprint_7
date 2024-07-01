import io.qameta.allure.*;
import io.qameta.allure.junit4.*;
import io.restassured.response.Response;
//import org.example.CourierAPI;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

@Link(url = "https://qa-scooter.praktikum-services.ru/docs/#api-Courier-Login", name = "#api-Courier-Login")
@DisplayName("Логин курьера")
public class LoginCourierTest extends CourierAPI {
    private String login;
    private String password;
    private String firstName;

    @Before
    @Step("Подготовка данных для тестирования")
    public void prepareTestData() {
        this.login = "courier_" + UUID.randomUUID();
        this.password = "pass_" + UUID.randomUUID();
        this.firstName = "name_" + UUID.randomUUID();

        createCourier(login, password, firstName);
    }

    @After
    @Step("Удаление данных после теста")
    public void clearAfterTests() {
        Integer idCourier = getIdCourier(loginCourier(login, password));
        if (idCourier == null) return;

        deleteCourier(idCourier);
    }

    @Test
    @DisplayName("Логин курьера в систему")
    @Description("Проверка возможности логина курьера. Ожидаемый результат: курьер залогинен в системе, возвращается его id")
    public void loginCourierIsSuccess() {
        Response response = loginCourier(login,password);

        checkStatusCode(response, 200);
        checkCourierIDNotNull(response);
    }

    @Test
    @DisplayName("Логин курьера в систему без входных данных")
    @Description("Проверка возможности логина курьера без входных данных. Ожидаемый результат: курьер не входит в систему")
    public void loginCourierMissingAllParamsIsFailed() {
        Response response = loginCourier("", "");

        checkStatusCode(response, 400);
        checkMessage(response, "message", "Недостаточно данных для входа");
    }

    @Test
    @DisplayName("Логин курьера в систему без логина")
    @Description("Проверка возможности логина курьера без логина. Ожидаемый результат: курьер не входит в систему")
    public void loginCourierMissingLoginParamIsFailed() {
        Response response = loginCourier("", password);

        checkStatusCode(response, 400);
        checkMessage(response, "message", "Недостаточно данных для входа");
    }

    @Test
    @DisplayName("Логин курьера в систему без пароля")
    @Description("Проверка возможности логина курьера без пароля. Ожидаемый результат: курьер не входит в систему")
    public void loginCourierMissingPasswordParamIsFailed() {
        Response response = loginCourier(login, "");

        checkStatusCode(response, 400);
        checkMessage(response, "message", "Недостаточно данных для входа");
    }

    @Test
    @DisplayName("Логин курьера в систему с некорректным логином")
    @Description("Проверка возможности логина курьера с некорректным логином. Ожидаемый результат: курьер не входит в систему")
    public void loginCourierIncorrectLoginParamIsFailed() {
        Response response = loginCourier(login + "1", password);

        checkStatusCode(response, 404);
        checkMessage(response, "message", "Учетная запись не найдена");
    }

    @Test
    @DisplayName("Логин курьера в систему с некорректным паролем")
    @Description("Проверка возможности логина курьера с некорректным паролем. Ожидаемый результат: курьер не входит в систему")
    public void loginCourierIncorrectPasswordParamIsFailed() {
        Response response = loginCourier(login, password + "1");

        checkStatusCode(response, 404);
        checkMessage(response, "message", "Учетная запись не найдена");
    }
}