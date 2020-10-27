package com.legends.cloud.web.config;

import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.cloud.context.scope.refresh.RefreshScope;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @Author herion
 * @Description 热更新配置
 * @Date  2019/4/25
 * @Param 
 * @return 
 **/
@Slf4j
@Component
public class ApolloPropertiesRefresher implements ApplicationContextAware {

    ApplicationContext applicationContext;

    @Autowired
    RefreshScope refreshScope;

    @ApolloConfigChangeListener
    public void onChange(ConfigChangeEvent changeEvent){
//         boolean eurekaPropertiesChanged = false;
//         for (String changedKey : changeEvent.changedKeys()) {
//             if (changedKey.startsWith("eureka.")) {
//                 log.info("===============================================================");
//                 log.info("changedKey:{} value:{}",changedKey,changeEvent.getChange(changedKey));
//                 ConfigChange configChange = changeEvent.getChange(changedKey);
//                 configChange.getOldValue();
//                 eurekaPropertiesChanged = true;
//                 break;
//             }
//         }
        refreshProperties(changeEvent);
//        if (eurekaPropertiesChanged) {
//            refreshEurekaProperties(changeEvent);
//        }
    }
    public void refreshProperties(ConfigChangeEvent changeEvent){
        this.applicationContext.publishEvent(new EnvironmentChangeEvent(changeEvent.changedKeys()));
        refreshScope.refreshAll();
    }



    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}