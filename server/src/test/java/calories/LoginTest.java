package calories;

/**
 * Created by msciab on 13/12/15.
 */

import static calories.TestUtil.*;
import static com.jayway.restassured.RestAssured.*;
import static com.jayway.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.parsing.Parser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Created by msciab on 27/06/15.
 */
@RunWith(JUnit4.class)
public class LoginTest {

    @Before
    public void init() {
        port = 9000;
        defaultParser = Parser.JSON;
    }

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
                //.log().body()
                .extract().path("ticket[1]");

        given().
                get("/logout/" + ticket).then()
                //.log().body()
                .body("ticket[1]", is("logged out"))
        ;
        given().
                get("/logout/" + ticket).then()
                //.log().body()
                .body("ticket[1]", is("no such ticket"))
        ;
    }

    @Test
    public void testLoginKo() {
        given().
                formParam("username", "admin1").
                formParam("password", "welcome1").
                post("/login").then()
                .body("ticket[0]", is(0))
                .body("ticket[1]", is("no such user - please register"))
        //.log().body()
        ;
        given().
                formParam("username", "admin").
                formParam("password", "welcome").
                post("/login").then()
                .body("ticket[0]", is(0))
                .body("ticket[1]", is("username or password incorrect"))
        //.log().body()
        ;
    }

    @Test
    public void testRegister() {
        // test wrong user
        given().
                formParam("username", "pinco pallino").
                formParam("name", "Pinco Pallino").
                formParam("password", "welcome").
                post("/register").then()
                //.log().body()
                .body("ticket[0]", is(0))
                .body("ticket[1]", is("username must be all letter or digits"))
        ;
        // test existing user
        given().
                formParam("username", "bob").
                formParam("password", "welcome").
                formParam("name", "Bob").
                post("/register").then()
                //.log().body()
                .body("ticket[0]", is(0))
                .body("ticket[1]", is("username already exists"))
        ;

        // register
        given().
                formParam("username", "mike").
                formParam("password", "hello").
                formParam("name", "Michele")
                .post("/register").then()
                //.log().body()
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
                //.log().body()
                .extract().path("ticket[1]");

        // ri-register
        given().
                formParam("username", "mike").
                formParam("password", "hello").
                formParam("name", "Michele")
                .post("/register").then()
                //.log().body()
                .body("ticket[0]", is(0))
                .body("ticket[1]", is("username already exists"))
        ;

        //  login again
        get("/cleanup/mike").then()
                .body("ticket[1]", is("deleted mike"))
        //.log().body()
        ;

        get("/cleanup/mike").then()
                .body("ticket[1]", is("not found mike"))
        //.log().body()
        ;

        // no login
        given().
                formParam("username", "mike").
                formParam("password", "hello").
                post("/login").then()
                //.log().body()
                .body("ticket[0]", is(0))
                .body("ticket[1]", is("no such user - please register"))
        ;

    }
}