## Spring Cloud 与Spring Boot版本对比

| Spring Cloud             | Spring Boot                                    |
| ------------------------ | ---------------------------------------------- |
| Angel版本                | 兼容Spring Boot 1.2.x                          |
| Brixton版本              | 兼容Spring Boot 1.3.x，也兼容Spring Boot 1.4.x |
| Camden版本               | 兼容Spring Boot 1.4.x，也兼容Spring Boot 1.5.x |
| Dalston版本、Edgware版本 | 兼容Spring Boot 1.5.x，不兼容Spring Boot 2.0.x |
| Finchley版本             | 兼容Spring Boot 2.0.x，不兼容Spring Boot 1.5.x |
| Greenwich版本            | 兼容Spring Boot 2.1.x                          |
| Hoxton版本               | 兼容Spring Boot 2.2.x                          |

------
- springcloud-config-server 
   客户端通过直接调用配置中心的server端来获取配置文件信息。这样就存在了一个问题，客户端和服务端的耦合性太高，如果server端要做集群，客户端只能通过原始的方式来路由，server端改变IP地址的时候，客户端也需要修改配置，不符合springcloud服务治理的理念。springcloud提供了这样的解决方案，我们只需要将server端当做一个服务注册到eureka中，client端去eureka中去获取配置中心server端的服务既可。主要是去掉了spring.cloud.config.uri直接指向server端地址的配置，增加了最后的三个配置：
1.spring.cloud.config.discovery.enabled ：开启Config服务发现支持
2.spring.cloud.config.discovery.serviceId ：指定server端的name,也就是server端spring.application.name的值
3.eureka.client.serviceUrl.defaultZone ：指向注册中心的地址
通过config server集成spring-cloud-starter-bus-kafka 在远程修改配置文件的时候通过http://config-server:port/actuator/bus-refresh 来刷新所有springcloud-config-client，无须重启springcloud-config-client,也无须在springcloud-config-client上一个一个单独通过http://configclientID:port/actuator/refresh 来刷新config-client的数据,借助Git仓库的WebHook，我们就可轻松实现配置的自动刷新

--------

#### 相关文章

- [一份微服务架构手稿图，彻底搞定微服务核心原理！](https://mp.weixin.qq.com/s/c3fQsf7oNOk9xfA95CtKHw)

- [1.5W 字搞懂 Spring Cloud，太牛了！](https://mp.weixin.qq.com/s/EHPKm50KmHq_KZIHyVef3A)

- [基于SpringBoot2.x搭建一个高可用的注册中心(Eureka)](https://mp.weixin.qq.com/s/9PYHN7qRA3YF65Fr8mYn4Q)

