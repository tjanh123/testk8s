package org.ttrung.mai.k8s.test1;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.config.EnableWebFlux;

import reactor.core.publisher.Mono;

@SpringBootApplication
@EnableWebFlux
@EnableEurekaClient
@RestController
@RequestMapping("/${spring.application.name}")
@EnableFeignClients(basePackageClasses = {Test3Proxy.class, Test2Proxy.class})
public class MainTest 
{
    public static void main( String[] args )
    {
        SpringApplication.run(MainTest.class, args);
    }
    
    @Value("${spring.application.name}")
    private String testName;
    
    @GetMapping("/{name}")
    public Mono<String> index(@PathVariable String name) throws UnknownHostException {
        String hostName = InetAddress.getLocalHost().getHostName();
        String hostAddress = InetAddress.getLocalHost().getHostAddress();
        String greeting = "Hi " + name + "! :) It's " + testName + ", my host is " + hostName + ":" + hostAddress;
        return Mono.just(greeting);
    }
    
    @Autowired
    private Test2Proxy test2Proxy;
    
    @Autowired
    private Test3Proxy test3Proxy;
    
    @GetMapping("/call")
    public Mono<Map<Integer, String>> call(){ 
    	Map<Integer, String> res = new HashMap<Integer, String>();
    	res.put(2, test2Proxy.callTest("<1 to 2>"));
    	res.put(3, test3Proxy.callTest("<1 to 3>"));
    	return Mono.just(res);
    }
    
}
