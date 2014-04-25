package ru.ecom4u.web.services;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Locale;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import ru.ecom4u.web.services.auxi.GlobalConstants;

@Service
public class MailService {

	@Value("#{properties['mail.from']}")
	private String fromEmail;

	@Autowired
	private MailSender mailSender;

	public boolean sendEmail(String to, String title, String htmlContent) {
		try {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setFrom(fromEmail);
			message.setTo(to);
			message.setSubject(title);
			message.setText(htmlContent);
			mailSender.send(message);
			return true;
		} catch (Throwable t) {
			t.printStackTrace();
			return false;
		}
	}

	public String getTemplate(String name, Locale locale) {
		String fname = name + "_" + locale + ".html";
		System.out.println(GlobalConstants.MAIL_TPL_PACKAGE_LOACATION + fname);
		
		InputStream inputStream = getClass().getResourceAsStream(GlobalConstants.MAIL_TPL_PACKAGE_LOACATION + fname);
		StringWriter writer = new StringWriter();
		try {
			IOUtils.copy(inputStream, writer, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		String template = writer.toString();
		try {
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
		return template;
	}
}
