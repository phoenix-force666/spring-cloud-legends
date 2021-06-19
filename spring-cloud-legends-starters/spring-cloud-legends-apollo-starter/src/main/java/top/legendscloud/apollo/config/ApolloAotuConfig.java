package top.legendscloud.apollo.config;

import com.ctrip.framework.apollo.spring.annotation.ApolloConfigRegistrar;
import com.ctrip.framework.apollo.spring.config.PropertySourcesProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


/**
 * 默认开启Apollo
 */
@Configuration
@Import({ApolloConfigRegistrar.class})
@ConditionalOnExpression(value = "${apollo.bootstrap.enabled:true}")
@ConditionalOnMissingBean({PropertySourcesProcessor.class})
public class ApolloAotuConfig {

}
