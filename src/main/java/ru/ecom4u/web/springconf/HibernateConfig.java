package ru.ecom4u.web.springconf;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.jolbox.bonecp.BoneCPDataSource;

@Configuration
@EnableTransactionManagement
public class HibernateConfig {

	@Value("#{properties['DB_DRIVER_CLASS']}")
	private String dbDriver;

	@Value("#{properties['DB_USER']}")
	private String dbUser;

	@Value("#{properties['DB_PASSWORD']}")
	private String dbPwd;

	@Value("#{properties['DB_URL']}")
	private String dbUrl;

	@Value("#{properties['DB_CONNECTIONS']}")
	private int dbMaxConect;

	@Value("#{properties['DB_TIMEOUT']}")
	private int dbTimeOut;

	@Value("#{T(ru.ecom4u.web.springconf.PropertiesConfig).slice(properties, 'hibernate.')}")
	private Properties hibernateProperties;

	@Bean
	public DataSource dataSource() {
		BoneCPDataSource dataSource = new BoneCPDataSource();
		dataSource.setDriverClass(dbDriver);
		dataSource.setJdbcUrl(dbUrl);
		dataSource.setUsername(dbUser);
		dataSource.setPassword(dbPwd);
		dataSource.setConnectionTimeoutInMs(dbTimeOut);

		// Hardcoded properties, adjust if required
		int partitionCount = dbMaxConect > 2 ? 2 : 1;
		dataSource.setMaxConnectionsPerPartition(dbMaxConect / partitionCount);
		dataSource.setPartitionCount(partitionCount);
		dataSource.setReleaseHelperThreads(1);

		return dataSource;
	}

	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource());
		sessionFactory.setPackagesToScan(new String[] { "ru.ecom4u.web.domain.db.entities" });
		sessionFactory.setHibernateProperties(hibernateProperties);
		return sessionFactory;
	}

	@Bean
	public HibernateTransactionManager transactionManager() {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(sessionFactory().getObject());
		return transactionManager;
	}
}
