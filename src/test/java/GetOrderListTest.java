import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
//import org.example.OrderAPI;
import org.junit.Test;

@Link(url = "https://qa-scooter.praktikum-services.ru/docs/#api-Orders-CreateOrder", name = "#api-Orders-GetOrdersPageByPage")
@DisplayName("Получение списка заказов")
public class GetOrderListTest extends OrderAPI {
    @Test
    @DisplayName("Получение списка заказов")
    @Description("Проверка на возможность получения списка заказов. Ожидаемый результат: возвращается список заказов")
    public void getOrderListWithoutParamsIsSuccess() {
        Response response = getOrdersList();

        //чето долго, но работает
        checkStatusCode(response, 200);
        checkOrdersInResponse(response);
    }
}