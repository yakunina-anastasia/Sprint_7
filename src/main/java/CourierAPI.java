import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.restassured.response.*;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.IsEqual.equalTo;

public class CourierAPI {

    private final CourierSite courierSite = new CourierSite();

    private boolean isCreated = false;

    @Step("Запрос на создание курьера")
    public Response createCourier(String login, String password, String firstName) {
        return courierSite.createCourier(new Courier(login, password, firstName));
    }

    @Step("Запрос на логин курьера")
    public Response loginCourier(String login, String password) {
        return courierSite.loginCourier(new Courier(login, password));
    }

    @Step("Запрос на ID курьера")
    public Integer getIdCourier(Response response) {
        return response.body().as(CourierResponse.class).getId();
    }

    @Step("Отправка запроса на удаление курьера")
    public Response deleteCourier(Integer idCourier) {
        return courierSite.deleteCourier(idCourier);
    }

    @Step("Проверка кода ответа")
    public void checkStatusCode(Response response, int code) {
        Allure.addAttachment("Ответ", response.getStatusLine());
        response.then().statusCode(code);
    }

    @Step("Проверка тела ответа")
    public void checkMessage(Response response, String label, Object body) {
        Allure.addAttachment("Ответ", response.getBody().asInputStream());
        response.then().assertThat().body(label, equalTo(body));
    }

    @Step("Проверка возврата ID курьера")
    public void checkCourierIDNotNull(Response response) {
        Allure.addAttachment("Ответ", response.getBody().asInputStream());
        response.then().assertThat().body("id", notNullValue());
    }

    public boolean isCourierCreated(Response response, int code) {
        if (response.getStatusCode() != code) return false;

        this.isCreated = true;
        return true;
    }

    public void setIsCreated(boolean isCreated) {
        this.isCreated = isCreated;
    }

    public boolean isCourierCreated() {
        return this.isCreated;
    }
}
