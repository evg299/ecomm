package ru.ecom4u.web;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Locale;
import java.util.Properties;

import org.apache.commons.io.IOUtils;

public class Test1 {
	public static void main(String[] args) throws IOException {
		Properties prop = new Properties();
		ClassLoader loader = Thread.currentThread().getContextClassLoader();           
		InputStream stream = loader.getResourceAsStream("ru/ecom4u/web/strings/validators_ru_RU.properties");
		
		StringWriter writer = new StringWriter();
		IOUtils.copy(stream, writer, "UTF8");
		String theString = writer.toString();
		System.out.println(theString);
		stream.close();
		
		prop.load(new StringReader(theString));
		
		System.out.println(prop.getProperty("registrationForm.fname.empty"));
		
		System.out.println(Locale.getDefault());
	}
}
