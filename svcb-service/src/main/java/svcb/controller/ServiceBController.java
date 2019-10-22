package svcb.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import svcb.service.SvcaFeignClient;

@RefreshScope
@RestController
@RequestMapping(value = "/svcb")
public class ServiceBController {

    @Autowired
    EurekaDiscoveryClient discoveryClient;

    @Value("${msg:unknown}")
    private String msg;

/*    @GetMapping(value = "/")
    public String printServiceB() {
        ServiceInstance serviceInstance = discoveryClient.getLocalServiceInstance();
        return serviceInstance.getServiceId() + " (" + serviceInstance.getHost() + ":" + serviceInstance.getPort() + ")" + "===>Say " + msg;
    }*/

    @Autowired
    RestTemplate restTemplate;

    @RequestMapping("/restTemplate")
    public String restTemplate(@RequestParam String id) {
        return restTemplate.getForObject("http://svca-service/svca/hi?id=" + id, String.class);
    }


    @HystrixCommand(fallbackMethod = "executeFallback")
    @GetMapping("/getIpAndPort")
    public String getIpAndPort() {
        return this.restTemplate.getForObject("http://svca-service/svca/getIpAndPort", String.class);
    }

    public String executeFallback() {
        return "返回降级ip:port===88888888:9999";
    }

    @GetMapping("/getIpAndPort1")
    public String getIpAndPort1() {
        return this.restTemplate.getForObject("http://svca-service/svca/getIpAndPort", String.class);
    }

    @Autowired
    SvcaFeignClient svcaFeignClient;


    @GetMapping("/hi")
    public String hi(@RequestParam String id) {
        return svcaFeignClient.hi(id);
    }

    @GetMapping("/hellow")
    public String hellow(@RequestParam String id) {
        return svcaFeignClient.hellow(id);
    }

  /*  @Autowired
    private LoadBalancerClient loadBalancerClient;*/


    @GetMapping("/testGateWay")
    public String testGateWay() {
        return "======testGateWay=====";
    }
}