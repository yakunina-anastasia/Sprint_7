import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.List;

import static org.hamcrest.CoreMatchers.notNullValue;

public class OrderAPI {
    private final OrderSite orderSite = new OrderSite();

    @Step("Запрос на создание заказа")
    public Response createOrder(String firstName, String lastName, String address, String phone,
                                String rentTime, String deliveryDate, String comment, List<String> scooterColor) {
        return orderSite.createOrder(new Order(firstName, lastName, address, phone,
                rentTime, deliveryDate, comment, scooterColor));
    }

    @Step("Запрос на удаление заказа")
    public Response deleteOrder(Integer trackId) {
        return orderSite.deleteOrder(trackId);
    }

    @Step("Проверка кода ответа")
    public void checkStatusCode(Response response, int code) {
        Allure.addAttachment("Ответ", response.getStatusLine());
        response.then().statusCode(code);
    }

    @Step("Проверка возврата track-номера заказа")
    public void checkResponseParamNotNull(Response response, String label) {
        Allure.addAttachment("Ответ", response.getBody().asInputStream());
        response.then().assertThat().body(label, notNullValue());
    }

    @Step("Получение track-номера заказа")
    public Integer getTrack(Response response) {
        return response.body().as(OrderResponse.class).getTrack();
    }

    @Step("Запрос на получение списка заказов")
    public Response getOrdersList() {
        return orderSite.getOrdersList();
    }

    @Step("Проверка наличия заказов в теле ответа")
    public void checkOrdersInResponse(Response response) {
        Allure.addAttachment("Список заказов", response.getBody().asInputStream());
        response.then().assertThat().body("orders", notNullValue());
    }
}