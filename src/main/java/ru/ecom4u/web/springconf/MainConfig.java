package ru.ecom4u.web.springconf;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ HibernateConfig.class, PropertiesConfig.class })
@ComponentScan(value = {"ru.ecom4u.web.domain.db", "ru.ecom4u.web.services" })
public class MainConfig {

}
