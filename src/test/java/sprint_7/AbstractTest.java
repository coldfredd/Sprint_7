package sprint_7;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import org.junit.Before;
import static sprint_7.config.RestConfig.HOST;

public class AbstractTest {
    @Before
    public void setUpRestAssured(){
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri(HOST)
                .build();
    }
}
