package svcb.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient(value = "svca-service", fallback = SvcaFeignClient.ServiceAClientFallback.class)
//这里的name对应调用服务的spring.applicatoin.name
public interface SvcaFeignClient {

    @RequestMapping(value = "/svca/hi")
    String hi(@RequestParam("id") String id);

    @RequestMapping(value = "/svca/hellow")
    String hellow(@RequestParam("id") String id);

    @Component
    class ServiceAClientFallback implements SvcaFeignClient {
        private static final Logger LOGGER = LoggerFactory.getLogger(ServiceAClientFallback.class);

        @Override
        public String hi(String id) {
            LOGGER.info("SERVICE A FAILED! - FALLING BACK 异常发生，进入fallback方法");
            return "hi============SERVICE A FAILED! - FALLING BACK 异常发生，进入fallback方法";
        }

        @Override
        public String hellow(String id) {
            LOGGER.info("SERVICE A FAILED! - FALLING BACK 异常发生，进入fallback方法");
            return "hellow==============SERVICE A FAILED! - FALLING BACK 异常发生，进入fallback方法";
        }
    }
}
