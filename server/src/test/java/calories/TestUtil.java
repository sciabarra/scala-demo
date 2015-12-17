package calories;

import com.jayway.restassured.parsing.Parser;
import org.junit.Before;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.jayway.restassured.RestAssured.defaultParser;
import static com.jayway.restassured.RestAssured.port;

/**
 * Created by msciab on 13/12/15.
 */
public class TestUtil {

    @Before
    public void init() {
        port = 9000;
        defaultParser = Parser.JSON;
    }

    public static Map<String, ?> map(Object... args) {
        Map<String, Object> m = new HashMap<String, Object>();
        for (int i = 0; i < args.length - 1; i += 2)
            m.put(args[i].toString(), args[i + 1]);
        return m;
    }

    public static List<Object> list(Object... args) {
        List<Object> list = new LinkedList<Object>();
        for (Object o : args)
            list.add(o);
        return list;
    }
}
