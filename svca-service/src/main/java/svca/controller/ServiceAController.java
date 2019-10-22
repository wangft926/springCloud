package svca.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import svca.service.ServiceBClient;

import javax.annotation.Resource;
import java.security.Principal;

@RefreshScope
@RestController
@RequestMapping(value = "/svca")
public class ServiceAController {

    @Value("${name:unknown}")
    private String name;

    @Autowired
    EurekaDiscoveryClient discoveryClient;
    @Autowired
    private ServiceBClient serviceBClient;


    @GetMapping(path = "/current")
    public Principal getCurrentAccount(Principal principal) {
        return principal;
    }

    @GetMapping(value = "test1")
    @ResponseBody
    public Object test() {
        return "9999999999999999";
    }


    @Autowired
    RestTemplate restTemplate;

    @Resource
    private Registration registration;

    @GetMapping("/getIpAndPort")
    @ResponseBody
    public String getIpAndPort() {
        return this.registration.getHost() + ":" + registration.getPort();
    }

    @GetMapping("/hi")
    public String hi(@RequestParam String id) {
        //return restTemplate.getForObject("http://svcb-service/hi?id="+id, String.class);
        return "service-A-hi";

    }

    @GetMapping("/hellow")
    public String hellow(@RequestParam String id) {
      /*  if (Integer.parseInt(id) == 8) {
            throw new RuntimeException("testsetestsetestestsetestestset==================");
        }*/
        return "===hellow===" + this.registration.getHost() + ":" + registration.getPort();
    }

}