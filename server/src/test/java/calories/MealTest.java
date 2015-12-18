package calories;

/**
 * Created by msciab on 13/12/15.
 */

import com.jayway.restassured.parsing.Parser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static com.jayway.restassured.RestAssured.*;
import static org.hamcrest.Matchers.is;

/**
 * Created by msciab on 27/06/15.
 */
@RunWith(JUnit4.class)
public class MealTest extends TestUtil {
    int ticket;

    @Before
    public void login() {
        ticket = given()
                .formParam("username", "bob")
                .formParam("password", "password")
                .post("/login").then()
                .log().body()
                .extract().path("ticket[1]");

        System.out.println("**** " + ticket);
    }

    @After
    public void logout() {

        //get("/logout/" + ticket).then().log().body();
    }

    @Test
    public void testAddRemove() {
        String id = given()
                .contentType("application/json; charset=UTF-8")
                .body(map("date", "09-07-1968",
                        "time", "17:55",
                        "meal", "Lunch",
                        "calories", "800")
                ).post("/meal/" + ticket).then()
                .log().body()
                .extract().path("meals[0].id");

        System.out.println(" >>>> " + id);
        // register
        given().contentType("application/json; charset=UTF-8")
                //.body("\""+id+"\"")
                .delete("/meal/" + ticket + "/" + id).then()
                .log().body()
        ;
    }
}