package calories;

/**
 * Created by msciab on 13/12/15.
 */

import com.jayway.restassured.parsing.Parser;
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
public class CaloriesTest extends TestUtil {

    //@Test
    public void testPost() {
        with()
                .body(map("date", "09-07-1968",
                        "time", "17:55",
                        "meal", "Lunch",
                        "calories", "800")
                ).post("/calories/123").then()
                .log().body()
        ;
    }


}