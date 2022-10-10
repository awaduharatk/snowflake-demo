package snowflake.batch.demo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "snowflake")
public class SnowFlakeProperties {

    private String user;

    private String pass;

    private String warehouse;

    private String db;

    private String schema;

    private String url;

}
