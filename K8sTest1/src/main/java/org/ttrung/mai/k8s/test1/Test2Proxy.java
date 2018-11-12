package org.ttrung.mai.k8s.test1;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="k8s-test2")
@RibbonClient(name="k8s-test2")
public interface Test2Proxy {

	@GetMapping("/k8s-test2/{name}")
	String callTest(@PathVariable("name") String name);
	
	
}
