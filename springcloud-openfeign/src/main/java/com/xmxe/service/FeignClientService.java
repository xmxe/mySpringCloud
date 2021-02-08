package com.xmxe.service;

import com.xmxe.config.FeiginConfig;
import com.xmxe.entity.User;
import com.xmxe.hystrix.ProducerRemoteHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
// 远程调用 服务名不允许带'_'
@FeignClient(qualifier = "feginQualifier",value = "provider-service",url = "http://localhost:8085/",
        fallback = ProducerRemoteHystrix.class,configuration = FeiginConfig.class,decode404 = true)
/*
 * name是value的别名，value也是name的别名。两者的作用是一致的，name指定FeignClient的名称，如果项目使用了Ribbon，name属性会作为微服务的名称，用于服务发现。
 * qualifier属性用来指定@Qualifier注解的值
 * url 调试时使用 指定请求的服务地址 此时name value无效
 * decode404当feign请求的接口(不是spring mvc的请求接口)发生http 404错误时，如果该字段位true，会调用decoder进行解码，否则抛出FeignException(假如未指定fallback)。
 * fallback：定义容错的处理类，当调用远程接口失败或超时时，会调用对应接口的容错逻辑，fallback指定的类必须实现@FeignClient标记的接口。
 * fallbackFactory：工厂类，用于生成fallback类示例，通过这个属性我们可以实现每个接口通用的容错逻辑，减少重复的代码。
 * path属性定义当前FeignClient的统一前缀。这样方便在该FeignClient中的@RequestMapping中书写value值
 */
public interface FeignClientService {

    /**
     * 如果想让服务消费者采用GET方式调用服务提供者，那么需要：
     * 条件1:服务消费者这边feign调用时，在所有参数前加上@RequestParam注解。
     * 条件2:服务消费者这边feign调用时，指明为GET方式（注:如果不指明method,那么在条件1满足的情况下，采用的是默认的GET方式）。
     * 注：这里条件1和条件2，是“且”的关系(都满足时，才为GET)。
     *
     */
    @GetMapping("/get_feign")
    String get_feign(@RequestParam(value = "name") String name);

    /**
     * 如果想让服务消费者采用POST方式调用服务提供者，那么只需要：
     * 条件1:服务消费者这边feign调用时，在所有参数前加上@RequestParam注解，并指明feign消费服务的方式为POST。
     * 条件2:服务消费者这边feign调用时，有且只有一个参数前为@RequestBody或什么也没有（如果有多个参数，那么其余参数前必须有@RequestParam）
     * 注：这里条件1和条件2，是“或”的关系（当至少一个满足时，即为POST）。
     * 注：在服务消费者中，使用feign消费服务时，如果参数前什么也不写，那么默认是由@RequestBody指明的。
     * 即：只要不满足GET方式请求，那么POST方式请求是一定支持的。
     *
     * 参数前未使用@RequestParam或参数前使用了@RequestBody注解(此时不论是否手动指定请求方式、不论指定的方式是POST还是GET，那么最终都以POST方式消费服务)
     * 在服务消费者中，使用feign消费服务时，如果参数前什么也不写，那么默认是由@RequestBody指明的。
     *'@RequestBody'注解的参数，需要POST方式才能传递数据
     *
     * 多参数时，如果服务消费者想采用POST进行feign调用，那么：服务消费者中该接口方法里的这些参数前，最多只能有一个参数是@RequestBody指明的，其余的参数必须使用@RequestParam指明。
     */
    @PostMapping("/post_feign")
    String post_feign(@RequestParam Map<String, Object> param);

    // feign多参数调用不允许使用对象接收 String queryBy(User user);//该请求不会成功，只要参数是复杂对象，使用下面的调用方式,或者使用多个@RequestParam,或者使用map
    @PostMapping("queryBy")
    String queryBy(@SpringQueryMap User user);

    // 服务消费者这边feign调用时，有且只有一个参数前为@RequestBody或什么也没有（如果有多个参数，那么其余参数前必须有@RequestParam）
    @PostMapping("requestBody")
    String requestBody(@RequestBody String name,@RequestParam("age") String age);

    // @PathVariable("id") 中的”id”，不能省略，必须指定。
    @GetMapping(value = "/simple/{id}")
    String findById(@PathVariable("id") Long id);



}