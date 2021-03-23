# spring-cloud-legends-apollo-starter
主要功能如下：
- 增强了apollo.bootstrap.enabled 配置，官方该配置支持在应用启动阶段是否向Spring容器注入被托管的properties文件配置信息
而在这里增强是否开启Apollo的功能
- 支持配置热更新  
- 自动集成Apollo
- 支持配置加密
  

## 配置文件关闭开启Apollo配置
    - apollo.bootstrap.enabled=true 开启使用Apollo
    - apollo.bootstrap.enabled=false 关闭使用Apollo配置

## 常用配置说明
- apollo.meta 的配置中心地址
    apollo.meta=http://localhost:8080
- app.id 应用id
    #指定apollo上面对应的应用id,程序会根据这个id去找到apollo上面对应的应用的配置
    app.id=spring-cloud-legends-apollo
   
- apollo.cluster 指定对应的集群
   apollo.cluster=local
  
- 在应用启动阶段是否向Spring容器注入被托管的properties文件配置信息，是否开启Apollo
  apollo.bootstrap.enabled=true 
  
- apollo.bootstrap.namespaces 指定要加载的命名空间  
  apollo.bootstrap.namespaces=application,public-legends

-   
  
## 配置文件加密配置
### 密码加密命令
    jasypt加密
    java -cp jasypt-1.9.2.jar org.jasypt.intf.cli.JasyptPBEStringEncryptionCLI input="123456" password="mysalt" algorithm=PBEWithMD5AndDES
    
    input：密码
    
    password：盐
    
    algorithm：加密方式
### 加密秘钥配置
jasypt.encryptor.password=mysalt

### 配置文件中配置加密
```
    password=ENC(密文)
```

