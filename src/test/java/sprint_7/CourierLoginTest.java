package sprint_7;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import sprint_7.model.Courier;
import sprint_7.steps.CourierSteps;

import static org.hamcrest.core.IsNull.notNullValue;

public class CourierLoginTest extends AbstractTest {
    private CourierSteps courierSteps = new CourierSteps();
    Courier courier;

    @Before
    public void setUp(){

        courier = new Courier();
        courier.setLogin(RandomStringUtils.randomAlphabetic(10));
        courier.setPassword(RandomStringUtils.randomAlphabetic(10));
    }

    @Test
    @DisplayName("Check status code 200 of /api/v1/courier/login")
    @Description("The test verifies that a successful request returns an ID")
    public void shouldReturnId(){
        createCourier();
        ValidatableResponse response = loginAsCourier();
        verifyStatusCodeAndId(response);
    }
    @Step("Create a courier")
    public void createCourier() {
        courierSteps.createCourier(courier);
    }

    @Step("Login as the courier")
    public ValidatableResponse loginAsCourier() {
        return courierSteps.login(courier);
    }

    @Step("Verify status code is 200 and ID is not null")
    public void verifyStatusCodeAndId(ValidatableResponse response) {
        response
                .statusCode(200)
                .body("id", notNullValue());
    }

    @After
    public void tearDown(){
        Integer id = courierSteps.login(courier)
                .extract().body().path("id");
        courier.setId(id);
        courierSteps.delete(courier);
    }


}
