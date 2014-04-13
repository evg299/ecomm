package ru.ecom4u.web.utils;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;

import java.io.IOException;

/**
 * Created by Evgeny(e299792459@gmail.com) on 09.04.14.
 */
public class JsonUtil {
    private static ObjectMapper mapper = new ObjectMapper();

    public static String toJSON(Object object) {
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();

        try {
            return ow.writeValueAsString(object);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
