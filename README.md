# mySpringCloud
|         serviceone         |         servicetwo          |     servicethree     |      servicefour      |         servicefive         |
| :------------------------: | :-------------------------: | :------------------: | :-------------------: | :-------------------------: |
|    spring cloud ribbon     |     spring cloud feign      | spring cloud gateway | spring cloud security |      spring cloud zuul      |
| spring cloud config server | spring cloud config client1 | spring cloud hystrix |                       | spring cloud config client2 |

***
#### note on 12.11
- spring-boot-starter-thymeleaf 模板引擎
- spring-boot-devtools使Spring Boot应用支持热部署，无需手动重启Spring Boot应用  
&ensp;&ensp;&ensp;&ensp;spring-boot-devtools是一个为开发者服务的一个模块，其中最重要的功能就是修改代码后自动启动springboot服务，速度比手动停止后再启动要快，节省出来的并不是手工操作的时间。  
&ensp;&ensp;&ensp;&ensp;原理 主要是使用了两个ClassLoader，一个Classloader加载不会改变的类（第三方Jar包），另一个ClassLoader加载会更改的类，称为  restart ClassLoader,这样在有代码更改的时候，原来的restartClassLoader 被丢弃，重新创建一个restartClassLoader，由于需要加载的类相比较少，所以实现了较快的重启时间
- serviceone集成spring cloud config server、spring cloud ribbon、提供了一个由feign测试调用的接口，spring redis 做session共享，spring-cloud-starter-bus-kafka、spring-boot-starter-actuator。需要更新仓库的最新资源使用post请求http://serviceone:port/actuator/bus-refresh 即可将所有的spring cloud config client的服务无需重启即可访问最新的资源，借助Git仓库的WebHook，我们就可轻松实现配置的自动刷新
- servicetwo 集成spring-cloud-config-client、spring cloud feign、spring-boot-starter-actuator，访问http://servicetwo:port/actuator/refresh
刷新单个spring cloud config client的最新资源，想要不重启刷新所有的spring cloud config client 需要借助spring cloud bus，注意spring cloud config client默认请求的config server端口为8888，如果想要更改需要在bootstrap.properties里面配置
- servicethree 熔断+路由 注意使用spring cloud gateway无法使用spring-boot-start-web
- servicefour spring security集成 现阶段xmxe集成的不算很好
- servicefive spring cloud zuul路由

#### spring boot+cas
1. [cas服务器搭建](https://blog.csdn.net/lhc0512/article/details/82466246)
2. [cas查询数据库验证用户](https://blog.csdn.net/zzy730913/article/details/80825800)

## spring cloud 与spring boot版本对比
