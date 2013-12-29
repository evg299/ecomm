package ru.ecom4u.web.spring;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;

public class MessageSourceImpl implements MessageSource {
	private String encoding = "UTF-8";
	private String[] propertiesSources;
	private Map<Locale, Properties> propMap = new HashMap<Locale, Properties>();

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public String[] getPropertiesSources() {
		return propertiesSources;
	}

	public void setPropertiesSources(String[] propertiesSources) {
		this.propertiesSources = propertiesSources;
	}

	private Properties fillProps(Locale locale) {
		Properties properties = new Properties();
		for (String source : propertiesSources) {
			String oldSource = source;
			if (source.endsWith(".properties")) {
				source = source.substring(0, source.length() - ".properties".length()) + "_" + locale + ".properties";
				System.out.println(source);
			}

			try {
				copy(properties, this.createProperties(source, encoding));
			} catch (IOException e) {
				try {
					copy(properties, this.createProperties(oldSource, encoding));
				} catch (IOException e1) {
					// --
				}
			}
		}

		return properties;
	}

	private Properties createProperties(String source, String encoding) throws IOException {
		Properties prop = new Properties();
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		InputStream stream = loader.getResourceAsStream(source);

		StringWriter writer = new StringWriter();
		IOUtils.copy(stream, writer, encoding);
		String propString = writer.toString();
		stream.close();

		prop.load(new StringReader(propString));

		return prop;
	}

	private void copy(Properties target, Properties source) {
		Enumeration<?> e = source.propertyNames();
		while (e.hasMoreElements()) {
			String key = (String) e.nextElement();
			target.setProperty(key, source.getProperty(key));
		}
	}

	@Override
	public String getMessage(String code, Object[] args, String defaultMessage, Locale locale) {
		String msg = resolveMsg(code, args, locale);

		return null != msg ? msg : defaultMessage;
	}

	@Override
	public String getMessage(String code, Object[] args, Locale locale) throws NoSuchMessageException {
		String msg = resolveMsg(code, args, locale);

		if (null != msg)
			return msg;

		throw new NoSuchMessageException(code, locale);
	}

	@Override
	public String getMessage(MessageSourceResolvable resolvable, Locale locale) throws NoSuchMessageException {
		String msg = null;
		for (String code : resolvable.getCodes()) {
			msg = resolveMsg(code, resolvable.getArguments(), locale);
			if (null != msg)
				break;
		}

		if (null != msg)
			return msg;

		throw new NoSuchMessageException("" + Arrays.asList(resolvable.getCodes()), locale);
	}

	private String resolveMsg(String code, Object[] args, Locale locale) {
		Properties properties = this.propMap.get(locale);
		if (null == properties) {
			properties = this.fillProps(locale);
			this.propMap.put(locale, properties);
		}

		if (null != properties) {
			String msgTpl = properties.getProperty(code);
			if (null != msgTpl) {
				if (null != args) {
					int i = 0;
					for (Object arg : args) {
						msgTpl = msgTpl.replaceAll("{" + i + "}", arg.toString());
						i++;
					}
				}

				return msgTpl;
			}
		}

		return null;
	}

}
