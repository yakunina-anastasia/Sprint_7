import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

public class OrderSite extends BaseSite{
    public Response createOrder(Order order) {
        return doPostRequest(
                UrlsForScooter.HOME_PAGE + UrlsForScooter.CREATE_ORDER,
                order,
                "application/json"
        );
    }

    public Response deleteOrder(Integer trackId) {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("track", trackId);

        return doPutRequest(
                UrlsForScooter.HOME_PAGE + UrlsForScooter.DELETE_ORDER,
                queryParams
        );
    }

    public Response getOrdersList() {
        return doGetRequest(UrlsForScooter.HOME_PAGE + UrlsForScooter.GET_ORDERS_LIST);
    }
}