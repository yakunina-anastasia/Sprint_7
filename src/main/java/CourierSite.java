import io.restassured.response.Response;

public class CourierSite extends BaseSite{
    public Response createCourier(Courier courier) {
        return doPostRequest(
                UrlsForScooter.HOME_PAGE + UrlsForScooter.CREATE_COURIER,
                courier,
                "application/json"
        );
    }

    public Response loginCourier(Courier courier) {
        return doPostRequest(
                UrlsForScooter.HOME_PAGE + UrlsForScooter.LOGIN_COURIER,
                courier,
                "application/json"
        );

    }

    public Response deleteCourier(Integer idCourier) {
        return doDeleteRequest(UrlsForScooter.HOME_PAGE + UrlsForScooter.DELETE_COURIER + idCourier);
    }
}
