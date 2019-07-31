package com.xhg.ops.workflow.config;


import com.xhg.ops.workflow.service.Impl.WorkflowCommonServiceImpl;
import org.activiti.engine.impl.cfg.IdGenerator;
import org.activiti.engine.impl.persistence.StrongUuidGenerator;
import org.activiti.engine.impl.persistence.deploy.Deployer;
import org.activiti.engine.impl.rules.RulesDeployer;
import org.activiti.spring.SpringAsyncExecutor;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.activiti.spring.boot.AbstractProcessEngineAutoConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Arrays;

/**
 * 流程引擎配置
 *
 */
@Configuration
public class ProcessConfiguration extends AbstractProcessEngineAutoConfiguration implements ApplicationListener<ApplicationStartedEvent> {
    protected static final Logger logger = LoggerFactory.getLogger(ProcessConfiguration.class);

    /**
    @Bean(name="activitiDatasourceBean")
    @ConfigurationProperties("spring.datasource")
    public DataSource activitiDataSource() {
        return DataSourceBuilder.create().build();
    }
    **/

    /**
    @Bean
    public PlatformTransactionManager dataSourceTransactionManager(@Qualifier("activitiDatasourceBean") DataSource dataSource) {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(dataSource);
        return transactionManager;
    }**/

    /**
    @Bean
    public PlatformTransactionManager dataSourceTransactionManager(DataSource dataSource) {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(dataSource);
        return transactionManager;
    }
     **/

    @Bean
    public PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver(){
        return new PathMatchingResourcePatternResolver();
    }

    @Bean
    public SpringProcessEngineConfiguration springProcessEngineConfiguration(
            DataSource dataSource,
            PlatformTransactionManager transactionManager,
            PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver,
            SpringAsyncExecutor springAsyncExecutor) throws IOException {

        //com.xhg.core.config.druid.DruidDataSourceConfig druidDataSourceConfig = new com.xhg.core.config.druid.DruidDataSourceConfig();
        //DataSource dataSource = druidDataSourceConfig.dataSource();

       // System.out.println("\n\n=========== dataSource: "+dataSource);

        SpringProcessEngineConfiguration processEngineConfiguration = baseSpringProcessEngineConfiguration(
                dataSource, transactionManager, springAsyncExecutor);
        processEngineConfiguration.setActivityFontName("宋体");
        processEngineConfiguration.setLabelFontName("宋体");
        processEngineConfiguration.setCustomPostDeployers(Arrays.<Deployer>asList(new RulesDeployer()));

        //不启动自动部署,则注释掉这2行
        //Resource[] resources = pathMatchingResourcePatternResolver.getResources("classpath*:/processes/**/*.bpmn");

        /**
        logger.info("\n\n\n\n======resources:"+resources);
        logger.info("\n\n\n\n======resources.length:"+resources.length);
        if(null != resources && resources.length>0){
            for(Resource item:resources){
                logger.info("\n-----------resource:"+item.getFilename());
            }
        }
        **/

       // processEngineConfiguration.setDeploymentResources(resources);

        processEngineConfiguration.setDeploymentResources(null);

        processEngineConfiguration.setDatabaseSchemaUpdate("false");

        processEngineConfiguration.setDbIdentityUsed(false);
        processEngineConfiguration.setAsyncExecutorActivate(false);

        //StrongUuidGenerator
        //分布式部署，解决主键冲突问题
        IdGenerator idGenerator = new StrongUuidGenerator();
         processEngineConfiguration.setIdGenerator(idGenerator);


        return processEngineConfiguration;
    }

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        System.setProperty("jxl.encoding", "UTF-8");
    }

}
