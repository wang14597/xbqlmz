package com.wwlei.authservice;

import com.wwlei.common.utils.Constants;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@SuppressWarnings("checkstyle:HideUtilityClassConstructor")
@ComponentScan(basePackages = {AuthServiceApplication.APPLICATION_PACKAGE_NAME, Constants.GLOBAL_EXCEPTION_PACKAGE_NAME})
public class AuthServiceApplication {

    public static final String APPLICATION_PACKAGE_NAME = "com.wwlei.authservice";

    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);
    }

}
