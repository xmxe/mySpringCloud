package com.xmxe.controller;

import com.xmxe.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Slf4j
@RestController
public class ProviderController {

    @GetMapping("get_ribbon")
    public String get_ribbon(String name){
        return  "get_ribbon-----------"+name;
    }

    @PostMapping("post_ribbon")
    public String post_ribbon(String name){
        return  "post_ribbon-----------"+name;
    }

    /**
     * 如果要接收(服务消费中传过来的)被@RequestBody指明的参数，那么对应方法的对应参数前一定要有@RequestBody；(如果没有的话，收到的参数值就为null;如果写成@RequestParam的话，那么feign调用会失败,其余参数前视情况可以写@RequestParam,也可以不写)
     * 如果要接收(服务消费中传过来的)被@RequestParam指明的参数，那么可以写@RequestParam，也可以不写(当服务提供者中对应的参数名字与服务消费者传过来的参数名字一致时，可以不写，不一致时，需要写)
     *
     * 如果服务消费者传过来参数时，全都用的是@RequestParam的话，那么服务提供者的Controller中对应参数前可以写@RequestParam,也可以不写(当两边参数名字一致时，可以省略不写)
     * 如果服务消费者传过来参数时，有@RequestBody的话，那么服务提供者的Controller中对应参数前必须要写@RequestBody(如果是多参数的话，其余参数前视情况可以写@RequestParam,也可以不写)
     */
    @GetMapping("get_feign")
    public String get_feign(String name){ return  "get_feign-----------"+name; }

    @GetMapping(value = "/simple/{id}")
    String findById(@PathVariable("id") Long id){
        return String.valueOf(id);
    }

    @PostMapping("post_feign")
    public String post_feign(@RequestParam Map<String, Object> param){
        if(param != null && !param.isEmpty()){
            log.info("post_feign参数-{}",param);
            return "post_feign-----------"+param.get("name");
        }
        return  "post_feign-----------";
    }

    @PostMapping("queryBy")
    public String queryBy(User user){
        if(user != null){
            log.info("user参数-{}",user.toString());
            return "user_feign-----------"+user.getName();
        }
        return  "user_feign-----------";
    }

    @PostMapping("requestBody")
    public String requestBody(@RequestBody String name,String age){
        return "name="+name+" age="+age;
    }

    @RequestMapping("/session_redis2")
    public String redis_session(HttpServletRequest request){
        //HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        String name = (String) session.getAttribute("name");
        if(StringUtils.isEmpty(name)){
            name = "SessionRedis2|" + System.currentTimeMillis();
            session.setAttribute("name", name);
        }
        System.out.println("访问端口：" + request.getServerPort());
        return name;
    }
}
