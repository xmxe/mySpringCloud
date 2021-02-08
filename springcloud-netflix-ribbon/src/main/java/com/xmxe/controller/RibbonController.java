package com.xmxe.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class RibbonController {
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient client;

    @GetMapping("/ribbon_get")
    public String ribbon_get(@RequestParam String name){
        //ribbon不支持下划线
        String url = "http://provider-service/get_ribbon?name="+name;
        ResponseEntity<String> getForEntity =  restTemplate.getForEntity(url,String.class);
        String getForEntityBody = getForEntity.getBody();
        log.info("getForEntity->{},getForEntityBody->{}",getForEntity,getForEntityBody);

        String url2 = "http://provider-service/get_ribbon?name={1}";
        ResponseEntity<String> getForEntity2 =  restTemplate.getForEntity(url2,String.class,name);
        String getForEntityBody2 = getForEntity2.getBody();
        log.info("getForEntity2->{},getForEntityBody2->{}",getForEntity2,getForEntityBody2);

        Map<String,Object> map = new HashMap<>();
        map.put("name",name);
        String url3 = "http://provider-service/get_ribbon?name={name}";
        ResponseEntity<String> getForEntity3 =  restTemplate.getForEntity(url3,String.class,map);
        String getForEntityBody3 = getForEntity3.getBody();
        log.info("getForEntity3->{},getForEntityBody3->{}",getForEntity3,getForEntityBody3);

//        restTemplate.getForObject()传参形式和上面的getForEntity是一样的,只不过不到需要.getBody了
        return restTemplate.getForObject(url, String.class);
    }

    @GetMapping("/ribbon_post")
    public String ribbon_post(@RequestParam String name){
        MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("name",name);
        //ribbon不支持下划线
        String url = "http://provider-service/post_ribbon";
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url,paramMap,String.class);
        String body = responseEntity.getBody();
        log.info("responseEntity->{},body->{}",responseEntity,body);

        String url2 = "http://provider-service/post_ribbon?name={name}";
        ResponseEntity<String> responseEntity2 = restTemplate.postForEntity(url2,null,String.class,name);
        String body2 = responseEntity2.getBody();
        log.info("responseEntity2->{},body2->{}",responseEntity2,body2);

        ResponseEntity<String> responseEntity3 = restTemplate.postForEntity(url2,null,String.class,Map.of("name",name));
        String body3 = responseEntity3.getBody();
        log.info("responseEntity3->{},body3->{}",responseEntity3,body3);
//        postForObject（和上面的用法一样）
        return restTemplate.postForObject(url,paramMap,String.class);
    }

    @GetMapping("/ribbon_put")
    public String ribbon_put(@RequestParam String name){
        // 更新时使用put请求 无返回值
        String url = "http://provider-service/put_ribbon";
        restTemplate.put(url,name);
        return "put请求成功";
    }

    @GetMapping("/ribbon/delete")
    public String ribbon_delete(String id){
        restTemplate.delete("http://HELLO-SERVICE/user_url/{1}",id);
        return "delete请求成功";
    }
}
