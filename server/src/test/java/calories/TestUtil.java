package calories;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by msciab on 13/12/15.
 */
public class TestUtil {
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
