package calories;

/**
 * Created by msciab on 13/12/15.
 */

import static com.jayway.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Created by msciab on 27/06/15.
 */
@RunWith(JUnit4.class)
public class LoginTest extends TestUtil {

    @Test
    public void testLoginOkLogout() {
        int ticket = given()
                .formParam("username", "admin")
                .formParam("password", "welcome1")
                //with()
                //.body(map("username", "admin", "password", "Welcome1"))
                .post("/login").then()
                .body("role", is("admin"))
                .body("name", is("Administrator"))
                .body("username", is("admin"))
                .body("ticket[0]", is(1))
                .log().body()
                .extract().path("ticket[1]");

        given().
                get("/logout/" + ticket).then()
                .log().body()
                .body("ticket[1]", is("Logged out."))
        ;
        given().
                get("/logout/" + ticket).then()
                .log().body()
                .body("ticket[1]", is("No such ticket!"))
        ;
    }

    @Test
    public void testLoginKo() {
        given()
                .formParam("username", "admin1")
                .formParam("password", "welcome1")
                .post("/login")
                .then()
                .body("ticket[0]", is(0))
                .body("ticket[1]", is("No such user. Please register!"))
                .log().body()
        ;
        given()
                .formParam("username", "admin")
                .formParam("password", "welcome")
                .post("/login").then()
                .body("ticket[0]", is(0))
                .body("ticket[1]", is("Username or password incorrect!"))
                .log().body()
        ;
    }

    @Test
    public void testRegister() {
        // test wrong user
        given().body(map(
                "username", "pinco pallino",
                "name", "Pinco Pallino",
                "password", "welcome",
                "calories", "1000"))
                .contentType("application/json; charset=UTF-8")
                .post("/register").then()
                .log().body()
                .body("ticket[0]", is(0))
                .body("ticket[1]", is("Username must be all letter or digits!"))
        ;
        // test existing user
        given().body(map(
                "username", "bob",
                "password", "welcome",
                "calories", "1000",
                "name", "Bob"))
                .contentType("application/json; charset=UTF-8")
                .post("/register").then()
                .log().body()
                .body("ticket[0]", is(0))
                .body("ticket[1]", is("Username already exists!"))
        ;

        // register
        given().body(map(
                "username", "mike",
                "password", "hello",
                "calories", "1000",
                "name", "Michele"))
                .contentType("application/json; charset=UTF-8")
                .post("/register").then()
                .log().body()
                .body("ticket[0]", is(1))
                .body("username", is("mike"))
                .body("name", is("Michele"))
                .body("role", is("user"))
        ;

        // test login
        int ticket = given()
                .formParam("username", "mike")
                .formParam("password", "hello")
                .post("/login").then()
                .body("role", is("user"))
                .body("name", is("Michele"))
                .body("username", is("mike"))
                .body("ticket[0]", is(1))
                .log().body()
                .extract().path("ticket[1]");

        // ri-register
        given().body(map(
                "username", "mike",
                "password", "hello",
                "calories", "1000",
                "name", "Michele"))
                .contentType("application/json; charset=UTF-8")
                .post("/register").then()
                .log().body()
                .body("ticket[0]", is(0))
                .body("ticket[1]", is("Username already exists!"))
        ;

        //  login again
        get("/cleanup/mike").then()
                .body("ticket[1]", is("deleted mike"))
                .log().body()
        ;

        get("/cleanup/mike").then()
                .body("ticket[1]", is("not found mike"))
                .log().body()
        ;

        // no login
        given().
                formParam("username", "mike").
                formParam("password", "hello").
                post("/login").then()
                .log().body()
                .body("ticket[0]", is(0))
                .body("ticket[1]", is("No such user. Please register!"))
        ;

    }
}