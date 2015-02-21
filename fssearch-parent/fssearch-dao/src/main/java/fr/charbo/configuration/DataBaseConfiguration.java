package fr.charbo.configuration;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * The Class DataBaseConfiguration.
 */
@Configuration
@EnableJpaRepositories(basePackages = {"fr.charbo.repository"})
public class DataBaseConfiguration {

  /**
   * Data source.
   *
   * @return the data source
   */
  @Bean
  public DataSource dataSource() {
    return new EmbeddedDatabaseBuilder()
    .setType(EmbeddedDatabaseType.HSQL)
    .addScript("classpath:schema.sql")
    .addScript("classpath:datas.sql")
    .build();
  }

  /**
   * Entity manager factory.
   *
   * @return the entity manager factory
   */
  @Bean
  public EntityManagerFactory entityManagerFactory() {
    final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
    vendorAdapter.setGenerateDdl(true);
    final LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
    factory.setJpaVendorAdapter(vendorAdapter);
    factory.setPackagesToScan("fr.charbo.domain");
    factory.setDataSource(this.dataSource());
    factory.afterPropertiesSet();
    return factory.getObject();
  }

  /**
   * Transaction manager.
   *
   * @return the platform transaction manager
   */
  @Bean
  public PlatformTransactionManager transactionManager() {
    final JpaTransactionManager txManager = new JpaTransactionManager();
    txManager.setEntityManagerFactory(this.entityManagerFactory());
    return txManager;
  }

}
