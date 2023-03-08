package com.arthor.core.spring.boot.stater.store.mysql;

import com.arthor.core.application.store.ApplicationStore;
import com.arthor.core.application.store.mysql.ApplicationMapper;
import com.arthor.core.application.store.mysql.MysqlApplicationStore;
import com.arthor.core.build.store.BuildRecordStore;
import com.arthor.core.build.store.mysql.BuildRecordMapper;
import com.arthor.core.build.store.mysql.MysqlBuildRecordStore;
import com.arthor.core.counter.store.CounterStore;
import com.arthor.core.counter.store.mysql.CounterMapper;
import com.arthor.core.counter.store.mysql.MysqlCounterStore;
import com.arthor.core.deploy.store.DeployRecordStore;
import com.arthor.core.deploy.store.DeploymentStore;
import com.arthor.core.deploy.store.mysql.DeployRecordMapper;
import com.arthor.core.deploy.store.mysql.DeploymentMapper;
import com.arthor.core.deploy.store.mysql.MysqlDeployRecordStore;
import com.arthor.core.deploy.store.mysql.MysqlDeploymentStore;
import com.arthor.core.env.store.EnvStore;
import com.arthor.core.env.store.mysql.EnvMapper;
import com.arthor.core.env.store.mysql.MysqlEnvStore;
import com.arthor.core.feature.store.FeatureStore;
import com.arthor.core.feature.store.mysql.FeatureMapper;
import com.arthor.core.feature.store.mysql.MysqlFeatureStore;
import com.arthor.core.lock.store.LockStore;
import com.arthor.core.lock.store.mysql.LockMapper;
import com.arthor.core.lock.store.mysql.MysqlLockStore;
import com.arthor.core.pipeline.store.PipelineStore;
import com.arthor.core.pipeline.store.mysql.MysqlPipelineStore;
import com.arthor.core.pipeline.store.mysql.PipelineMapper;
import com.arthor.core.user.store.UserStore;
import com.arthor.core.user.store.mysql.MysqlUserStore;
import com.arthor.core.user.store.mysql.UserMapper;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@Configuration // see line 270 in org.springframework.context.annotation.ConfigurationClassParser.doProcessConfigurationClass
@ConditionalOnProperty(value = "store.mysql.enabled", matchIfMissing = true)
public class MybatisStoreAutoConfiguration {

    private final static String MAPPER_PACKAGE_NAME = "com.arthor.core.**.store.mysql";
    private final static String MAPPER_LOCATIONS = "store/mysql/mapper/*Mapper.xml";

    @Configuration
    @MapperScan(MAPPER_PACKAGE_NAME) // 不能忘记哈,我的哥
    public static class MybatisCoreAutoConfiguration {

        @Bean
        public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
            SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
            sqlSessionFactoryBean.setDataSource(dataSource);
            org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
            configuration.addMappers(MAPPER_PACKAGE_NAME);
            configuration.setLogImpl(StdOutImpl.class);
            configuration.setDefaultExecutorType(ExecutorType.REUSE);
            configuration.setDefaultStatementTimeout(25000);
            sqlSessionFactoryBean.setConfiguration(configuration);
            PathMatchingResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
            Resource[] resources = resourcePatternResolver.getResources(MAPPER_LOCATIONS);
            sqlSessionFactoryBean.setMapperLocations(resources);
            return sqlSessionFactoryBean.getObject();
        }

    }

    @Configuration
    @AutoConfigureAfter(MybatisCoreAutoConfiguration.class)
    public static class MysqlStoreAutoConfiguration {

        @Bean
        @ConditionalOnMissingBean(value = ApplicationStore.class)
        public ApplicationStore applicationStore(ApplicationMapper applicationMapper) {
            return new MysqlApplicationStore(applicationMapper);
        }

        @Bean
        @ConditionalOnMissingBean(value = FeatureStore.class)
        public FeatureStore featureStore(FeatureMapper featureMapper) {
            return new MysqlFeatureStore(featureMapper);
        }

        @Bean
        @ConditionalOnMissingBean(value = BuildRecordStore.class)
        public BuildRecordStore buildRecordStore(BuildRecordMapper buildRecordMapper) {
            return new MysqlBuildRecordStore(buildRecordMapper);
        }

        @Bean
        @ConditionalOnMissingBean(value = DeploymentStore.class)
        public DeploymentStore deploymentStore(DeploymentMapper deploymentMapper) {
            return new MysqlDeploymentStore(deploymentMapper);
        }

        @Bean
        @ConditionalOnMissingBean(value = DeployRecordStore.class)
        public DeployRecordStore deployRecordStore(DeployRecordMapper deployRecordMapper) {
            return new MysqlDeployRecordStore(deployRecordMapper);
        }

        @Bean
        @ConditionalOnMissingBean(value = CounterStore.class)
        public CounterStore counterStore(CounterMapper counterMapper) {
            return new MysqlCounterStore(counterMapper);
        }

        @Bean
        @ConditionalOnMissingBean(value = LockStore.class)
        public LockStore lockStore(LockMapper lockMapper) {
            return new MysqlLockStore(lockMapper);
        }

        @Bean
        @ConditionalOnMissingBean(value = PipelineStore.class)
        public PipelineStore pipelineStore(PipelineMapper pipelineMapper) {
            return new MysqlPipelineStore(pipelineMapper);
        }

        @Bean
        @ConditionalOnMissingBean(value = EnvStore.class)
        public EnvStore envStore(EnvMapper envMapper) {
            return new MysqlEnvStore(envMapper);
        }

        @Bean
        @ConditionalOnMissingBean(value = UserStore.class)
        public UserStore userStore(UserMapper userMapper) {
            return new MysqlUserStore(userMapper);
        }

    }

}
