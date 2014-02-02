package ru.ecom4u.web.springconf;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Configuration
@Import({ HibernateConfig.class, PropertiesConfig.class })
@ComponentScan(value = { "ru.ecom4u.web.domain.db", "ru.ecom4u.web.services" })
public class MainConfig {

	@Value("#{properties['mail.host']}")
	private String mailHost;

	@Value("#{properties['mail.port']}")
	private int mailPort;

	@Value("#{properties['mail.username']}")
	private String mailUsername;

	@Value("#{properties['mail.password']}")
	private String mailPassword;

	@Value("#{properties['mail.smtp.auth']}")
	private boolean mailSmtpAuth;

	@Value("#{properties['mail.smtp.starttls.enable']}")
	private boolean mailSmtpStarttlsEnable;

	@Bean
	public JavaMailSender mailSender() {
		JavaMailSenderImpl mailSenderImpl = new JavaMailSenderImpl();
		mailSenderImpl.setHost(mailHost);
		mailSenderImpl.setPort(mailPort);
		mailSenderImpl.setUsername(mailUsername);
		mailSenderImpl.setPassword(mailPassword);

		Properties javaMailProperties = new Properties();
		javaMailProperties.setProperty("mail.smtp.auth", "" + mailSmtpAuth);
		javaMailProperties.setProperty("mail.smtp.starttls.enable", "" + mailSmtpStarttlsEnable);
		mailSenderImpl.setJavaMailProperties(javaMailProperties);

		return mailSenderImpl;
	}

	@Bean
	public MultipartResolver multipartResolver() {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setMaxUploadSize(64 * 1024 * 1024);
		return multipartResolver;
	}
}
