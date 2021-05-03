package pers.clare.demo.data;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.util.StringUtils;
import pers.clare.hisql.annotation.EnableHiSql;

import javax.sql.DataSource;

@EnableHiSql(
        basePackages = "pers.clare.demo.data.sql"
        , dataSourceRef = DemoHiSqlConfig.DATA_SOURCE
)
public class DemoHiSqlConfig {
    public static final String PREFIX = "demo";
    static final String DATA_SOURCE_PROPERTIES = "spring.datasource." + PREFIX;

    public static final String DATA_SOURCE = PREFIX + "DataSource";
    public static final String DATA_SOURCE_INITIALIZER = PREFIX + "DataSourceInitializer";

    @Primary
    @Bean(name = DATA_SOURCE)
    @ConfigurationProperties(prefix = DATA_SOURCE_PROPERTIES)
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .build();
    }

    @Primary
    @Bean(name = DATA_SOURCE_INITIALIZER)
    public DataSourceInitializer dataSourceInitializer(
            @Qualifier(DATA_SOURCE) DataSource datasource
            , @Value("${" + DATA_SOURCE_PROPERTIES + ".hikari.schema}") String schema
    ) {
        DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
        dataSourceInitializer.setDataSource(datasource);
        if (!StringUtils.isEmpty(schema)) {
            ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
            resourceDatabasePopulator.addScript(new ClassPathResource(schema));
            dataSourceInitializer.setDatabasePopulator(resourceDatabasePopulator);
        }
        return dataSourceInitializer;
    }
}
