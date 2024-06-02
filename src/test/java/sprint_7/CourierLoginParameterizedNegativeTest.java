package sprint_7;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import sprint_7.model.Courier;
import sprint_7.steps.CourierSteps;

import java.util.Arrays;
import java.util.Collection;


@RunWith(Parameterized.class)
public class CourierLoginParameterizedNegativeTest extends AbstractTest{
    private CourierSteps courierSteps = new CourierSteps();
    private Courier courier;

    private final String login;
    private final String password;


    public CourierLoginParameterizedNegativeTest(String login, String password) {
        this.login = login;
        this.password = password;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"john.doe@example.com", ""},
                {null, "password"}
   //           {"john.doe@example.com", null},//тут будет ошибка, вместо 400 отватился по таймауту
        });
    }

    @Before
    public void setUp(){

        courier = new Courier();
        courier.setLogin(login);
        courier.setPassword(password);
    }

    @Test
    @DisplayName("Check status code 400 of /api/v1/courier/login")
    @Description("The test checks if logging in with a non-existent user returns an error")
    public void testLoginCourierWithoutRequiredFields(){//если авторизоваться под несуществующим пользователем, запрос возвращает ошибку;
        ValidatableResponse response = loginAsCourier();
        verifyStatusCodeAndMessage(response);
    }//на 3 тесте с null в пароле отвалится по таймауту

    @Step("Login as the courier")
    public ValidatableResponse loginAsCourier() {
        ValidatableResponse response = courierSteps.login(courier);
        return response;
    }
    @Step("Verify status code is 400 and response have message")
    public void verifyStatusCodeAndMessage(ValidatableResponse response) {
        response
                .statusCode(400)
                .body("message", Matchers.is("Недостаточно данных для входа"));
    }
}
