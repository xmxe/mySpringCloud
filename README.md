# mySpringCloud


- springcloud-config-server 
  - 客户端通过直接调用配置中心的server端来获取配置文件信息。这样就存在了一个问题，客户端和服务端的耦合性太高，如果server端要做集群，客户端只能通过原始的方式来路由，server端改变IP地址的时候，客户端也需要修改配置，不符合springcloud服务治理的理念。springcloud提供了这样的解决方案，我们只需要将server端当做一个服务注册到eureka中，client端去eureka中去获取配置中心server端的服务既可。主要是去掉了spring.cloud.config.uri直接指向server端地址的配置，增加了最后的三个配置：
  1. spring.cloud.config.discovery.enabled ：开启Config服务发现支持
  2. spring.cloud.config.discovery.serviceId ：指定server端的name,也就是server端spring.application.name的值
  3. eureka.client.serviceUrl.defaultZone ：指向注册中心的地址

  通过config server集成spring-cloud-starter-bus-kafka 在远程修改配置文件的时候通过http://config-server:port/actuator/bus-refresh 来刷新所有springcloud-config-client，无须重启springcloud-config-client,也无需在springcloud-config-client上一个一个单独通过http://configclientID:port/actuator/refresh来刷新config-client的数据

- spring-boot-starter-thymeleaf 模板引擎
  - spring-boot-devtools使Spring Boot应用支持热部署，无需手动重启Spring Boot应用,spring-boot-devtools是一个为开发者服务的一个模块，其中最重要的功能就是修改代码后自动启动springboot服务，速度比手动停止后再启动要快，节省出来的并不是手工操作的时间
  - 原理:主要是使用了两个ClassLoader，一个Classloader加载不会改变的类（第三方Jar包），另一个ClassLoader加载会更改的类，称为  restart ClassLoader,这样在有代码更改的时候，原来的restartClassLoader 被丢弃，重新创建一个restartClassLoader，由于需要加载的类相比较少，所以实现了较快的重启时间

- serviceone
  
  - 集成spring cloud config server、spring cloud ribbon。提供了一个由feign测试调用的接口，spring redis 做session共享，spring-cloud-starter-bus-kafka、spring-boot-starter-actuator。当需要更新远程仓库的最新资源时使用post请求 http://serviceone:port/actuator/bus-refresh 即可将所有的spring-cloud-config-client的服务无需重启即可访问最新的资源，借助Git仓库的WebHook，我们就可轻松实现配置的自动刷新
  
- servicetwo
  
  - 集成spring-cloud-config-client、spring cloud feign、spring-boot-starter-actuator，访问http://servicetwo:port/actuator/refresh 刷新单个spring-cloud-config-client的最新资源，想要不重启刷新所有的spring-cloud-config-client 需要借助spring cloud bus，注意spring cloud config client默认请求的config server端口为8888，如果想要更改需要在bootstrap.properties里面配置
  
- servicethree
  
  - 熔断+路由 注意使用spring cloud gateway无法使用spring-boot-start-web
  
- servicefour
  
  - spring security集成 现阶段集成的不是很好
  
- servicefive 
  
  - spring cloud zuul路由



  

  

  

  

  

  

  

  

  

  

  

  

  

  

--------

#### spring boot+cas
1. [cas服务器搭建](https://blog.csdn.net/lhc0512/article/details/82466246)

2. [cas查询数据库验证用户](https://blog.csdn.net/zzy730913/article/details/80825800)



------


## spring cloud 与spring boot版本对比

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

[什么是微服务？](https://mp.weixin.qq.com/s/kMXob-m8rSGn4DcaJJPybA)

[一份微服务架构手稿图，彻底搞定微服务核心原理！](https://mp.weixin.qq.com/s/c3fQsf7oNOk9xfA95CtKHw)

[冒着挂科的风险也要给你们看的 Spring Cloud 入门总结](https://mp.weixin.qq.com/s/w0euDh5EpGrfppvbWXKeUw)

[Spring Cloud 20000 字总结！](https://mp.weixin.qq.com/s/34w6qqhEh8pFMMsL-X1dzg)

[1.5W 字搞懂 Spring Cloud，太牛了！](https://mp.weixin.qq.com/s/EHPKm50KmHq_KZIHyVef3A)

