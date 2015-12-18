package calories;

import com.jayway.restassured.http.ContentType;
import org.junit.Test;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

/**
 * Created by msciab on 17/12/15.
 */
public class DemoTest extends TestUtil {

    //@Test
    public void testRegister() {
        // test wrong user
        given().contentType("application/json; charset=UTF-8")
                .body(map(
                        "username", "pinco pallino",
                        "name", "Pinco Pallino",
                        "password", "welcome",
                        "calories", "1000"))
                .when()
                .post("/register").then()
                .log().body()
                .body("ticket[0]", is(0))
                .body("ticket[1]", is("Username must be all letter or digits!"))
        ;
    }

}