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
public class CourierCreatParameterizedNegativeTest extends AbstractTest {

    private final String login;
    private final String password;
    private final CourierSteps courierSteps = new CourierSteps();
    private Courier courier;

    public CourierCreatParameterizedNegativeTest(String login, String password) {
        this.login = login;
        this.password = password;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {null, "password"},
                {"john.doe@example.com", null},
        });
    }

    @Before
    public void setUp() {
        courier = new Courier();
        courier.setLogin(login);
        courier.setPassword(password);
    }

    @Test
    @DisplayName("Check status code 400 of /api/v1/courier")
    @Description("Parameterized test for /api/v1/courier endpoint. If none of the fields are requested, an error is returned")
    public void testCreatingCourierWithoutRequiredFields() {
        ValidatableResponse response = createCourier();
        checkStatusCodeAndMessage(response);
    }

    @Step("Create a courier")
    public ValidatableResponse createCourier() {
        return courierSteps.createCourier(courier);
    }
    @Step("Check status code 409 and body contains error message")
    public void checkStatusCodeAndMessage(ValidatableResponse response) {
        response
                .statusCode(400)
                .body("message", Matchers.is("Недостаточно данных для создания учетной записи"));
    }
}
