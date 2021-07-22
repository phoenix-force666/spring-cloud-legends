# spring-cloud-legends
spring cloud legends 一个更易于新手快速搭建微服务治理微服务开发平台。此项目包含开发分布式应用微服务的必需组件，为必要的基础框架做了统一兼容调试，方便开发者通过 Spring Cloud 编程模型轻松使用这些组件来开发分布式应用服务。
依托 Spring Cloud、Spring Cloud Alibaba 通过spring cloud legends快速搭建分布式应用系统。
## 主要功能
### **spring-cloud-legends-gateway-starter:**  基于spring cloud gateway 快速构建网关服务
#### 内置功能：  
- 1、默认使用Alibaba Sentinel 进行流量控制，断路和系统自适应保护
- 2、默认使用Alibaba Nacos 作为注册中心与配置中心
- 3、默认支持Prometheus监控
- 4、knife4j 聚合网关所有转发的微服务API文档
- 5、报文加解密、加签验签、敏感字段加解密、token验证
- 6、支持多渠道接入秘钥分配
- 7、防SQL注入
### spring-cloud-legends-web-starter
#### 内置功能
- 基础错误码、基础返回码、常用工具类、规范请求响应报文
- 统一异常处理、统一日志
- 默认使用Alibaba Sentinel 进行流量控制，断路和系统自适应保护
- 默认使用Alibaba Nacos 作为注册中心与配置中心，支持配置文件加密
- 默认使用swagger文档管理，支持API文档生成、API调试
- 默认支持Prometheus监控
