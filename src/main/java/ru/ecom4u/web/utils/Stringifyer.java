package ru.ecom4u.web.utils;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.io.StringWriter;

/**
 * Created by Evgeny on 13.06.14.
 */
public class Stringifyer
{
    private static ObjectMapper mapper = new ObjectMapper();

    public static String stringify(Object o){
        StringWriter stringWriter = new StringWriter();
        try
        {
            mapper.writeValue(stringWriter, o);
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        return stringWriter.toString();
    }
}
