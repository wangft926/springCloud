package com.zjx;

import de.codecentric.boot.admin.server.config.AdminServerProperties;
import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.web.context.request.async.TimeoutCallableProcessingInterceptor;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@SpringBootApplication
@EnableDiscoveryClient
@EnableAdminServer
public class MonitorApplication {

    public static void main(String[] args) {
        SpringApplication.run(MonitorApplication.class, args);
    }


    @Configuration
    public static class SecuritySecureConfig extends WebSecurityConfigurerAdapter {
        private final String adminContextPath;

        public SecuritySecureConfig(AdminServerProperties adminServerProperties) {
            this.adminContextPath = adminServerProperties.getContextPath();
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            // @formatter:off
            SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
            successHandler.setTargetUrlParameter("redirectTo");

            http.authorizeRequests()
                    .antMatchers(adminContextPath + "/assets/**").permitAll()
                    .antMatchers(adminContextPath + "/login").permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .formLogin().loginPage(adminContextPath + "/login").successHandler(successHandler).and()
                    .logout().logoutUrl(adminContextPath + "/logout").and()
                    .httpBasic().and()
                    .csrf().disable();
            // @formatter:on
        }
    }

    @Configuration
    public class WebMvcConfig implements WebMvcConfigurer {


        @Override
        public void configureAsyncSupport(final AsyncSupportConfigurer configurer) {
            configurer.setDefaultTimeout(60 * 1000L);
            configurer.registerCallableInterceptors(timeoutInterceptor());
            configurer.setTaskExecutor(threadPoolTaskExecutor());
        }

        @Bean
        public TimeoutCallableProcessingInterceptor timeoutInterceptor() {
            return new TimeoutCallableProcessingInterceptor();
        }

        @Bean
        public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
            ThreadPoolTaskExecutor t = new ThreadPoolTaskExecutor();
            t.setCorePoolSize(10);
            t.setMaxPoolSize(100);
            t.setQueueCapacity(20);
            t.setThreadNamePrefix("WYF-Thread-");
            return t;
        }
    }

}