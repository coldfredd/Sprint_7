package sprint_7;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsNull.notNullValue;

public class OrderListTest extends AbstractTest {
    @Test
    @DisplayName("Check status code 200 of api/v1/orders")
    @Description("The test ensures that the response body contains a list of orders")
    public void testGetOrderList() {
        Response response = getOrderList();
        checkStatusCode(response);
        checkOrdersInResponseBody(response);
    }

    @Step("Get order list")
    public Response getOrderList() {
        Response response = given()
                .when()
                .get("api/v1/orders");
        return response;
    }
    @Step("Check status code is 200")
    public void checkStatusCode(Response response) {
        response.then().assertThat().statusCode(200);
    }
    @Step("Check if 'orders' field exists and is not empty")
    public void checkOrdersInResponseBody(Response response) {
        response.then().body("orders", notNullValue());
    }
}
