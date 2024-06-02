package sprint_7;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import sprint_7.model.Courier;
import sprint_7.steps.CourierSteps;


public class CourierCreatTest extends AbstractTest {
    private final CourierSteps courierSteps = new CourierSteps();
    Courier courier;

    @Before
    public void setUp(){

        courier = new Courier();
        courier.setLogin(RandomStringUtils.randomAlphabetic(10));
        courier.setPassword(RandomStringUtils.randomAlphabetic(10));
    }
    @Test
    @DisplayName("Check status code 201 of /api/v1/courier")
    @Description("Check create courier test for /api/v1/courier endpoint. And body have ok: true")
    public void createCourierTest(){ //Создание курьера, проверка статуса и успешный запрос возвращает ok: true;
        ValidatableResponse response = createCourier();
        checkCourierHaveOk(response);
    }

    @Test
    @DisplayName("Check status code 409 of /api/v1/courier")
    @Description("Check can't create courier if already exists test for /api/v1/courier endpoint.")
    public void loginCourierAlreadyExists(){//нельзя создать двух одинаковых курьеров; Если создать пользователя с логином, который уже есть, возвращается ошибка.

        createCourier();
        ValidatableResponse response = createCourier();
        checkCourierAlreadyExists(response);
    }
    @Step("Create a courier")
    public ValidatableResponse createCourier() {
        return courierSteps.createCourier(courier);
    }
    @Step("Check status code 201 and body have Ok")
    public void checkCourierHaveOk(ValidatableResponse response) {
        response
                .statusCode(201)
                .body("ok", Matchers.is(true));
    }

    @Step("Check status code 409 and body contains error message")
    public void checkCourierAlreadyExists(ValidatableResponse response) {
        response
                .statusCode(409)
                .body("message", Matchers.is("Этот логин уже используется. Попробуйте другой."));
    }

    @After
    public void tearDown(){
        Integer id = courierSteps.login(courier)
                .extract().body().path("id");
        courier.setId(id);
        courierSteps.delete(courier);
    }
}
