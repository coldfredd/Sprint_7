package sprint_7;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import sprint_7.model.Courier;
import sprint_7.steps.CourierSteps;

public class CourierLoginNegativeTest extends AbstractTest{
    private final CourierSteps courierSteps = new CourierSteps();
    Courier courier;

    @Before
    public void setUp(){
        courier = new Courier();
        courier.setLogin(RandomStringUtils.randomAlphabetic(10));
        courier.setPassword(RandomStringUtils.randomAlphabetic(10));
    }

    @Test
    @DisplayName("Check status code 404 of /api/v1/courier/login")
    @Description("Check can't login courier if already exists test for /api/v1/courier/login endpoint.")
    public void userNotExist(){//если авторизоваться под несуществующим пользователем, запрос возвращает ошибку;
        ValidatableResponse response = loginAsCourier();
        checkStatusCodeAndMessage(response);
    }
    @Test
    @DisplayName("Check status code 404 of /api/v1/courier/login")
    @Description("Check can't login courier if enter login incorrectly")
    public void incorrectLoginOrPassword(){//система вернёт ошибку, если неправильно указать логин или пароль;
        createCourier();
        courier.setLogin(RandomStringUtils.randomAlphabetic(11));
        ValidatableResponse response = loginAsCourier();
        checkStatusCodeAndMessage(response);
    }
    @Step("Create a courier")
    public void createCourier() {
        courierSteps.createCourier(courier);
    }
    @Step("Login as the courier")
    public ValidatableResponse loginAsCourier() {
        ValidatableResponse response = courierSteps.login(courier);
        return response;
    }
    @Step("Verify status code is 400 and response have message")
    public void checkStatusCodeAndMessage(ValidatableResponse response) {
        response
                .statusCode(404)
                .body("message", Matchers.is("Учетная запись не найдена"));
    }
}
