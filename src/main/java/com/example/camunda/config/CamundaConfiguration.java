package com.example.camunda.config;


import java.io.IOException;
import javax.sql.DataSource;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.spring.ProcessEngineFactoryBean;
import org.camunda.bpm.engine.spring.SpringProcessEngineConfiguration;
import org.camunda.bpm.engine.spring.SpringProcessEngineServicesConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@Import( SpringProcessEngineServicesConfiguration.class )
public class CamundaConfiguration {

  @Value("${camunda.bpm.history-level:full}")
  private String historyLevel;


  // configure data source via application.properties
  @Autowired
  private DataSource dataSource;

  @Autowired
  private ResourcePatternResolver resourceLoader;

  @Bean
  public SpringProcessEngineConfiguration processEngineConfiguration() throws IOException {
    final SpringProcessEngineConfiguration config = new SpringProcessEngineConfiguration();

    config.setDataSource(dataSource);
    config.setDatabaseSchemaUpdate("true");

    config.setTransactionManager(transactionManager());

    config.setHistory(historyLevel);

    config.setJobExecutorActivate(true);
    config.setMetricsEnabled(false);

    // deploy all processes from folder 'processes'
    final Resource[] resources = resourceLoader.getResources("classpath:/processes/*.bpmn");
    config.setDeploymentResources(resources);

    return config;
  }

  @Bean
  public PlatformTransactionManager transactionManager() {
    return new DataSourceTransactionManager(dataSource);
  }

  @Bean
  public ProcessEngineFactoryBean processEngine() throws IOException {
    final ProcessEngineFactoryBean factoryBean = new ProcessEngineFactoryBean();
    factoryBean.setProcessEngineConfiguration(processEngineConfiguration());
    return factoryBean;
  }

  @Bean
  public ProcessEngine processEngine(ProcessEngineFactoryBean factoryBean) throws Exception {
      return factoryBean.getObject();
  }
}
