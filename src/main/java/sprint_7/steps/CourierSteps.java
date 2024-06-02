package sprint_7.steps;

import io.restassured.response.ValidatableResponse;
import sprint_7.model.Courier;
import static io.restassured.RestAssured.given;

public class CourierSteps {

    private static final String COURIER = "/api/v1/courier";
    private static final String LOGIN = "/api/v1/courier/login";
    private static final String COURIER_DELETE = "/api/v1/courier/{id}";
    public ValidatableResponse createCourier(Courier courier){

        return given()
                .body(courier)
                .when()
                .post(COURIER)
                .then();
    }

    public ValidatableResponse login(Courier courier) {


        return  given()
                .body(courier)
                .when()
                .post(LOGIN)
                .then();
    }

    public ValidatableResponse delete(Courier courier) {
        return  given()
                .pathParam("id",courier.getId())
                .when()
                .delete(COURIER_DELETE)
                .then();
    }
}
