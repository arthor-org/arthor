package com.arthor.core.spring.boot.stater.store;

import com.arthor.core.spring.boot.stater.store.mysql.MybatisStoreAutoConfiguration;
import org.springframework.context.annotation.Import;

@Import({
        MybatisStoreAutoConfiguration.class
})
//@ConditionalOnBean(DataSource.class)
//@AutoConfigureAfter(DataSourceAutoConfiguration.class)
public class StoreAutoConfiguration {


}
