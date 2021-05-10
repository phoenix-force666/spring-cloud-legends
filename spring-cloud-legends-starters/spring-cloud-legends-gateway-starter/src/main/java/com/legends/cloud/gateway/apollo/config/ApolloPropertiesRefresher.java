//package com.legends.cloud.gateway.apollo.config;
//
//import com.ctrip.framework.apollo.model.ConfigChangeEvent;
//import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
//import com.ctrip.framework.apollo.spring.annotation.ApolloConfigRegistrar;
//import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
//import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
//import org.springframework.cloud.context.scope.refresh.RefreshScope;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.ApplicationContextAware;
//import org.springframework.context.annotation.Configuration;
//
///**
// * 配置中心自动装配
// */
//@Configuration
//@ConditionalOnBean(value = {ApplicationContext.class,RefreshScope.class})
//@ConditionalOnClass(ApolloConfigRegistrar.class)
//@EnableApolloConfig
//public class ApolloPropertiesRefresher implements ApplicationContextAware {
//
//    ApplicationContext applicationContext;
//
//    @Autowired
//    RefreshScope refreshScope;
//
//    @ApolloConfigChangeListener
//    public void onChange(ConfigChangeEvent changeEvent)
//    {
//        refreshProperties(changeEvent);
//    }
//
//    public void refreshProperties(ConfigChangeEvent changeEvent)
//    {
//        this.applicationContext.publishEvent(new EnvironmentChangeEvent(changeEvent.changedKeys()));
//        this.refreshScope.refreshAll();
//    }
//
//    @Override
//    public void setApplicationContext(ApplicationContext applicationContext)
//            throws BeansException
//    {
//        this.applicationContext = applicationContext;
//    }
//}
