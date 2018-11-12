package org.ttrung.mai.k8s.test3;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
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
}
