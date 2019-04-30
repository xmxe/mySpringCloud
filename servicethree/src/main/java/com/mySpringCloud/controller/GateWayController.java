package com.mySpringCloud.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class GateWayController {

    @RequestMapping("/hello")
	public String hello(String name){return "负载均衡,hello,这是由serviceone发布的--- "+name;}

}
