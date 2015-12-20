package calories;

/**
 * Created by msciab on 13/12/15.
 */

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


/**
 * Created by msciab on 27/06/15.
 */
@RunWith(JUnit4.class)
public class MealFilterTest extends TestUtil {
    int ticket;

    @Before
    public void login() {
        ticket = given()
                .formParam("username", "admin")
                .formParam("password", "welcome1")
                .post("/login").then()
                //.log().body()
                .extract().path("ticket[1]");
        System.out.println("**** " + ticket);
    }

    @After
    public void logout() {
        //get("/logout/" + ticket).then().log().body();
    }

    @Test
    public void testFilterAll() {
        given()
                .get("/meal/" + ticket).then()
                .log().body()
                .assertThat()
                .body("meals", hasSize(5))
        ;

    }

    @Test
    public void testFilterFromDate() {
        given().param("fromDate", "2015-11-20")
                .get("/meal/" + ticket)
                .then()
                .log().body()
                .assertThat()
                .body("meals", hasSize(4))
        ;
    }

    @Test
    public void testFilterFromToDate() {
        given().param("fromDate", "2015-11-20")
                .param("toDate", "2015-11-21")
                .get("/meal/" + ticket)
                .then()
                .log().body()
                .assertThat()
                .body("meals", hasSize(3))
        ;
    }

    @Test
    public void testFilterFromTime() {
        given().param("fromDate", "2015-11-19")
                .param("toDate", "2015-11-22")
                .param("fromTime", "07:30")
                .get("/meal/" + ticket)
                .then()
                .log().body()
                .assertThat()
                .body("meals", hasSize(4))
        ;
    }

    @Test
    public void testFilterToTime() {
        given().param("fromDate", "2015-11-19")
                .param("toDate", "2015-11-22")
                .param("fromTime", "07:00")
                .param("toTime", "11:00")
                .get("/meal/" + ticket)
                .then()
                .log().body()
                .assertThat()
                .body("meals", hasSize(2))
        ;
    }

}