package sprint_7;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsNull.notNullValue;


@RunWith(Parameterized.class)
public class OrderCreateTest extends AbstractTest {
    private String color1;
    private String color2;

    public OrderCreateTest(String color1, String color2) {
        this.color1 = color1;
        this.color2 = color2;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"BLACK", "GREY"}, // можно указать оба цвета;
                {"BLACK", null},    // можно указать один из цветов — BLACK или GREY;
                {null, "GREY"},    // можно указать один из цветов — BLACK или GREY;
                {null, null}       // можно совсем не указывать цвет;
        });
    }

    @Test
    @DisplayName("Check status code 201 of api/v1/orders")
    @Description("The test verifies that the response body contains a track when creating an order with different colors.")
    public void testCreateOrder() {
        Map<String, Object> requestBody = prepareOrderRequest(color1, color2);
        Response response = createOrder(requestBody);
        checkStatusCode(response);
        checkTrackInResponseBody(response);
    }
    @Step("Prepare order request with colors: {color1}, {color2}")
    public Map<String, Object> prepareOrderRequest(String color1, String color2) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("color1", color1);
        requestBody.put("color2", color2);
        return requestBody;
    }

    @Step("Create order with request body: {requestBody}")
    public Response createOrder(Map<String, Object> requestBody) {
        return given()
                .body(requestBody)
                .when()
                .post("api/v1/orders");
    }
    @Step("Check status code is 201")
    public void checkStatusCode(Response response) {
        response.then().statusCode(201);
    }
    @Step("Check if 'track' field exists and is not null")
    public void checkTrackInResponseBody(Response response) {
        response.then().body("track", notNullValue());
    }
}
